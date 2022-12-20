package net.novorex.pirates.api.inventory.action;

import net.novorex.pirates.api.inventory.CustomInventory;
import net.novorex.pirates.api.inventory.InventoryAction;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class CancelInventoryAction implements InventoryAction {

    @Override
    public void run(Player player, CustomInventory customInventory, InventoryClickEvent event) {
        event.setCancelled(true);
    }
}