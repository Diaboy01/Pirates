package net.novorex.pirates.commands;

import java.io.IOException;

import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class PlayerDamageCommand implements CommandExecutor {

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage("Du hast keinen Zugriff auf diese Aktion.");
            return true;
        }
        if (args.length != 1) {
            sender.sendMessage("Syntax§8: §f/modifydamage " + "§8<§f%§8>".replace("%", "Prozent pro Herz"));
            return true;
        }
        double value = -1.0;
        try {
            value = Double.parseDouble(args[0]);
        }
        catch (NumberFormatException exception) {
            sender.sendMessage("Bitte gebe eine Nummer an!");
            return true;
        }
        if (value < 0.0) {
            sender.sendMessage("Bitte gebe eine Nummer an!");
            return true;
        }
        this.saveValue(value);
        sender.sendMessage("Alle Spieler erleiden nun §c-" + value + "% Schaden pro halbes Herz."); //§c+ wurde zu §c-
        return true;
    }

    private void saveValue(final double value) {
        final File file = new File("plugins/Novorex/General/", "damageIndex.yml");
        final YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        config.set("damageModifier", (Object)value);
        try {
            config.save(file);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static double getValue() {
        final File file = new File("plugins/Novorex/General/", "damageIndex.yml");
        final YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        if (config.get("damageModifier") == null) {
            return -1.0;
        }
        return config.getDouble("damageModifier");
    }
}