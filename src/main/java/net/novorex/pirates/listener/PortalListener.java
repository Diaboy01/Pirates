package net.novorex.pirates.listener;

import net.novorex.pirates.api.RandomTeleport;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.Random;

public class PortalListener implements Listener {


    @EventHandler
    public void onPlayerChangedWorld(final PlayerChangedWorldEvent event) {
        World world = Bukkit.getWorld("world");

        final Player player = event.getPlayer();
        World worldGetTo = event.getPlayer().getWorld();
        String worldGetToName = worldGetTo.getName();

        if (!worldGetToName.equalsIgnoreCase("world") && !player.hasPermission("empire.portal")) {
            assert world != null;
            RandomTeleport.spawn(player);
            player.sendMessage("Diese Welt ist gesperrt!");
            worldGetTo.getWorldFolder().delete();
        }
    }

    /**
    @EventHandler
    public void playerTeleport(final PlayerTeleportEvent event) {
        World world = Bukkit.getWorld("world");

        final Player player = event.getPlayer();
        World worldGetTo = event.getTo().getWorld();
        String worldGetToName = worldGetTo.getName();

        if (!worldGetToName.equalsIgnoreCase("world")) {
            assert world != null;
            RandomTeleport.spawn(player);
            player.sendMessage("Diese Welt ist gesperrt!");
            worldGetTo.getWorldFolder().delete();
        }
    }
    **/
}