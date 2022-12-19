package net.novorex.pirates.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class InventoryUtils {

    /* OLD
    public static String inventoryToString(Inventory inventory) {

        JsonObject obj = new JsonObject();

        obj.addProperty("name", inventory.getType().name());
        if (inventory.getType().name().equalsIgnoreCase("player")) {
            obj.addProperty("size", 45);
        } else {
            obj.addProperty("size", inventory.getSize());
        }


        JsonArray items = new JsonArray();

        for (int i = 0; i <= inventory.getSize(); i++) {
            ItemStack item = inventory.getItem(i);
            if (item != null) {
                ItemMeta itemMeta = item.getItemMeta();
                Damageable dmg = (Damageable) itemMeta;
                JsonObject jitem = new JsonObject();
                jitem.addProperty("type", item.getType().toString());
                jitem.addProperty("name", itemMeta.getDisplayName());
                jitem.addProperty("damage", dmg.getDamage());
                jitem.addProperty("quantity", item.getAmount());
                jitem.addProperty("slot", i);

                if (itemMeta.hasLore()) {
                    JsonArray lore = new JsonArray();
                    for (String s: itemMeta.getLore()) {
                        lore.add(s);
                    }
                    jitem.add("lore", lore);
                }

                if (itemMeta.hasEnchants()) {
                    JsonArray enchants = new JsonArray();
                    Iterator< Map.Entry < Enchantment, Integer >> irt = itemMeta.getEnchants().entrySet().iterator();
                    while (irt.hasNext()) {
                        Map.Entry < Enchantment, Integer > entry = irt.next();
                        JsonObject enc = new JsonObject();
                        Enchantment ee = (Enchantment) entry.getKey();
                        enc.addProperty("type", ee.getKey().getKey());
                        enc.addProperty("level", entry.getValue());
                        enchants.add(enc);
                    }


                    jitem.add("enchantments", enchants);
                }

                items.add(jitem);
            }
        }

        obj.add("items", items);

        return obj.toString();
    }

    public static Inventory stringToInventory(String s) {
        JsonObject obj = new JsonParser().parse(s).getAsJsonObject();

        Inventory inv = Bukkit.createInventory(null, obj.get("size").getAsInt(), obj.get("name").getAsString());

        JsonArray items = obj.get("items").getAsJsonArray();

        for (JsonElement itemele: items) {
            JsonObject jitem = itemele.getAsJsonObject();

            ItemStack item = new ItemStack(Material.valueOf(jitem.get("type").getAsString()), jitem.get("quantity").getAsInt());
            ItemMeta itemMeta = item.getItemMeta();

            Damageable dmg = (Damageable) itemMeta;
            dmg.setDamage(jitem.get("damage").getAsInt());


            if (jitem.has("lore")) {
                JsonArray jlore = jitem.get("lore").getAsJsonArray();
                List< String > lore = new ArrayList< String >();
                for (JsonElement loreE: jlore) {
                    lore.add(loreE.getAsString());
                }
                itemMeta.setLore(lore);
            }

            if (jitem.has("enchantments")) {
                JsonArray jenchants = jitem.get("enchantments").getAsJsonArray();
                for (JsonElement je: jenchants) {
                    JsonObject encObj = je.getAsJsonObject();
                    System.out.println(encObj.toString());

                    Enchantment enchantment = Enchantment.getByKey(NamespacedKey.minecraft(encObj.get("type").getAsString()));
                    if(enchantment != null) {
                        itemMeta.addEnchant(enchantment, encObj.get("level").getAsInt(), true);
                    }
                }
            }

            item.setItemMeta(itemMeta);
            inv.setItem(jitem.get("slot").getAsInt(), item);
        }

        return inv;
    }
     */

    public static String inventoryToString(Inventory inventory) {
        return toBase64(inventory);
    }

    public static ItemStack[] stringToContent(String content) {
        return stacksFromBase64(content);
    }

    private static String toBase64(Inventory inventory) {
        return toBase64(inventory.getContents());
    }

    private static String toBase64(ItemStack[] contents) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            dataOutput.writeInt(contents.length);

            for (ItemStack stack : contents) {
                dataOutput.writeObject(stack);
            }

            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
            throw new IllegalStateException("Unable to save item stacks.", e);
        }
    }

    private static ItemStack[] stacksFromBase64(String data) {
        if (data == null)
            return new ItemStack[]{};

        ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
        BukkitObjectInputStream dataInput = null;
        ItemStack[] stacks = null;

        try {
            dataInput = new BukkitObjectInputStream(inputStream);
            stacks = new ItemStack[dataInput.readInt()];
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        for (int i = 0; i < stacks.length; i++) {
            try {
                stacks[i] = (ItemStack) dataInput.readObject();
            } catch (IOException | ClassNotFoundException e) {
                try {
                    dataInput.close();
                } catch (IOException ignored) {
                }
                return null;
            }
        }

        try {
            dataInput.close();
        } catch (IOException ignored) {
        }

        return stacks;
    }
}
