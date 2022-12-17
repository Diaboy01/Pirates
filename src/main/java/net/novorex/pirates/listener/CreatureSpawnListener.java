package net.novorex.pirates.listener;

import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class CreatureSpawnListener implements Listener {

    @EventHandler
    public void onCreatureSpawnEvent(CreatureSpawnEvent event) {
        EntityType type = event.getEntity().getType();
        if (
                type == EntityType.PHANTOM ||
                type == EntityType.CREEPER ||
                type == EntityType.ENDERMAN
        ) {
            event.getEntity().remove();
        }
    }
}
