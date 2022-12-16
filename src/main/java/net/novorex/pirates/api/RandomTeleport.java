package net.novorex.pirates.api;

import net.novorex.pirates.Main;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


public class RandomTeleport {
    public static void spawn(@NotNull Player player) {
        World world = Bukkit.getWorld("world");
        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();

        player.setInvulnerable(true);
        Bukkit.getScheduler().runTaskLater(Main.instance, () -> player.teleport(world.getSpawnLocation()), 20L);
        Bukkit.getScheduler().runTaskLater(Main.instance, () -> Bukkit.dispatchCommand(console, "spreadplayers 0 0 50 200 false " + player.getName()), 20L * 5);
        Bukkit.getScheduler().runTaskLater(Main.instance, () -> player.setInvulnerable(false), 20L * 15);

    }
}
