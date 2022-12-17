package net.novorex.pirates.listener;


import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class PlayerMotdListener implements Listener {

    @EventHandler
    public void onServerPing(ServerListPingEvent event) {
        event.setMaxPlayers(100);
        event.setMotd("§e➠§e§lEMPIRE OF PIRATES §f2§r \n§6by Novorex.net / §6§oSponsor: Nitrado.net");
    }
}
