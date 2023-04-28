package org.demonuk.nametaghiderplus;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import static org.demonuk.nametaghiderplus.NametagHiderPlus.*;

public class Hider implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (instance.team.getEntries().contains(player.getName())) {
            return;
        }
        if (getInstance().debugMode) {
            try {
                getInstance().team.addEntry(player.getName());
                tellConsole(MAIN_PREFIX + " Added player " + player.getName()+ " to the team!");
            } catch (Exception e) {
                tellConsole(MAIN_PREFIX + " Error while adding player to team!");
                tellConsole(MAIN_PREFIX + " Exception: " + e);
            }
        } else {
            getInstance().team.addEntry(player.getName());
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (!getInstance().team.getEntries().contains(player.getName())) {
            return;
        }
        if (!getInstance().debugMode) {
            getInstance().team.removeEntry(player.getName());
            tellConsole(MAIN_PREFIX + " Removed player " + player.getName()+ " from the team!");
    } else {
        try {
            getInstance().team.removeEntry(event.getPlayer().getName());
        } catch (Exception e) {
                tellConsole(MAIN_PREFIX + " Error while removing player from team!");
                tellConsole(MAIN_PREFIX + " Exception: " + e);
            }
        }
    }

    @EventHandler
    public void onPlayerReveal(PlayerInteractEntityEvent event) {
        if (!(event.getRightClicked() instanceof Player Interacted)) {
        return;
        }
        Player player = event.getPlayer();
        player.sendActionBar(Component.text(Interacted.getName()));

    }
}
