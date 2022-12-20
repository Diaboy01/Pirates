package net.novorex.pirates.shop;

import net.novorex.pirates.Main;
import net.novorex.pirates.api.inventory.CustomInventory;
import net.novorex.pirates.api.inventory.InventoryAction;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public final class ShopBuyAction implements InventoryAction {

    private final double eco;
    private final ItemStack result;

    public ShopBuyAction(double eco, ItemStack result) {
        this.eco = eco;
        this.result = result;
    }

    @Override
    public void run(Player player, CustomInventory customInventory, InventoryClickEvent event) {
        event.setCancelled(true);

        if(Main.instance.getEconomy().getBalance(player) >= eco) {
            Main.instance.getEconomy().withdrawPlayer(player, eco);

            if(player.getInventory().firstEmpty() == -1) {
                player.getWorld().dropItem(player.getLocation(), result);
            } else {
                player.getInventory().addItem(result);
            }

            player.sendMessage("Kauf abgeschlossen! -" + eco + " Dukaten");
            player.closeInventory();
        }
    }
}
