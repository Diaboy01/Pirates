package net.novorex.pirates.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import java.io.File;


public class Invite implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;
            ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
            String playerName = player.getName();
            if (player.hasPermission("*") || player.isOp()) {
                commandSender.sendMessage("Achtung! Du bist * OP");
            }
                if (player.hasPermission("empire.leader")) {
                    File playersFile = new File("plugins/Novorex/Players/", playerName + ".yml");
                    YamlConfiguration config = YamlConfiguration.loadConfiguration(playersFile);
                    String teamName = config.getString("Team");
                    String farbe = config.getString("Farbe");
                    if (args.length != 1) {
                        commandSender.sendMessage("Error! Nutze: /invite SPIELERNAME");
                        return true;
                    }
                    if (args[0].matches(playerName)) {
                        commandSender.sendMessage("Error! Du kannst dich nicht selbst einladen!");
                        return true;
                    }

                    Player target = Bukkit.getPlayer(args[0]);
                    if (target.hasPermission("empire.leader")) {
                        commandSender.sendMessage("Error! Du kannst keinen anderen Leader einladen!");
                        return true;
                    }

                    Bukkit.dispatchCommand(console, "tellraw " + args[0] + " [\"\",{\"text\":\"[!] Du hast eine Einladung für die Allianz " + teamName + " erhalten! -> \",\"bold\":true},{\"text\":\"[EINLADUNG ANNEHMEN?]\",\"bold\":true,\"underlined\":true,\"clickEvent\":{\"action\":\"suggest_command\",\"value\":\"/accept " + teamName + " " + farbe + "\"}}]");
                    commandSender.sendMessage("Spieler: " + args[0] + " wurde zum Team: " + teamName + " eingeladen!");
                    Bukkit.dispatchCommand(console, "lp user " + args[0] + " permission settemp accept." + teamName + " true 5m");
                }
        } else {
            commandSender.sendMessage("Error!");
            return true;
        }
        return false;
    }
}
