package net.novorex.pirates.api.inventory;
import java.util.HashMap;

import net.novorex.pirates.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class CustomInventory {

    public static HashMap<String, CustomInventory> customInventoryOpen = new HashMap<String, CustomInventory>();
    private static boolean a = false;

    private final HashMap<String, InventoryAction> inventoryActions;
    private final Inventory inventory;

    @SuppressWarnings("deprecation")
    public CustomInventory(String name, int size) {
        inventoryActions = new HashMap<>();
        this.inventory = Bukkit.createInventory(null, size, name);

        if(!a) {
            a = true;
            Bukkit.getPluginManager().registerEvents(new CustomInventoryListener(), Main.instance);
        }
    }

    public void setSlot(int slot, ItemStack itemStack, InventoryAction inventoryAction) {
        this.inventory.setItem(slot, itemStack);
        this.inventoryActions.put(String.valueOf(slot), inventoryAction);
    }

    public void clearSlot(int slot) {
        this.inventory.setItem(slot, new ItemStack(Material.AIR));
        this.inventoryActions.remove(String.valueOf(slot));
    }

    public void clickItemStack(InventoryClickEvent event, int slot, Player player) {
        InventoryAction inventoryAction = inventoryActions.get(String.valueOf(slot));
        if(inventoryAction == null) return;
        inventoryAction.run(player, this, event);
    }

    public Inventory getInventory() {
        return this.inventory;
    }

    public void open(Player player) {
        Bukkit.getScheduler().runTask(Main.instance, new Runnable() {

            @Override
            public void run() {
                if(customInventoryOpen.containsKey(player.getUniqueId().toString())) {
                    player.closeInventory();
                    customInventoryOpen.remove(player.getUniqueId().toString());

                    Bukkit.getScheduler().runTaskLater(Main.instance, () -> {
                        customInventoryOpen.put(player.getUniqueId().toString(), CustomInventory.this);
                        player.openInventory(inventory);
                    }, 2);
                } else {
                    customInventoryOpen.put(player.getUniqueId().toString(), CustomInventory.this);
                    player.openInventory(inventory);
                }
            }
        });
    }

    public void close(Player player) {
        customInventoryOpen.remove(player.getUniqueId().toString());
        player.closeInventory();
    }

    public static void close(Player player, CustomInventory customInventory) {
        customInventory.close(player);
    }

    public static void open(Player player, CustomInventory customInventory) {
        customInventory.open(player);
    }

    public static boolean hasOpen(Player player) {
        return customInventoryOpen.containsKey(player.getUniqueId().toString());
    }
}