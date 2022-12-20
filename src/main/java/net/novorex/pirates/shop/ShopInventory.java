package net.novorex.pirates.shop;

import net.novorex.pirates.Main;
import net.novorex.pirates.api.ItemAPI;
import net.novorex.pirates.api.inventory.CustomInventory;
import net.novorex.pirates.api.inventory.InventoryAction;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public final class ShopInventory extends CustomInventory {


    public ShopInventory() {
        super("§6Shop", 9*3);

        super.setSlot(11, new ItemAPI(Material.valueOf("SMALLSHIPS_OAK_COG")).setName("§6Schiffe §8/ §7Utilities").get(), new InventoryAction() {

            @Override
            public void run(Player player, CustomInventory customInventory, InventoryClickEvent event) {
                event.setCancelled(true);
                player.closeInventory();
                Bukkit.getScheduler().runTaskLater(Main.instance, () -> new ShopShipInventory().open(player), 2);
            }
        });

        super.setSlot(13, new ItemAPI(Material.DIAMOND).setName("§6Seltene Rohstoffe").get(), new InventoryAction() {

            @Override
            public void run(Player player, CustomInventory customInventory, InventoryClickEvent event) {
                event.setCancelled(true);
                player.closeInventory();
                Bukkit.getScheduler().runTaskLater(Main.instance, () -> new ShopRessourceInventory().open(player), 2);
            }
        });

        super.setSlot(15, new ItemAPI(Material.valueOf("PIRATE_HAT_PIRATE_HELMET")).setName("§6Cosmetics").get(), new InventoryAction() {

            @Override
            public void run(Player player, CustomInventory customInventory, InventoryClickEvent event) {
                event.setCancelled(true);
                player.closeInventory();
                Bukkit.getScheduler().runTaskLater(Main.instance, () -> new ShopCosmeticInventory().open(player), 2);
            }
        });
    }
}
