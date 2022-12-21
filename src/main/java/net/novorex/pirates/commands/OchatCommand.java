package net.novorex.pirates.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class OchatCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender.isOp()) {
            if (!(args[0].equals("o"))) {
                sender.sendMessage("Error! Nutze: /o o TEXT");
            } else {
                String msg = "";
                for (int i = 1; i < args.length; ++i) {
                    msg = String.valueOf(msg) + args[i];
                    msg = String.valueOf(msg) + " ";
                }
                Bukkit.broadcastMessage("§f" + "§k??????????§r§f " + "§8➝ §7" + msg);
            }
        }
        return false;
    }
}

