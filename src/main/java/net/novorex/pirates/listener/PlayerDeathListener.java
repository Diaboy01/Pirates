package net.novorex.pirates.listener;

import com.google.common.collect.Lists;
import net.kyori.adventure.text.Component;
import net.novorex.pirates.Main;
import net.novorex.pirates.api.InventoryUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.util.Vector;

import java.awt.*;

import static net.novorex.pirates.api.YAMLPlayers.printYml;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        String playerName = player.getName();

        long time = System.currentTimeMillis();
        String inventoryString = InventoryUtils.inventoryToString(player.getInventory());
        printYml(playerName, String.valueOf(time), inventoryString);

        event.getDrops().clear();
        event.setKeepLevel(true);
        player.getInventory().clear();
        player.getInventory().setArmorContents(new ItemStack[4]);
        player.getWorld().strikeLightningEffect(player.getLocation());

        ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
        skullMeta.setOwner(player.getName());
        skullMeta.setLore(Lists.newArrayList("§0" + time));
        itemStack.setItemMeta(skullMeta);
        Item item = player.getWorld().dropItem(player.getLocation(), itemStack);
        item.setGlowing(true);
        Bukkit.getScheduler().runTaskLater(Main.instance, () ->item.remove(), 20L * 10);
    }
}