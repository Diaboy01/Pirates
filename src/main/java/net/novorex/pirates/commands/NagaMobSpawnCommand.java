package net.novorex.pirates.commands;

import net.novorex.pirates.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class NagaMobSpawnCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender.isOp()) {
            ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
            Player player = (Player) sender;

            for(Entity e : player.getNearbyEntities(200, 200, 200)) {
                if (e instanceof Player && e.getType() == EntityType.PLAYER) {
                    Location loc = (Location) e.getLocation();
                    int x = (int) loc.getX();
                    int y = (int) loc.getY() + 30;
                    int z = (int) loc.getZ();

                    Bukkit.getScheduler().runTaskLater(Main.instance, () -> Bukkit.dispatchCommand(console, "minecraft:summon mowziesmobs:naga " + x + " " + y + " " + z), 20L);
                }
            }


        }
        return false;
    }


}

