package net.novorex.pirates.commands;

import net.novorex.pirates.api.UUIDFetcher;
import net.novorex.pirates.api.claim.ClaimAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class ClaimCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            Location loc = player.getLocation();
            double x = Math.abs(loc.getX());
            double z = Math.abs(loc.getZ());
            if (x < 300 || z < 300) { //TODO testen
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
                    player.sendMessage("Du hast dein Gebiet bei der Position " + player.getLocation().getChunk() + " beansprucht!");
                    return false;
                } else {
                    if (args.equals("delete") && sender.isOp()) {
                        ClaimAPI.unclaim(Bukkit.getPlayer(UUIDFetcher.getUUID(args[2]))); //TODO testen

                    } else {
                        player.sendMessage("Schreibe: /claim");
                    }
                }
            }
        }

        return false;
    }
}

