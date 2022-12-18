package net.novorex.pirates.listener;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.novorex.pirates.Main;
import net.novorex.pirates.api.RandomTeleport;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;


import static net.novorex.pirates.api.YAMLPlayers.printYml;


public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();

        Player player = event.getPlayer();

        Bukkit.dispatchCommand(console, "lp user " + player.getName() + " permission set essentials.warps." + player.getName() +  " true");
        Bukkit.dispatchCommand(console, "lp user " + player.getName() + " permission unset essentials.warps.Diaboy01");

        player.setGameMode(GameMode.SURVIVAL);
        event.setJoinMessage("§fServer §8➝ §7 [+] " + player.getName());
        String playerName = player.getName();

        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§6§oSponsor: Nitrado.net"));
        Bukkit.dispatchCommand(console,"tellraw " + playerName + " [\"\",{\"text\":\"Sponsor: \",\"italic\":true,\"color\":\"gold\"},{\"text\":\"Nitrado.net\",\"italic\":true,\"underlined\":true,\"color\":\"gold\",\"clickEvent\":{\"action\":\"open_url\",\"value\":\"https://nitra.do/KevinFilmt\"}}]");

        File playersFile = new File("plugins/Novorex/Players/", playerName + ".yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(playersFile);

        player.setPlayerListHeaderFooter("§e§lEMPIRE OF PIRATES 2", "§6§o➠ Sponsor: Nitrado.net");


        if(player.hasPlayedBefore()) {
            //NORMAL JOIN
            String teamName = config.getString("Team");
            String prefix = String.format("%s", teamName);
            if (teamName == null || prefix == null || prefix.equals("-")) {
                prefix = "";
            } else {prefix = "#" + prefix + " ";}

            if(player.isOp()) {
                player.setPlayerListName("" + prefix + "" + player.getDisplayName());
            } else {
                player.setPlayerListName("" + prefix + "" + player.getDisplayName());
            }
            Bukkit.dispatchCommand(console, "team add " + teamName);
            Bukkit.dispatchCommand(console, "team join " + teamName + " " + playerName);
        } else {
            //FIRST JOIN
            //for(int i = 0; i < 35; i++){
            //player.getInventory().setItem(i, null);
            //}
            RandomTeleport.spawn(player);
            Bukkit.dispatchCommand(console, "team add - ");
            Bukkit.dispatchCommand(console, "team join - " + playerName);
            Bukkit.dispatchCommand(console, "eco give " + playerName + " 100");
            printYml(playerName, "Team", "-");
            player.sendTitle("§e§lEMPIRE OF PIRATES 2", "Willkommen " + playerName + "!", 20, 120, 20);
            Bukkit.getScheduler().runTaskLater(Main.instance, () -> Bukkit.dispatchCommand(console, "clear " + playerName), 20L * 8);
            Bukkit.getScheduler().runTaskLater(Main.instance, () ->Bukkit.dispatchCommand(console, "kit starter " + playerName), 20L * 10);
        }

        Bukkit.getScheduler().runTaskLater(Main.instance, () ->player.setGameMode(GameMode.SURVIVAL), 20L * 5);
    }


}