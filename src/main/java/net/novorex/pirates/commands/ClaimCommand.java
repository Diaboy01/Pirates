package net.novorex.pirates.commands;

import net.novorex.pirates.api.claim.ClaimAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ClaimCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;

            if(args.length == 0) {
                if(ClaimAPI.hasClaim(player)) {
                    player.sendMessage("Du hast bereits ein Gebiet!");
                    return false;
                }

                if(ClaimAPI.isAreaClaimed(player.getLocation().getChunk())) {
                    player.sendMessage("Dieses Gebiet gehört bereits jemandem!");
                    return false;
                }

                ClaimAPI.claim(player.getLocation().getChunk(), player);
                player.sendMessage("Du hast den 3x3 Chunk bei der Position " + player.getLocation().getChunk() + " beansprucht!");
                return false;
            }
        }

        return false;
    }
}

