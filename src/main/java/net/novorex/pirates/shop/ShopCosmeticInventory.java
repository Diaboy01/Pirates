package net.novorex.pirates.shop;

import net.novorex.pirates.Main;
import net.novorex.pirates.api.ItemAPI;
import net.novorex.pirates.api.inventory.CustomInventory;
import net.novorex.pirates.api.inventory.InventoryAction;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ShopCosmeticInventory extends CustomInventory {

    public ShopCosmeticInventory() {
        super("§6Shop §0- §7Cosmetics", 9*3);

        super.setSlot(0, new ItemAPI(Material.DARK_OAK_DOOR, 1).setName("Zurück").get(), new InventoryAction() {

            @Override
            public void run(Player player, CustomInventory customInventory, InventoryClickEvent event) {
                event.setCancelled(true);
                Bukkit.getScheduler().runTaskLater(Main.instance, () -> new ShopInventory().open(player), 2);
            }
        });

        super.setSlot(12, new ItemAPI(Material.valueOf("BONESANDSWORDS_PIRATE_BANDANA_HELMET")).addLore("§7Kosten: §625 Dukaten").get(), new ShopBuyAction(25, new ItemAPI(Material.valueOf("BONESANDSWORDS_PIRATE_BANDANA_HELMET")).get()));
        super.setSlot(13, new ItemAPI(Material.valueOf("BONESANDSWORDS_PIRATE_HAT_HELMET")).addLore("§7Kosten: §638 Dukaten").get(), new ShopBuyAction(38, new ItemAPI(Material.valueOf("BONESANDSWORDS_PIRATE_HAT_HELMET")).get()));
        super.setSlot(14, new ItemAPI(Material.valueOf("PIRATE_HAT_PIRATE_HELMET")).addLore("§7Kosten: §650 Dukaten").get(), new ShopBuyAction(50, new ItemAPI(Material.valueOf("PIRATE_HAT_PIRATE_HELMET")).get()));
    }
}
