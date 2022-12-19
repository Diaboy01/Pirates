package net.novorex.pirates.commands;

import net.novorex.pirates.Main;
import net.novorex.pirates.api.InventoryUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
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

            if(config.get(args[1]) == null) {
                player.sendMessage("Dieses Inventar existiert nicht.");
                return false;
            }

            String inventoryString = config.getString(args[1]);

            try {
                long timestamp = Long.parseLong(inventoryString);
                if(timestamp < 1671491463968L) {
                    player.sendMessage("Dieses Inventar wird von unserem neuen System nicht mehr unterstützt.");
                    return false;
                }

                ItemStack[] stacks = InventoryUtils.stringToContent(inventoryString);
                Arrays.stream(stacks).filter(Objects::nonNull).forEach(itemStack -> {
                    if (player.getInventory().firstEmpty() == -1) {
                        player.getWorld().dropItem(player.getLocation(), itemStack);
                    } else {
                        player.getInventory().addItem(itemStack);
                    }
                });
            } catch (NumberFormatException exception) {
                player.sendMessage("Das ist keine gültige Inventar-Nummer!");
            }
        }
        if(args.length == 1) {
            Player player = (Player) sender;
            ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();

            File playersFile = new File("plugins/Novorex/Players/", player.getName() + ".yml");
            YamlConfiguration config = YamlConfiguration.loadConfiguration(playersFile);

            if(config.get(args[0]) != null) {
                long timestamp = Long.parseLong(args[0]);
                if(timestamp < 1671491463968L) {
                    player.sendMessage("Dieses Inventar wird von unserem neuen System nicht mehr unterstützt.");
                    return false;
                }

                Main.instance.getEconomy().withdrawPlayer(player, 33);
                String inventoryString = config.getString(args[0]);
                ItemStack[] stacks = InventoryUtils.stringToContent(inventoryString);

                Arrays.stream(stacks).filter(Objects::nonNull).forEach(itemStack -> {
                    if (player.getInventory().firstEmpty() == -1) {
                        player.getWorld().dropItem(player.getLocation(), itemStack);
                    } else {
                        player.getInventory().addItem(itemStack);
                    }
                });

                player.sendMessage("Du hast dir dein Inventar für 33 Dukaten zurückgekauft.");
            } else {
                player.sendMessage("Diese Zahl existiert nicht.");
            }
        }

        return false;
    }
}
