package net.novorex.pirates.api;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

public class PlayerWorldTimings {

    public static final long TIME_LIMIT = 1000 * 60 * 30; // 30 min.

    private static final HashMap<UUID, PlayerWorldTimings> cache = new HashMap<>();

    public static PlayerWorldTimings getTimings(UUID uuid) {
        if(cache.containsKey(uuid)) {
            return cache.get(uuid);
        }

        String date = Utils.getDate();
        File generalFile = new File("plugins/Novorex/General/TimePlayed/", date + ".yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(generalFile);

        return createEntry(uuid, config.getLong(uuid.toString()));
    }

    public static void dispose(UUID uuid) {
        cache.remove(uuid);
    }

    public static PlayerWorldTimings createEntry(UUID uuid, long timeSpend) {
        PlayerWorldTimings playerWorldTimings = new PlayerWorldTimings(uuid, timeSpend);
        cache.put(uuid, playerWorldTimings);
        return playerWorldTimings;
    }

    private long timeSpend;
    private UUID uuid;

    private long timing;
    private boolean started;

    private PlayerWorldTimings(UUID uuid, long startTime) {
        this.uuid = uuid;
        this.timeSpend = startTime;

        this.timing = 0L;
        this.started = false;
    }

    public UUID getUniqueId() {
        return this.uuid;
    }

    public void startCounting() {
        this.started = true;
        this.timing = System.currentTimeMillis();
    }

    public void stopCounting() {
        this.started = false;
        this.timeSpend += System.currentTimeMillis() - this.timing;
        this.timing = 0L;
    }

    public void clearTime() {
        this.timeSpend = 0L;
    }

    public long getTimeSpend() {
        if(this.started) {
            this.timeSpend += System.currentTimeMillis() - this.timing;
            this.timing = System.currentTimeMillis();
        }

        return this.timeSpend;
    }

    public boolean exceedsTimeLimit(long timeLimit) {
        return getTimeSpend() >= timeLimit;
    }

    public boolean isCounting() {
        return this.started;
    }

    public void appendData(long timeSpend) {
        this.timeSpend = timeSpend;
    }
}
