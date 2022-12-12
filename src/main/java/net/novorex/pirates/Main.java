package net.novorex.pirates;

import net.novorex.pirates.commands.*;
import net.novorex.pirates.listener.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    public static Main instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        super.onEnable();

        instance = this;

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new PlayerJoinListener(), this);
        pluginManager.registerEvents(new PlayerWorldTimingListener(), this);
        pluginManager.registerEvents(new PlayerDeathListener(), this);

        getCommand("inv").setExecutor(new GetInventoryBackup());
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
