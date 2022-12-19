package net.novorex.pirates.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;


public class ChatListener implements Listener {
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();
        event.setCancelled(true);
        Bukkit.broadcastMessage("§f" + "" + player.getDisplayName() + " " + "§8➝ §7" + event.getMessage());
    }
}