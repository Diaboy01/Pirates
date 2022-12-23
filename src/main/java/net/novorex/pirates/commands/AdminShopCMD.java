package net.novorex.pirates.commands;

import net.novorex.pirates.shop.ShopInventory;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


public class AdminShopCMD implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            Location loc = player.getLocation();
            double x = Math.abs(loc.getX());
            double z = Math.abs(loc.getZ());
            if (x < 20 || z < 20) {
                new ShopInventory().open(player);
            }
        }
        return false;
    }
}
