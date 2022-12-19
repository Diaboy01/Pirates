package net.novorex.pirates.api.claim;

import net.novorex.pirates.Main;
import net.novorex.pirates.api.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public final class ClaimNotifyHandler {

    private static final HashMap<Player, String> INSIDE = new HashMap<>();
    private static final FileWriter LOGGER;

    static {
        File logFile = new File("plugins/Novorex/ClaimLogs", String.format("logs-%s.txt", Utils.getDate().replace(".", "-")));

        try {
            new File("plugins/Novorex/ClaimLogs").mkdir();

            if(!logFile.exists()) {
                logFile.createNewFile();
            }

            LOGGER = new FileWriter(logFile, true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void init() {
        ClaimAPI.init();

        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.instance, () -> {
            for(Player player : Bukkit.getOnlinePlayers()) {
                String claimedCurrently = ClaimAPI.getClaimed(player.getLocation().getChunk());

                if(claimedCurrently != null) {
                    boolean a = false;

                    if(INSIDE.containsKey(player)) {
                        if(!INSIDE.get(player).equals(claimedCurrently)) {
                            INSIDE.put(player, claimedCurrently);
                            a = true;
                        }
                    } else {
                        INSIDE.put(player, claimedCurrently);
                        a = true;
                    }

                    if(a) {
                        String name = Bukkit.getOfflinePlayer(UUID.fromString(claimedCurrently)).getName();
                        player.sendMessage("Du hast das Claim Gebiet von " + (name == null ? "einem Spieler" : name) + " betreten.");

                        try {
                            if(!player.getName().equals(name)) {
                                LOGGER.write("[" + Utils.getFullDate() + "]" + player.getName() + " hat das Gebiet von " + name + " betreten.\n");
                                LOGGER.flush();
                            }
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                } else {
                    if(INSIDE.containsKey(player)) {
                        String name = Bukkit.getOfflinePlayer(UUID.fromString(INSIDE.remove(player))).getName();
                        player.sendMessage("Du hast das Claim Gebiet von " + (name == null ? "einem Spieler" : name) + " verlassen.");
                        INSIDE.remove(player);

                        try {
                            if(!player.getName().equals(name)) {
                                LOGGER.write("[" + Utils.getFullDate() + "]" + player.getName() + " hat das Gebiet von " + name + " verlassen.\n");
                                LOGGER.flush();
                            }
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }, 20, 20);
    }
}
