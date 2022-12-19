package net.novorex.pirates.commands;

import net.novorex.pirates.Main;
import net.novorex.pirates.api.UUIDFetcher;
import net.novorex.pirates.api.claim.ClaimAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class ClaimCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player) {
            ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
            Player player = (Player) sender;
            Location loc = player.getLocation();
            double x = Math.abs(loc.getX());
            double z = Math.abs(loc.getZ());
            if (x > 200 || z > 200) {
                if (args.length == 0) {
                    if (ClaimAPI.hasClaim(player)) {
                        player.sendMessage("Du hast bereits ein Gebiet geclaimt!");
                        return false;
                    }

                    if (ClaimAPI.isAreaClaimed(player.getLocation().getChunk())) {
                        player.sendMessage("Dieses Gebiet gehört bereits jemandem!");
                        return false;
                    }
                    ClaimAPI.claim(player.getLocation().getChunk(), player);
                    Bukkit.getScheduler().runTaskLater(Main.instance, () -> Bukkit.dispatchCommand(console, "lp user " + player.getName() + " permission set essentials.setwarp true"), 20L * 1);
                    Bukkit.getScheduler().runTaskLater(Main.instance, () -> Bukkit.dispatchCommand(console, "talk " + player.getName() + " /setwarp " + player.getName()), 20L * 2);
                    Bukkit.getScheduler().runTaskLater(Main.instance, () -> Bukkit.dispatchCommand(console, "lp user " + player.getName() + " permission unset essentials.setwarp"), 20L * 3);
                    Bukkit.getScheduler().runTaskLater(Main.instance, () -> Bukkit.dispatchCommand(console, "lp user " + player.getName() + " permission set essentials.warps." + player.getName() +  " true"), 20L * 5);
                    player.sendMessage("Du hast dein Gebiet bei der Position " + player.getLocation().getChunk() + " beansprucht! ( /warp " + player.getName() + " c)");
                    return false;
                } else if(args.length == 2) {
                    if (args[0].equals("delete") && sender.isOp()) {
                        UUID uuid = UUIDFetcher.getUUID(args[1]);

                        if(uuid != null) {
                            if(ClaimAPI.unclaim(uuid)) {
                                sender.sendMessage("Gebiet von " + UUIDFetcher.getName(uuid) + " gelöscht.");
                                Bukkit.dispatchCommand(console, "delwarp " + player.getName());
                            } else {
                                sender.sendMessage("Der Spieler hat kein Gebiet.");
                            }
                        } else {
                            sender.sendMessage("Spieler existiert nicht.");
                        }
                    } else {
                        player.sendMessage("Schreibe: /claim");
                    }
                }
            }
        }

        return false;
    }
}

