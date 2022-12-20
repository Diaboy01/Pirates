package net.novorex.pirates.commands;

import net.novorex.pirates.Main;
import net.novorex.pirates.shop.ShopInventory;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class UserShopCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
            if (Main.instance.getEconomy().getBalance(player) >= 1) {
                Main.instance.getEconomy().withdrawPlayer(player, 1);
                Bukkit.dispatchCommand(console, "minecraft:give " + player.getName() + " simpleshops:simple_shop 1");
                player.sendMessage("Du hast dir 1 Shop für 1 Dukate gekauft");
            }
        }
        return false;
    }
}
