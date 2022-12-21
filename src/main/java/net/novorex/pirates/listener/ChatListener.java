package net.novorex.pirates.listener;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.io.File;


public class ChatListener implements Listener {
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();

        //Wie unperformant soll der Chat sein? Diaboy: JA

        File playersFile = new File("plugins/Novorex/Players/", playerName + ".yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(playersFile);

        String teamName = config.getString("Team");
        String farbe = "f";
        farbe = config.getString("Farbe");
        String prefix = String.format("%s", teamName);
        if (teamName == null || prefix == null || prefix.equals("-")) {
            prefix = "";
        } else {prefix = "§" + farbe + "#" + prefix + " ";}

        if(player.isOp()) {
            player.setPlayerListName("§" + farbe + player.getDisplayName());
            player.setDisplayName(prefix + player.getDisplayName());
        } else {
            player.setPlayerListName("§" + farbe + player.getDisplayName());
            player.setDisplayName(prefix + player.getDisplayName());
        }

        event.setCancelled(true);
        Bukkit.broadcastMessage("§f" + "" + player.getDisplayName() + " " + "§r§8➝ §7" + event.getMessage());
    }
}