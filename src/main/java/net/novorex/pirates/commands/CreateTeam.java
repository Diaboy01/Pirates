package net.novorex.pirates.commands;

import net.novorex.pirates.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class CreateTeam implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
            if (commandSender.isOp()) {
                if (!(args.length == 2)) {
                    commandSender.sendMessage("Error! Nutze: /create TEAMNAME TEAMFARBE(als Wort z.B. gold )");
                }
                if (args.length == 2) {
                    ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                    Bukkit.getScheduler().runTaskLater(Main.instance, () -> Bukkit.dispatchCommand(console, "team add " + args[0]), 20L * 1);
                    Bukkit.getScheduler().runTaskLater(Main.instance, () -> Bukkit.dispatchCommand(console, "team modify " + args[0] + " color " + args[1]), 20L * 2);
                    Bukkit.getScheduler().runTaskLater(Main.instance, () ->  Bukkit.dispatchCommand(console, "team modify " + args[0] + " nametagVisibility hideForOtherTeams"), 20L * 3);
                    Bukkit.getScheduler().runTaskLater(Main.instance, () -> commandSender.sendMessage("Team: " + args[0] + " wurde erstellt!"), 20L * 4);
                }
            }
        return false;
    }
}