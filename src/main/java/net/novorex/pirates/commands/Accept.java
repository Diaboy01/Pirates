package net.novorex.pirates.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import static net.novorex.pirates.api.YAMLPlayers.printYml;

public class Accept implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
            String playerName = player.getName();

            if (player.hasPermission("*") || player.isOp()) {
                commandSender.sendMessage("Achtung! Du bist * OP");
                return true;
            }
            if (player.hasPermission("empire.leader")) {
                commandSender.sendMessage("Error! Du bist ein Leader");
                return true;
            }
                if (args.length != 2) {
                    commandSender.sendMessage("Error! Nutze: /accept TEAMNAME PRÜFZAHL");
                    return true;
                } else {
                    int PRÜFZAHL = Integer.parseInt(args[1]);
                    if (PRÜFZAHL < 1 || PRÜFZAHL > 9) {
                        commandSender.sendMessage("Error! Diese PRÜFZAHL ist falsch!");
                        return true;
                    }
                    if (player.hasPermission("accept." + args[0])) {
                        Bukkit.dispatchCommand(console, "team join " + args[0] + " " + playerName);
                        printYml(playerName, "Team", args[0]);
                        printYml(playerName, "Farbe", args[1]);
                        //Bukkit.dispatchCommand(console, "kick " + playerName + " Allianz " + args[0] + " hinzugefügt! Please rejoin!");
                        commandSender.sendMessage("Spieler: " + playerName + " wurde zur Allianz " + args[0] + " hinzugefügt!");
                        Bukkit.dispatchCommand(console, "talk " + playerName + " #" + args[0]);
                    } else {
                        commandSender.sendMessage("Error! Keine aktuelle Einladung vorhanden!");
                        return true;
                    }
                }
            } else {
                commandSender.sendMessage("Error!");
                return true;
            }
        return false;
        }
    }
