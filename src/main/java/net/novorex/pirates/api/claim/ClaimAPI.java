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
import java.util.Arrays;

public final class ClaimAPI {

    private static final NamespacedKey CLAIMED_KEY = new NamespacedKey("novorex", "claimed");

    private static final char SPLIT = 'P';
    private static final File FILE = new File("plugins/Novorex/Claim/", "claims.yml");
    private static final YamlConfiguration CONFIG = YamlConfiguration.loadConfiguration(FILE);

    public static void claim(@NotNull Chunk chunk, @NotNull Player player) {
        Arrays.stream(getAreaChunks(chunk)).forEach(c -> c.getPersistentDataContainer().set(CLAIMED_KEY, PersistentDataType.STRING, player.getUniqueId().toString()));

        CONFIG.set(player.getUniqueId().toString(), chunk.getX() + SPLIT + chunk.getZ() + SPLIT + chunk.getWorld().getName());
        save();
    }

    public static boolean hasClaim(@NotNull Player player) {
        return CONFIG.contains(player.getUniqueId().toString());
    }

    public static void unclaim(@NotNull Player player) {
        if(hasClaim(player)) {
            String claim = CONFIG.getString(player.getUniqueId().toString());
            String[] splitted = claim.split(String.valueOf(SPLIT));

            int x = Integer.parseInt(splitted[0]), z = Integer.parseInt(splitted[1]);
            World world = Bukkit.getWorld(splitted[2]);

            if(world != null) {
                Chunk baseChunk = world.getChunkAt(x, z);
                Arrays.stream(getAreaChunks(baseChunk)).forEach(chunk -> chunk.getPersistentDataContainer().remove(CLAIMED_KEY));
            }
        }
    }

    private static void save() {
        try {
            CONFIG.save(FILE);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private static final int CHUNK_OFFSET = 5; //TODO modify chunk size: CHUNK_OFFSET = 1 -> 4x4, CHUNK_OFFSET = 2 -> 5x5, ...
    private static Chunk[] getAreaChunks(@NotNull Chunk chunk) {
        Chunk[] chunks = new Chunk[(3 + CHUNK_OFFSET) * (3 + CHUNK_OFFSET)];

        int j = 0;
        for(int x = -1 - CHUNK_OFFSET; x < 2 + CHUNK_OFFSET; x++) {
            for(int z = -1 - CHUNK_OFFSET; z < 2 + CHUNK_OFFSET; z++) {
                chunks[j++] = chunk.getWorld().getChunkAt(chunk.getX() + x, chunk.getZ() + z);
            }
        }

        return chunks;
    }

    public static boolean isAreaClaimed(@NotNull Chunk chunk) {
        return Arrays.stream(getAreaChunks(chunk)).anyMatch(ClaimAPI::isClaimed);
    }

    public static boolean isClaimed(@NotNull Chunk chunk) {
        return chunk.getPersistentDataContainer().has(CLAIMED_KEY, PersistentDataType.STRING);
    }

    public static String getClaimed(@NotNull Chunk chunk) {
        return chunk.getPersistentDataContainer().has(CLAIMED_KEY, PersistentDataType.STRING) ? chunk.getPersistentDataContainer().get(CLAIMED_KEY, PersistentDataType.STRING) : null;
    }
}