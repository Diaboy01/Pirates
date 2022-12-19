package net.novorex.pirates;

import net.milkbowl.vault.economy.Economy;
import net.novorex.pirates.api.claim.ClaimNotifyHandler;
import net.novorex.pirates.commands.*;
import net.novorex.pirates.listener.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    public static Main instance;

    private Economy economy;

    @Override
    public void onEnable() {
        // Plugin startup logic
        super.onEnable();

        instance = this;
        if (!setupEconomy()) {
            getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            Bukkit.shutdown();
            return;
        }

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new PlayerJoinListener(), this);
        pluginManager.registerEvents(new PlayerWorldTimingListener(), this);
        pluginManager.registerEvents(new PlayerDeathListener(), this);
        pluginManager.registerEvents(new PlayerPickupItemListener(), this);
        pluginManager.registerEvents(new PlayerExtraDamageListener(), this);
        pluginManager.registerEvents(new PortalListener(), this);
        pluginManager.registerEvents(new CreatureSpawnListener(), this);
        pluginManager.registerEvents(new PlayerMotdListener(), this);
        pluginManager.registerEvents(new ChatListener(), this);

        getCommand("talk").setExecutor(new FakeTalk());
        getCommand("inv").setExecutor(new GetInventoryByCMD());
        getCommand("modifydamage").setExecutor(new PlayerDamageCommand());
        getCommand("claim").setExecutor(new ClaimCommand());
        getCommand("dukaten").setExecutor(new DukatenCommand());
        getCommand("time").setExecutor(new FarmTimeCommand());
        getCommand("shop").setExecutor(new ShopCommand());

        ClaimNotifyHandler.init();
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }

        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }

        economy = rsp.getProvider();
        return economy != null;
    }

    public Economy getEconomy() {
        return this.economy;
    }


       //     for (Player player : Bukkit.getOnlinePlayers()) {
      //  PlayerJoinEvent playerJoinEvent = new PlayerJoinEvent(player, null);
      //  Bukkit.getPluginManager().callEvent(playerJoinEvent);}

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        super.onDisable();

     //   for (Player player : Bukkit.getOnlinePlayers()) {
     //       PlayerQuitEvent playerQuitEvent = new PlayerQuitEvent(player, (String) null);
     //       Bukkit.getPluginManager().callEvent(playerQuitEvent);
     //       player.kickPlayer("Server Plugin Update! Please rejoin!");}

    }
}
