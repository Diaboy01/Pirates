package net.novorex.pirates.api.claim;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.StringBufferInputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public final class ClaimAPI {

    // private static final NamespacedKey CLAIMED_KEY = new NamespacedKey("novorex", "claimed");

    private static final String SPLIT = "P";
    private static final File FILE = new File("plugins/Novorex/Claim/", "claims.yml");
    private static final YamlConfiguration CONFIG = YamlConfiguration.loadConfiguration(FILE);
    private static final HashMap<String, String> CACHE = new HashMap<>();

    static void init() {
        Set<String> uuids = CONFIG.getConfigurationSection("").getKeys(false);

        uuids.forEach(u -> {
            if(CONFIG.get(u) != null) {
                String content = CONFIG.getString(u);
                String[] split = content.split(SPLIT);

                if (split.length == 3) {
                    int x = Integer.parseInt(split[0]), z = Integer.parseInt(split[1]);
                    String world = split[2];

                    World w = Bukkit.getWorld(world);
                    if (w != null) {
                        Chunk chunk = w.getChunkAt(x, z);
                        CACHE.put(chunk.toString(), u);

                        Arrays.stream(getAreaChunks(chunk)).forEach(c -> {
                            CACHE.put(c.toString(), u);
                        });
                    }
                }
            }
        });
    }

    public static void claim(@NotNull Chunk chunk, @NotNull Player player) {
        Arrays.stream(getAreaChunks(chunk)).forEach(c -> {
            // c.getPersistentDataContainer().set(CLAIMED_KEY, PersistentDataType.STRING, player.getUniqueId().toString());
            CACHE.put(chunk.toString(), player.getUniqueId().toString());
        });

        CONFIG.set(player.getUniqueId().toString(), chunk.getX() + SPLIT + chunk.getZ() + SPLIT + chunk.getWorld().getName());
        save();
    }

    public static boolean hasClaim(@NotNull Player player) {
        return CONFIG.contains(player.getUniqueId().toString());
    }

    public static boolean hasClaim(@NotNull UUID uuid) {
        return CONFIG.contains(uuid.toString());
    }

    public static void unclaim(@NotNull Player player) {
        unclaim(player.getUniqueId());
    }

    public static boolean unclaim(@NotNull UUID uuid) {
        if(hasClaim(uuid)) {
            String claim = CONFIG.getString(uuid.toString());
            CONFIG.set(uuid.toString(), null);
            save();

            String[] splitted = claim.split(String.valueOf(SPLIT));

            int x = Integer.parseInt(splitted[0]), z = Integer.parseInt(splitted[1]);
            World world = Bukkit.getWorld(splitted[2]);

            if (world != null) {
                Chunk baseChunk = world.getChunkAt(x, z);
                CACHE.remove(baseChunk.toString());

                Arrays.stream(getAreaChunks(baseChunk)).forEach(chunk -> {
                    // chunk.getPersistentDataContainer().remove(CLAIMED_KEY);
                    CACHE.remove(chunk.toString());
                });
            }

            return true;
        }

        return false;
    }

    private static void save() {
        try {
            CONFIG.save(FILE);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private static Chunk[] getAreaChunks(@NotNull Chunk chunk) {
        Chunk[] chunks = new Chunk[7 * 7];

        int j = 0;
        for(int x = -3; x < 4; x++) {
            for(int z = -3; z < 4; z++) {
                chunks[j++] = chunk.getWorld().getChunkAt(chunk.getX() + x, chunk.getZ() + z);
            }
        }

        return chunks;
    }

    public static boolean isAreaClaimed(@NotNull Chunk chunk) {
        return Arrays.stream(getAreaChunks(chunk)).anyMatch(ClaimAPI::isClaimed);
    }

    public static boolean isClaimed(@NotNull Chunk chunk) {
        // return chunk.getPersistentDataContainer().has(CLAIMED_KEY, PersistentDataType.STRING);
        return CACHE.containsKey(chunk.toString());
    }

    public static String getClaimed(@NotNull Chunk chunk) {
        // return chunk.getPersistentDataContainer().has(CLAIMED_KEY, PersistentDataType.STRING) ? chunk.getPersistentDataContainer().get(CLAIMED_KEY, PersistentDataType.STRING) : null;
        return CACHE.getOrDefault(chunk.toString(), null);
    }
}