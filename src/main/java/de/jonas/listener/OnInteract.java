package de.jonas.listener;

import de.jonas.util.Methods;
import de.jonas.worldeditor.WorldEditor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class OnInteract implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        if (p.hasPermission("worldeditor")) {
            if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (p.getItemInHand().getType() == Material.DIAMOND_AXE) {
                    if (e.getClickedBlock() != null) {
                        e.setCancelled(true);
                        Methods.location2.put(p, e.getClickedBlock().getLocation());
                        Methods.sendLavaParticles(e.getClickedBlock().getLocation());
                        p.sendMessage(WorldEditor.prefix + "Location 2 Set!");
                    }
                }
            } else if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
                if (p.getItemInHand().getType() == Material.DIAMOND_AXE) {
                    e.setCancelled(true);
                    Methods.location1.put(p, e.getClickedBlock().getLocation());
                    Methods.sendLavaParticles(e.getClickedBlock().getLocation());
                    p.sendMessage(WorldEditor.prefix + "Location 1 Set!");
                }
            }
        }
    }

}
