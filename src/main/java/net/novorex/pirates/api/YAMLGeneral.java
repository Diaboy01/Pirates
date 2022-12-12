package net.novorex.pirates.api;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class YAMLGeneral {
    public static void printYml(String fileName, String name, String value) {
        File generalFile = new File("plugins/Novorex/General/", fileName + ".yml");
        File directory = new File("plugins/Novorex/General/");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(generalFile);
        directory.mkdirs();
        if (!generalFile.exists()) {
            try {
                generalFile.createNewFile();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        config.set(name,value);
        try {
            config.save(generalFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}