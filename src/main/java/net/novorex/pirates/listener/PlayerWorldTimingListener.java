package net.novorex.pirates.listener;

import net.novorex.pirates.Main;
import net.novorex.pirates.api.PlayerWorldTimings;
import net.novorex.pirates.api.Utils;
import net.novorex.pirates.api.YAMLTimePlayed;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.text.SimpleDateFormat;


public class PlayerWorldTimingListener implements Listener {


    //static File generalFile1 = new File("plugins/Novorex/General/","Bauwelt.yml");
    //static YamlConfiguration config1 = YamlConfiguration.loadConfiguration(generalFile1);
    private static final String Bauwelt = "world";

    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("mm:ss");

    static {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.instance, new Runnable() {

            String date = Utils.getDate();

            @Override
            public void run() {
                String newDate = Utils.getDate();
                if(!date.equals(newDate)) {
                    for(Player player : Bukkit.getOnlinePlayers()) {
                        PlayerWorldTimings playerWorldTimings = PlayerWorldTimings.getTimings(player.getUniqueId());
                        playerWorldTimings.stopCounting();
                        playerWorldTimings.appendData(0L);
                        playerWorldTimings.startCounting();
                    }

                    date = newDate;
                }

                for(Player player : Bukkit.getOnlinePlayers()) {
                    PlayerWorldTimings playerWorldTimings = PlayerWorldTimings.getTimings(player.getUniqueId());
                    Location loc = player.getLocation();
                    double x = Math.abs(loc.getX());
                    double z = Math.abs(loc.getZ());

                    if(!(player.hasPermission("empire.admin") || player.hasPermission("empire.notime"))) {
                        if (x > 1000 || z > 1000) {
                            if(!playerWorldTimings.isCounting()) {
                                playerWorldTimings.startCounting();
                            }

                            long time = PlayerWorldTimings.TIME_LIMIT - playerWorldTimings.getTimeSpend();

                            if (time < 500) {
                                player.sendMessage("Deine Farmzeit ist für heute aufgebraucht!");
                                player.teleport(Bukkit.getWorld(Bauwelt).getSpawnLocation());
                            } else {
                                player.sendActionBar("Verbleibende Farmzeit: §a" + FORMAT.format(time));
                            }

                            if(playerWorldTimings.exceedsTimeLimit(PlayerWorldTimings.TIME_LIMIT)) {
                                player.teleport(Bukkit.getWorld(Bauwelt).getSpawnLocation());

                                if(playerWorldTimings.isCounting()) {
                                    playerWorldTimings.stopCounting();
                                }
                            }
                        } else {
                            if (playerWorldTimings.isCounting()) {
                                playerWorldTimings.stopCounting();
                            }
                        }
                    }
                }
            }
        }, 20, 20);
    }


    @EventHandler
    public void onPlayerChangedWorld(final PlayerChangedWorldEvent event) {
        final Player player = event.getPlayer();
        World worldTo = event.getPlayer().getWorld();

        PlayerWorldTimings playerWorldTimings = PlayerWorldTimings.getTimings(player.getUniqueId());
        if(!(player.hasPermission("empire.admin") || player.hasPermission("empire.notime"))) {
            if (!worldTo.getName().equals(Bauwelt)) { //worldFrom.getName().equals(Bauwelt)
                if (!playerWorldTimings.isCounting()) {
                    playerWorldTimings.startCounting();
                }
            } else {
                if(playerWorldTimings.exceedsTimeLimit(PlayerWorldTimings.TIME_LIMIT)) {
                    player.teleport(Bukkit.getWorld(Bauwelt).getSpawnLocation());
                    player.sendMessage("Deine Farmzeit ist für heute aufgebraucht!");
                } else {
                    if (playerWorldTimings.isCounting()) {
                        playerWorldTimings.stopCounting();
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerTeleport(final PlayerTeleportEvent event) {
        final Player player = event.getPlayer();
        Location loc = player.getLocation();
        double x = Math.abs(loc.getX());
        double z = Math.abs(loc.getZ());

        PlayerWorldTimings playerWorldTimings = PlayerWorldTimings.getTimings(player.getUniqueId());
        if(!(player.hasPermission("empire.admin") || player.hasPermission("empire.notime"))) {
            if (x > 1000 || z > 1000) {
                if(playerWorldTimings.exceedsTimeLimit(PlayerWorldTimings.TIME_LIMIT)) {
                    event.setCancelled(true);
                } else {
                    if (!playerWorldTimings.isCounting()) {
                        playerWorldTimings.startCounting();
                    }
                }
            } else {
                if (playerWorldTimings.isCounting()) {
                    playerWorldTimings.stopCounting();
                }
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        String date = Utils.getDate();

        PlayerWorldTimings playerWorldTimings = PlayerWorldTimings.getTimings(player.getUniqueId());
        if(playerWorldTimings.isCounting()) {playerWorldTimings.stopCounting();}
        YAMLTimePlayed.printTime(date, player.getUniqueId().toString(), playerWorldTimings.getTimeSpend());

        PlayerWorldTimings.dispose(player.getUniqueId());

    }
}
