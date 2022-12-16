package net.novorex.pirates.commands;

import net.novorex.pirates.api.InventoryUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;

import static org.bukkit.Bukkit.getServer;

public class GetInventoryByCMD implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length == 2 && sender.isOp()) {
            Player player = getServer().getPlayer(args[0]);

            File playersFile = new File("plugins/Novorex/Players/", player.getName() + ".yml");
            YamlConfiguration config = YamlConfiguration.loadConfiguration(playersFile);

            String inventoryString = config.getString(args[1]);
            Inventory inv = InventoryUtils.stringToInventory(inventoryString);

            Arrays.stream(inv.getContents()).filter(Objects::nonNull).forEach(itemStack -> {
                if(player.getInventory().firstEmpty() == -1) {
                    player.getWorld().dropItem(player.getLocation(), itemStack);
                } else {
                    player.getInventory().addItem(itemStack);
                }
            });
        }
        if(args.length == 1) {
            Player player = (Player) sender;
            ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
            Bukkit.dispatchCommand(console, "eco take " + player.getName() + " 33");

            File playersFile = new File("plugins/Novorex/Players/", player.getName() + ".yml");
            YamlConfiguration config = YamlConfiguration.loadConfiguration(playersFile);

            String inventoryString = config.getString(args[0]);
            Inventory inv = InventoryUtils.stringToInventory(inventoryString);

            Arrays.stream(inv.getContents()).filter(Objects::nonNull).forEach(itemStack -> {
                if(player.getInventory().firstEmpty() == -1) {
                    player.getWorld().dropItem(player.getLocation(), itemStack);
                } else {
                    player.getInventory().addItem(itemStack);
                }
            });
        }

        return false;
    }
}
