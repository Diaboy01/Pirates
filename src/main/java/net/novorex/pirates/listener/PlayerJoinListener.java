package net.novorex.pirates.listener;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();

        Player player = event.getPlayer();
        String playerName = player.getName();
        event.setJoinMessage("§fServer §8➝ §7 [+] " + player.getName());

        if(player.isOp()) {
            player.setPlayerListName("§r§l" + player.getDisplayName());
        } else {
            player.setPlayerListName("§r" + player.getDisplayName());
        }

        if (player.hasPlayedBefore()) {
            //NORMAL JOIN
        } else {
            //FIRST JOIN
        }


    }
}
