package net.novorex.pirates.commands;

import net.novorex.pirates.Main;
import net.novorex.pirates.api.ItemAPI;
import net.novorex.pirates.api.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public final class WithdrawCommand implements CommandExecutor, Listener {

    public static ItemStack DUKAT = new ItemAPI(Material.SUNFLOWER).setName("§6Dukat").get();

    public static ItemStack getDukaten(int amount) {
        return new ItemAPI(Material.SUNFLOWER, amount).setName("§6Dukat").get();
    }

    public WithdrawCommand() {
        Bukkit.getPluginManager().registerEvents(this, Main.instance);
    }

    @EventHandler
    public void onBlockInteract(PlayerInteractEvent event) {
        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player player = event.getPlayer();
            ItemStack itemStack = player.getInventory().getItemInMainHand();

            if(itemStack.getType() == Material.SUNFLOWER && itemStack.hasItemMeta() && itemStack.getItemMeta().hasDisplayName()) {
                if(itemStack.getItemMeta().getDisplayName().equals("§6Dukat")) {
                    int amount = itemStack.getAmount();
                    player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                    player.sendMessage("Du hast §6" + (amount == 1 ? "1 Dukat" : amount + " Dukaten") + " §feingezahlt.");
                    Main.instance.getEconomy().depositPlayer(player, amount);
                    event.setCancelled(true);
                }
            }
        }
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;

            if(strings.length == 1) {
                try {
                    int amount = Integer.parseInt(strings[0]);

                    if(amount <= 0) {
                        throw new NumberFormatException("u stupid");
                    }

                    if(Main.instance.getEconomy().getBalance(player) >= amount) {
                        Main.instance.getEconomy().withdrawPlayer(player, amount);

                        while (amount > 64) {
                            amount -= 64;
                            Utils.giveItemStack(player, new ItemAPI(Material.SUNFLOWER, 64).setName("§6Dukat").get());
                        }

                        Utils.giveItemStack(player, new ItemAPI(Material.SUNFLOWER, amount).setName("§6Dukat").get());
                        player.sendMessage("Du hast §6" + (amount == 1 ? "1 Dukat" : amount + " Dukaten") + " §fabgehoben.");
                        return false;
                    } else {
                        commandSender.sendMessage("Du besitzt nicht genügend Dukaten.");
                        return false;
                    }
                } catch (NumberFormatException exception) {
                    commandSender.sendMessage("Bitte gebe eine gültige Anzahl an Dukaten an.");
                }
            }

            commandSender.sendMessage("Benutze: /withdraw <Anzahl>");
        }

        return true;
    }
}
