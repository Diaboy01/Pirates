package net.novorex.pirates.api.inventory;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class CustomInventoryListener implements Listener {

    @EventHandler
    public void onCustomInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if(CustomInventory.hasOpen(player)) {
            CustomInventory customInventory = CustomInventory.customInventoryOpen.get(player.getUniqueId().toString());
            customInventory.clickItemStack(event, event.getRawSlot(), player);
        }
    }

    @EventHandler
    public void onCustomInventoryClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();

        if(CustomInventory.hasOpen(player)) {
            CustomInventory customInventory = CustomInventory.customInventoryOpen.get(player.getUniqueId().toString());
            CustomInventory.close(player, customInventory);
        }
    }
}
