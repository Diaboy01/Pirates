package net.novorex.pirates.commands;

import net.novorex.pirates.Main;
import net.novorex.pirates.api.PlayerWorldTimings;
import net.novorex.pirates.api.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class FarmTimeCommand implements CommandExecutor {

    private static final double COST = 50;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length == 0 && sender instanceof Player) {
            Player player = (Player) sender;

            if (Main.instance.getEconomy().getBalance(player) >= COST) {
                Main.instance.getEconomy().withdrawPlayer(player, COST);
                PlayerWorldTimings playerWorldTimings = PlayerWorldTimings.getTimings(player.getUniqueId());
                boolean counting = playerWorldTimings.isCounting();
                playerWorldTimings.stopCounting();
                playerWorldTimings.clearTime();
                if (counting) {
                    playerWorldTimings.startCounting();
                }
                player.sendMessage("Du hast für " + COST + " Dukaten deine Farmzeit zurückgesetzt.");
            } else {
                player.sendMessage("Du besitzt nicht genügend Dukaten.");
            }
        }
        return false;
    }
}
