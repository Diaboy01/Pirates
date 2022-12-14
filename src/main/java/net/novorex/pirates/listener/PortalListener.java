package net.novorex.pirates.listener;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PortalListener implements Listener {


    @EventHandler
    public void onPlayerChangedWorld(final PlayerChangedWorldEvent event) {
        World world = Bukkit.getWorld("world");

        final Player player = event.getPlayer();
        World worldGetTo = event.getPlayer().getWorld();
        String worldGetToName = worldGetTo.getName();

        if (!worldGetToName.equals("world")) {
            assert world != null;
            player.teleport(world.getSpawnLocation());
            player.sendMessage("Diese Welt ist gesperrt!");
        }
    }

    @EventHandler
    public void playerTeleport(final PlayerTeleportEvent event) {
        World world = Bukkit.getWorld("world");

        final Player player = event.getPlayer();
        World worldGetTo = event.getTo().getWorld();
        String worldGetToName = worldGetTo.getName();

        if (!worldGetToName.equals("world")) {
            assert world != null;
            player.teleport(world.getSpawnLocation());
            player.sendMessage("Diese Welt ist gesperrt!");
        }
    }
}