package net.novorex.pirates.api;

import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.HttpURLConnection;
import java.util.function.Consumer;
import java.util.concurrent.Executors;
import java.util.HashMap;
import java.lang.reflect.Type;
import com.google.gson.GsonBuilder;
import java.util.concurrent.ExecutorService;
import java.util.UUID;
import java.util.Map;
import com.google.gson.Gson;

public class UUIDFetcher
{
    public static final long FEBRUARY_2015 = 1422748800000L;
    private static Gson gson;
    private static final String UUID_URL = "https://api.mojang.com/users/profiles/minecraft/%s?at=%d";
    private static final String NAME_URL = "https://api.mojang.com/user/profiles/%s/names";
    private static Map<String, UUID> uuidCache;
    private static Map<UUID, String> nameCache;
    private static ExecutorService pool;
    private String name;
    private UUID id;

    static {
        UUIDFetcher.gson = new GsonBuilder().registerTypeAdapter((Type)UUID.class, (Object)new UUIDTypeAdapter()).create();
        UUIDFetcher.uuidCache = new HashMap<String, UUID>();
        UUIDFetcher.nameCache = new HashMap<UUID, String>();
        UUIDFetcher.pool = Executors.newCachedThreadPool();
    }

    public static void insert(final String name, final UUID value) {
        if (!UUIDFetcher.uuidCache.containsKey(name)) {
            UUIDFetcher.uuidCache.put(name, value);
        }
        if (!UUIDFetcher.nameCache.containsKey(value)) {
            UUIDFetcher.nameCache.put(value, name);
        }
    }

    public static void getUUID(final String name, final Consumer<UUID> action) {
        UUIDFetcher.pool.execute(() -> action.accept(getUUID(name)));
    }

    public static UUID getUUID(final String name) {
        return getUUIDAt(name, System.currentTimeMillis());
    }

    public static void getUUIDAt(final String name, final long timestamp, final Consumer<UUID> action) {
        UUIDFetcher.pool.execute(() -> action.accept(getUUIDAt(name, timestamp)));
    }

    public static UUID getUUIDAt(String name, final long timestamp) {
        name = name.toLowerCase();
        if (UUIDFetcher.uuidCache.containsKey(name)) {
            return UUIDFetcher.uuidCache.get(name);
        }
        try {
            final HttpURLConnection connection = (HttpURLConnection)new URL(String.format("https://api.mojang.com/users/profiles/minecraft/%s?at=%d", name, timestamp / 1000L)).openConnection();
            connection.setReadTimeout(5000);
            final UUIDFetcher data = (UUIDFetcher)UUIDFetcher.gson.fromJson((Reader)new BufferedReader(new InputStreamReader(connection.getInputStream())), (Class)UUIDFetcher.class);
            if (data == null) {
                return null;
            }
            UUIDFetcher.uuidCache.put(name, data.id);
            UUIDFetcher.nameCache.put(data.id, data.name);
            return data.id;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void getName(final UUID uuid, final Consumer<String> action) {
        UUIDFetcher.pool.execute(() -> action.accept(getName(uuid)));
    }

    public static String getName(final UUID uuid) {
        if (UUIDFetcher.nameCache.containsKey(uuid) && UUIDFetcher.nameCache.get(uuid) != null) {
            return UUIDFetcher.nameCache.get(uuid);
        }
        try {
            final HttpURLConnection connection = (HttpURLConnection)new URL(String.format("https://api.mojang.com/user/profiles/%s/names", UUIDTypeAdapter.fromUUID(uuid))).openConnection();
            connection.setReadTimeout(5000);
            final UUIDFetcher[] nameHistory = (UUIDFetcher[])UUIDFetcher.gson.fromJson((Reader)new BufferedReader(new InputStreamReader(connection.getInputStream())), (Class)UUIDFetcher[].class);
            final UUIDFetcher currentNameData = nameHistory[nameHistory.length - 1];
            UUIDFetcher.uuidCache.put(currentNameData.name.toLowerCase(), uuid);
            UUIDFetcher.nameCache.put(uuid, currentNameData.name);
            return currentNameData.name;
        }
        catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
