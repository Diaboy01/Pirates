package net.novorex.pirates.api.inventory;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public interface InventoryAction {

    void run(Player player, CustomInventory customInventory, InventoryClickEvent event);

}