package org.demonuk.nametaghiderplus.commands;

import org.bukkit.command.*;
import org.jetbrains.annotations.NotNull;

import static org.demonuk.nametaghiderplus.NametagHiderPlus.instance;

public abstract class CommandAbstract implements CommandExecutor, TabCompleter {

    public CommandAbstract(String command) {
        PluginCommand pluginCommand = instance.getCommand(command);
        if(pluginCommand != null) {
            pluginCommand.setExecutor(this);
        }
    }

    public abstract void execute(CommandSender sender, String label, String[] args);

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        execute(sender, label, args);
        return true;
    }
}