package net.novorex.pirates.listener;

import com.google.common.collect.Lists;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.novorex.pirates.Main;
import net.novorex.pirates.api.InventoryUtils;
import net.novorex.pirates.api.Utils;
import net.novorex.pirates.api.claim.ClaimAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import static net.novorex.pirates.api.YAMLPlayers.printYml;

public class PlayerDeathListener implements Listener {
    private FileWriter fileWriter;
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("[dd.MM.yyyy HH:mm:ss]");
    String date = Utils.getDate();

    public PlayerDeathListener() {
        File directory = new File("plugins/Novorex/Logs/" + date + "/");
        directory.mkdirs();

        File deathLogFile = new File("plugins/Novorex/Logs/" + date + "/", "death.log");
        if(!deathLogFile.exists()) {
            try {
                deathLogFile.createNewFile();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }

        try {
            fileWriter = new FileWriter(deathLogFile);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void printDeath(Player player, Location deathLocation, String message, Player killer) {
        try {
            fileWriter.write(simpleDateFormat.format(new Date()) + "(+1h) " + killer.getName() + " hat getötet: " + player.getName() + " am Ort: [Welt=" + deathLocation.getWorld().getName() + ",x=" + deathLocation.getBlockX() + ",y=" + deathLocation.getBlockY() + ",z=" + deathLocation.getBlockZ() + "] (" + message + ")\n");
            fileWriter.flush();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        String playerName = player.getName();

        event.setKeepLevel(true);

        long time = System.currentTimeMillis();
        player.sendMessage("§l§k->§r§0" + time + "§r§l§k<-");
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
        Bukkit.getScheduler().runTaskLater(Main.instance, item::remove, 20L * 30);
        //
        String claimedCurrently = ClaimAPI.getClaimed(player.getLocation().getChunk());
        if (claimedCurrently != null && playerName.equals(Bukkit.getOfflinePlayer(UUID.fromString(claimedCurrently)).getName())){
            event.setDeathMessage("§7§l" + event.getDeathMessage() + " (" + player.getKiller().getName() + ")");
            printDeath(event.getEntity(), event.getEntity().getLocation(), event.getDeathMessage(), player.getKiller());
        } else {event.setDeathMessage("§7" + event.getDeathMessage());}
    }
}