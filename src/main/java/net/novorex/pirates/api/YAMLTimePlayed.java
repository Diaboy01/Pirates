package net.novorex.pirates.api;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class YAMLTimePlayed {

    public static void printTime(String date, String playerName, long time) {
        File generalFile = new File("plugins/Novorex/General/TimePlayed/", date + ".yml");
        File directory = new File("plugins/Novorex/General/TimePlayed/");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(generalFile);
        directory.mkdirs();
        if (!generalFile.exists()) {
            try {
                generalFile.createNewFile();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        config.set(playerName,time);
        try {
            config.save(generalFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
