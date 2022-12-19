package net.novorex.pirates.api;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.v1_16_R3.MojangsonParser;
import net.minecraft.server.v1_16_R3.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.*;

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
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

            dataOutputStream.writeInt(contents.length);
            for(ItemStack itemStack : contents) {
                if(itemStack == null || itemStack.getType().isAir()) {
                    continue;
                }

                dataOutputStream.writeUTF(itemStack.getType().name());
                dataOutputStream.writeInt(itemStack.getAmount());

                net.minecraft.server.v1_16_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(itemStack);
                dataOutputStream.writeUTF(nmsStack.getOrCreateTag().toString());
            }

            dataOutputStream.flush();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
            throw new IllegalStateException("Unable to save item stacks.", e);
        }
    }

    private static ItemStack[] stacksFromBase64(String data) {
        if (data == null) return new ItemStack[0];

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
        DataInputStream dataInputStream = new DataInputStream(byteArrayInputStream);

        try {
            int amount = dataInputStream.readInt();
            int index = 0;

            ItemStack[] stacks = new ItemStack[amount];

            while (dataInputStream.available() > 0) {
                if(index == amount) throw new IllegalStateException("Index can not be larger than amount received.");

                ItemStack itemStack = new ItemStack(Material.valueOf(dataInputStream.readUTF()), dataInputStream.readInt());
                String nbtString = dataInputStream.readUTF();
                NBTTagCompound nbtTagCompound = MojangsonParser.parse(nbtString);
                net.minecraft.server.v1_16_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(itemStack);
                nmsStack.setTag(nbtTagCompound);
                itemStack = CraftItemStack.asBukkitCopy(nmsStack);
                stacks[index++] = itemStack;
            }

            return stacks;
        } catch (IOException | CommandSyntaxException | IllegalArgumentException exception) {
            exception.printStackTrace();
        }

        return new ItemStack[0];
    }
}