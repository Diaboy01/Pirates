package net.novorex.pirates.shop;

import net.novorex.pirates.Main;
import net.novorex.pirates.api.ItemAPI;
import net.novorex.pirates.api.inventory.CustomInventory;
import net.novorex.pirates.api.inventory.InventoryAction;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ShopShipInventory extends CustomInventory {

    public ShopShipInventory() {
        super("§6Shop §0- §7Schiffe", 9*4);

        super.setSlot(0, new ItemAPI(Material.DARK_OAK_DOOR, 1).setName("Zurück").get(), new InventoryAction() {

            @Override
            public void run(Player player, CustomInventory customInventory, InventoryClickEvent event) {
                event.setCancelled(true);
                Bukkit.getScheduler().runTaskLater(Main.instance, () -> new ShopInventory().open(player), 2);
            }
        });

        super.setSlot(10, new ItemAPI(Material.valueOf("SMALLSHIPS_OAK_COG")).addLore("§7Kosten: §650 Dukaten").get(), new ShopBuyAction(50, new ItemAPI(Material.valueOf("SMALLSHIPS_OAK_COG")).get()));
        super.setSlot(11, new ItemAPI(Material.valueOf("SMALLSHIPS_SPRUCE_COG")).addLore("§7Kosten: §650 Dukaten").get(), new ShopBuyAction(50, new ItemAPI(Material.valueOf("SMALLSHIPS_SPRUCE_COG")).get()));
        super.setSlot(12, new ItemAPI(Material.valueOf("SMALLSHIPS_BIRCH_COG")).addLore("§7Kosten: §650 Dukaten").get(), new ShopBuyAction(50, new ItemAPI(Material.valueOf("SMALLSHIPS_BIRCH_COG")).get()));
        super.setSlot(14, new ItemAPI(Material.valueOf("SMALLSHIPS_JUNGLE_COG")).addLore("§7Kosten: §650 Dukaten").get(), new ShopBuyAction(50, new ItemAPI(Material.valueOf("SMALLSHIPS_JUNGLE_COG")).get()));
        super.setSlot(15, new ItemAPI(Material.valueOf("SMALLSHIPS_ACACIA_COG")).addLore("§7Kosten: §650 Dukaten").get(), new ShopBuyAction(50, new ItemAPI(Material.valueOf("SMALLSHIPS_ACACIA_COG")).get()));
        super.setSlot(16, new ItemAPI(Material.valueOf("SMALLSHIPS_DARK_OAK_COG")).addLore("§7Kosten: §650 Dukaten").get(), new ShopBuyAction(50, new ItemAPI(Material.valueOf("SMALLSHIPS_DARK_OAK_COG")).get()));

        super.setSlot(20, new ItemAPI(Material.valueOf("SMALLSHIPS_CANNON_ITEM")).addLore("§7Kosten: §625 Dukaten").get(), new ShopBuyAction(25, new ItemAPI(Material.valueOf("SMALLSHIPS_CANNON_ITEM")).get()));
        super.setSlot(24, new ItemAPI(Material.valueOf("SMALLSHIPS_CANNONBALL_ITEM"), 4).addLore("§7Kosten: §65 Dukaten").get(), new ShopBuyAction(5, new ItemAPI(Material.valueOf("SMALLSHIPS_CANNONBALL_ITEM"), 4).get()));

    }
}
