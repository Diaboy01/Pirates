package net.novorex.pirates.api;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    private static final SimpleDateFormat DATE_FORMAT_LIGHT = new SimpleDateFormat("dd.MM.yyyy");

    public static String getDate() {
        return DATE_FORMAT_LIGHT.format(new Date());
    }

    public static String getFullDate() {
        return DATE_FORMAT.format(new Date());
    }

    public static void giveItemStack(@NotNull Player player, @NotNull ItemStack itemStack) {
        if(itemStack.getType() == Material.AIR) return;

        if(player.getInventory().firstEmpty() == -1) {
            player.getWorld().dropItem(player.getLocation(), itemStack);
        } else {
            player.getInventory().addItem(itemStack);
        }
    }
}