package net.novorex.pirates.shop;

import net.novorex.pirates.Main;
import net.novorex.pirates.api.ItemAPI;
import net.novorex.pirates.api.inventory.CustomInventory;
import net.novorex.pirates.api.inventory.InventoryAction;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ShopRessourceInventory extends CustomInventory {

    public ShopRessourceInventory() {
        super("§6Shop §0- §7Ressourcen", 9*4);

        super.setSlot(0, new ItemAPI(Material.DARK_OAK_DOOR, 1).setName("Zurück").get(), new InventoryAction() {

            @Override
            public void run(Player player, CustomInventory customInventory, InventoryClickEvent event) {
                event.setCancelled(true);
                Bukkit.getScheduler().runTaskLater(Main.instance, () -> new ShopInventory().open(player), 2);
            }
        });

        super.setSlot(10, new ItemAPI(Material.GUNPOWDER).addLore("§7Kosten: §63 Dukaten").get(), new ShopBuyAction(3, new ItemAPI(Material.GUNPOWDER).get()));
        super.setSlot(11, new ItemAPI(Material.BLAZE_ROD).addLore("§7Kosten: §610 Dukaten").get(), new ShopBuyAction(10, new ItemAPI(Material.BLAZE_ROD).get()));
        super.setSlot(12, new ItemAPI(Material.ENDER_PEARL).addLore("§7Kosten: §615 Dukaten").get(), new ShopBuyAction(15, new ItemAPI(Material.ENDER_PEARL).get()));
        super.setSlot(13, new ItemAPI(Material.HEART_OF_THE_SEA).addLore("§7Kosten: §625 Dukaten").get(), new ShopBuyAction(25, new ItemAPI(Material.HEART_OF_THE_SEA).get()));
        super.setSlot(14, new ItemAPI(Material.NAUTILUS_SHELL).addLore("§7Kosten: §65 Dukaten").get(), new ShopBuyAction(5, new ItemAPI(Material.NAUTILUS_SHELL).get()));
        super.setSlot(15, new ItemAPI(Material.STRING).addLore("§7Kosten: §31 Dukaten").get(), new ShopBuyAction(1, new ItemAPI(Material.STRING).get()));
        super.setSlot(16, new ItemAPI(Material.FEATHER).addLore("§7Kosten: §61 Dukat").get(), new ShopBuyAction(1, new ItemAPI(Material.FEATHER).get()));

        super.setSlot(19, new ItemAPI(Material.COAL).addLore("§7Kosten: §31 Dukaten").get(), new ShopBuyAction(1, new ItemAPI(Material.COAL).get()));
        super.setSlot(20, new ItemAPI(Material.IRON_INGOT).addLore("§7Kosten: §62 Dukaten").get(), new ShopBuyAction(2, new ItemAPI(Material.IRON_INGOT).get()));
        super.setSlot(21, new ItemAPI(Material.LAPIS_LAZULI).addLore("§7Kosten: §61 Dukaten").get(), new ShopBuyAction(1, new ItemAPI(Material.LAPIS_LAZULI).get()));
        super.setSlot(22, new ItemAPI(Material.GOLD_INGOT).addLore("§7Kosten: §63 Dukaten").get(), new ShopBuyAction(3, new ItemAPI(Material.GOLD_INGOT).get()));
        super.setSlot(23, new ItemAPI(Material.REDSTONE).addLore("§7Kosten: §61 Dukaten").get(), new ShopBuyAction(1, new ItemAPI(Material.REDSTONE).get()));
        super.setSlot(24, new ItemAPI(Material.DIAMOND).addLore("§7Kosten: §65 Dukaten").get(), new ShopBuyAction(5, new ItemAPI(Material.DIAMOND).get()));
        super.setSlot(25, new ItemAPI(Material.EMERALD).addLore("§7Kosten: §62 Dukaten").get(), new ShopBuyAction(2, new ItemAPI(Material.EMERALD).get()));
    }
}
