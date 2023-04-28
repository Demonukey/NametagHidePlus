package org.demonuk.nametaghiderplus;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.demonuk.nametaghiderplus.commands.HiderCommand;

import java.io.File;
import java.util.Objects;

public final class NametagHiderPlus extends JavaPlugin {

    public Scoreboard scoreboard;
    public Team team;


    public boolean debugMode;

    public boolean hideNicknames;

    public static NametagHiderPlus instance;

    public NametagHiderPlus() {
        instance = this;
    }

    public static NametagHiderPlus getInstance() {
        return instance;
    }
    public static final String MAIN_PREFIX = ChatColor.GRAY + "[" + ChatColor.DARK_GREEN + "NametagHider+" + ChatColor.GRAY + "]";

    public static void tellConsole(String message) {
        Bukkit.getConsoleSender().sendMessage(message);
    }
    @Override
    public void onEnable() {
        instance = this;
        // Plugin startup logic
        new HiderCommand();
        getServer().getPluginManager().registerEvents(new Hider(), this);
    tellConsole("NametagHider+ is loaded successfully!");
        File configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            tellConsole(MAIN_PREFIX + " Where the config at?");
            saveDefaultConfig();
        } else {
            debugModeCheck();
        }
        scoreboard = getServer().getScoreboardManager().getMainScoreboard();
        if (!scoreboard.getTeams().contains(scoreboard.getTeam("NameTagHider+"))) {
            team = scoreboard.registerNewTeam("NameTagHider+");
            team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.NEVER);
        } else
            team = scoreboard.getTeam("NameTagHider+");
        if (Objects.requireNonNull(getConfig().getString("hideNicknames")).equalsIgnoreCase("true")) {
            tellConsole(MAIN_PREFIX + " Hide nicknames is enabled!");
            team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.NEVER);
            hideNicknames = true;
        } else if (Objects.requireNonNull(getConfig().getString("hideNicknames")).equalsIgnoreCase("false")) {
            tellConsole(MAIN_PREFIX + " Hide nicknames is disabled!");
            team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);
            hideNicknames = false;
        } else {
            tellConsole(MAIN_PREFIX + " Error while checking for hide nicknames!");
            tellConsole(MAIN_PREFIX + " Hide nicknames automatically disabled for further investigation!");
        }
    }

    @Override
    public void onDisable() {
        tellConsole(MAIN_PREFIX + " E");
    }
    private void debugModeCheck() {
        try {
            if (Objects.requireNonNull(getConfig().getString("debug")).equalsIgnoreCase("true")) {
                tellConsole(MAIN_PREFIX + " Debug mode is enabled!");
                debugMode = true;
            } else if (Objects.requireNonNull(getConfig().getString("debug")).equalsIgnoreCase("false")) {
                tellConsole(MAIN_PREFIX + " Debug mode is disabled!");
                debugMode = false;
            } else throw (new Exception());
        } catch (Exception e) {
            tellConsole(MAIN_PREFIX + " Error while checking for debug mode!");
            tellConsole(MAIN_PREFIX + " Debug mode automatically enabled for further investigation!");
            tellConsole(MAIN_PREFIX + " Exception: " + e);
            debugMode = true;
        }
    }
}
