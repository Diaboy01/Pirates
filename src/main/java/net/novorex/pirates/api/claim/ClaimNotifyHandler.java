package net.novorex.pirates.api.claim;

import net.novorex.pirates.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public final class ClaimNotifyHandler {

    private static final HashMap<Player, String> INSIDE = new HashMap<>();

    public static void init() {
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
                        player.sendMessage("Du hast das Gebiet von " + (name == null ? "Unbekannt" : name) + " betreten.");
                    }
                } else {
                    if(INSIDE.containsKey(player)) {
                        String name = Bukkit.getOfflinePlayer(UUID.fromString(INSIDE.remove(player))).getName();
                        player.sendMessage("Du hast das Gebiet von " + (name == null ? "Unbekannt" : name) + " verlassen.");
                        INSIDE.remove(player);
                    }
                }
            }
        }, 20, 20);
    }
}
