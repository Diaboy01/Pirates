package net.novorex.pirates.commands;

import net.novorex.pirates.Main;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static org.bukkit.Bukkit.getServer;

public class NetherRandomTPCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender.isOp()) {
            ConsoleCommandSender console = getServer().getConsoleSender();
            World nether = Bukkit.getWorld("DIM-1");
            Player playerSender = (Player) sender;

            for(Entity player : playerSender.getNearbyEntities(200, 200, 200)) {
                if (player instanceof Player && player.getType() == EntityType.PLAYER) {
                    Bukkit.dispatchCommand(console, "lp group default permission set empire.portal true");
                    ((Player) player).setInvulnerable(true);

                    Bukkit.getScheduler().runTaskLater(Main.instance, () -> Bukkit.dispatchCommand(console, "lp user " + player.getName() + " permission settemp essentials.world true 10s"), 20L * 1);
                    Bukkit.getScheduler().runTaskLater(Main.instance, () -> {
                        assert nether != null;
                        player.teleport(nether.getSpawnLocation());
                    }, 20L * 2);
                    Bukkit.getScheduler().runTaskLater(Main.instance, () -> Bukkit.dispatchCommand(console, "talk " + player.getName() + " /essentials:world DIM-1"), 20L * 3);

                    Bukkit.getScheduler().runTaskLater(Main.instance, () -> Bukkit.dispatchCommand(console, "essentials:vanish " + player.getName() + " enable"), 20L * 4);
                    ((Player) player).sendTitle("§e§lEMPIRE OF PIRATES 2", "FINALE: " + player.getName() + " vs. ALL!", 20, 120, 20);
                    player.sendMessage("30 Sekunden! Verstecke dich!");
                    Bukkit.getScheduler().runTaskLater(Main.instance, () -> player.setInvulnerable(false), 20L * 35);
                    Bukkit.getScheduler().runTaskLater(Main.instance, () -> Bukkit.dispatchCommand(console, "essentials:vanish " + player.getName() + " disable"), 20L * 35);
                    Bukkit.getScheduler().runTaskLater(Main.instance, () -> player.sendMessage("Die Zeit ist vorbei! Kämpfe, bis niemand mehr übrig ist!"), 20L * 35);
                }
            }


        }
        return false;
    }


}

