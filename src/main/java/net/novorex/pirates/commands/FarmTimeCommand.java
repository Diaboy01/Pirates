package net.novorex.pirates.commands;

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
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        Player player = (Player) sender;
        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();

        PlayerWorldTimings playerWorldTimings = PlayerWorldTimings.getTimings(player.getUniqueId());
        boolean counting = playerWorldTimings.isCounting();
        playerWorldTimings.stopCounting();
        playerWorldTimings.clearTime();
        if(counting) {
            playerWorldTimings.startCounting();
        }

        Bukkit.dispatchCommand(console, "eco take " + player.getName() + " 50");
        return false;
    }
}
