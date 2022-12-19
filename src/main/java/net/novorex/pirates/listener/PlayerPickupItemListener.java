package net.novorex.pirates.listener;
import net.novorex.pirates.api.InventoryUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class PlayerPickupItemListener implements Listener {

    @EventHandler
    public void onPlayerDropItem(PlayerPickupItemEvent event) {
        Player player = event.getPlayer();
        if(event.getItem().getItemStack().getType() == Material.PLAYER_HEAD) {
            SkullMeta skullMeta = (SkullMeta)event.getItem().getItemStack().getItemMeta();
            List<String> loreList = skullMeta.getLore();

            if(loreList != null && loreList.size() == 1 && skullMeta.getOwner() != null && skullMeta.getOwner().equals(player.getName())) {
                String inventoryString = String.valueOf(ChatColor.stripColor(loreList.get(0)));

                File playersFile = new File("plugins/Novorex/Players/", player.getName() + ".yml");
                YamlConfiguration config = YamlConfiguration.loadConfiguration(playersFile);

                if(config.get(inventoryString) != null) {
                    event.setCancelled(true);
                    event.getItem().remove();

                    ItemStack[] content = InventoryUtils.stringToContent(config.getString(inventoryString));
                    Arrays.stream(content).filter(Objects::nonNull).forEach(itemStack -> {
                        if (player.getInventory().firstEmpty() == -1) {
                            player.getWorld().dropItem(player.getLocation(), itemStack);
                        } else {
                            player.getInventory().addItem(itemStack);
                        }
                    });

                    config.set(inventoryString, null);

                    try {
                        config.save(playersFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
