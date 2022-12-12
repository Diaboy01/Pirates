package net.novorex.pirates.api;


import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class YAMLPlayers {
    public static void printYml(String playerName, String name, String value) {
        File playersFile = new File("plugins/Novorex/Players/", playerName + ".yml");
        File directory = new File("plugins/Novorex/Players/");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(playersFile);
        directory.mkdirs();
        if (!playersFile.exists()) {
            try {
                playersFile.createNewFile();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        config.set(name,value);
        try {
            config.save(playersFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
