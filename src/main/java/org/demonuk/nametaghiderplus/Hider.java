package org.demonuk.nametaghiderplus;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import static org.demonuk.nametaghiderplus.NametagHiderPlus.*;

public class Hider implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (getInstance().debugMode) {
            try {
                getInstance().team.addEntry(player.getName());
            } catch (Exception e) {
                tellConsole(MAIN_PREFIX + " Error while adding player to team!");
                tellConsole(MAIN_PREFIX + " Exception: " + e);
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (!getInstance().debugMode) {
            getInstance().team.removeEntry(player.getName());
    } else {
        try {
            getInstance().team.removeEntry(event.getPlayer().getName());
        } catch (Exception e) {
                tellConsole(MAIN_PREFIX + " Error while removing player from team!");
                tellConsole(MAIN_PREFIX + " Exception: " + e);
            }
        }
    }
}
