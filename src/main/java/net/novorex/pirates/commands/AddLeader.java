package net.novorex.pirates.commands;

import net.novorex.pirates.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import static net.novorex.pirates.api.YAMLPlayers.printYml;

public class AddLeader implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
            if (commandSender.isOp()) {
                if (!(args.length == 3)) {
                    commandSender.sendMessage("Error! Nutze: /add SPIELERNAME TEAMNAME TEAMFARBE(als Zahl z.B. 6 )");
                }
                if (args.length == 3) {
                    ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                    Bukkit.dispatchCommand(console, "team join " + args[1] + " " + args[0]);
                    Bukkit.dispatchCommand(console, "lp user " + args[0] + " permission set empire.leader true");
                    printYml(args[0], "Team", args[1]);
                    printYml(args[0], "Farbe", args[2]);
                    //Bukkit.getScheduler().runTaskLater(Main.instance, () -> Bukkit.dispatchCommand(console, "kick " + args[0] + " zum Leader befördert! Please rejoin!"), 20L * 3);
                    commandSender.sendMessage("Spieler: " + args[0] + " wurde zum Leader der Aillianz: " + args[1] + " ernannt!");
                }
            }
        return false;
    }
}