package net.novorex.pirates.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class FakeTalk implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender.isOp()) {
            String msg = "";
            for (int i = 1; i < args.length; ++i) {
                msg = String.valueOf(msg) + args[i];
                msg = String.valueOf(msg) + " ";
            }
            if (Bukkit.getServer().getPlayer(args[0]) == null) {
                final Player[] playerlist = Bukkit.getServer().getOnlinePlayers().toArray(new Player[0]);
                for (int j = 0; j < playerlist.length; ++j) {
                    playerlist[j].sendMessage("<" + args[0] + "> " + msg);
                }
            } else {
                Bukkit.getServer().getPlayer(args[0]).chat(msg);
            }
        }
        return false;
    }
}

