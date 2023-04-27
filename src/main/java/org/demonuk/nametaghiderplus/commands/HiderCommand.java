package org.demonuk.nametaghiderplus.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.demonuk.nametaghiderplus.NametagHiderPlus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class HiderCommand extends CommandAbstract {


    public HiderCommand() {
        super("hider");
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        if (!sender.hasPermission("nametagHiderPlus.admin")) {
            sender.sendMessage(NametagHiderPlus.MAIN_PREFIX + Arrays.toString(" Вы не владете божественной магией, а потому не сможете применить эту команду.".getBytes(StandardCharsets.UTF_8)));
            return;
        }
        if (args.length == 0) {
            helpCommand(sender);
            return;
        }
        if (args[0].equalsIgnoreCase("reload")) {
            NametagHiderPlus.getInstance().reloadConfig();
            sender.sendMessage(NametagHiderPlus.MAIN_PREFIX + "§7] §fConfig reloaded!");
        } else if (args[0].equalsIgnoreCase("hide")) {
            Bukkit.getOnlinePlayers().forEach(player -> {
                if (NametagHiderPlus.getInstance().team.getEntries().contains(player.getName())) {
                    NametagHiderPlus.getInstance().team.addEntry(player.getName());
                    if (NametagHiderPlus.getInstance().debugMode) {
                        sender.sendMessage(NametagHiderPlus.MAIN_PREFIX + "§7] §fPlayer's " + player.getName() + " nickname is hidden!");
                    }
                }
            });
        } else if (args[0].equalsIgnoreCase("show")) {
            Bukkit.getOnlinePlayers().forEach(player -> {
                if (NametagHiderPlus.getInstance().team.getEntries().contains(player.getName())) {
                    NametagHiderPlus.getInstance().team.removeEntry(player.getName());
                    if (NametagHiderPlus.getInstance().debugMode) {
                        sender.sendMessage(NametagHiderPlus.MAIN_PREFIX + "§7] §fPlayer's " + player.getName() + " nickname is hidden!");
                    }
                }
            });
        }
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            return null;
        }
        if (args.length == 1) {
            List<String> completions = new ArrayList<>();
            completions.add("reload");
            completions.add("hide");
            completions.add("show");
            return completions.stream()
                    .filter(completion -> completion.startsWith(args[0]))
                    .collect(Collectors.toList());
        } else if (args.length == 2 && args[0].equalsIgnoreCase("reset")) {
            return Bukkit.getOnlinePlayers().stream()
                    .map(Player::getName)
                    .filter(name -> name.startsWith(args[1]))
                    .collect(Collectors.toList());
        }
        return null;
    }

    private void helpCommand(CommandSender sender) {
        sender.sendMessage(NametagHiderPlus.MAIN_PREFIX + "§7] §fКоманды:");
        sender.sendMessage(NametagHiderPlus.MAIN_PREFIX + "§7] §f/hider reload - перезагрузить плагин");
        sender.sendMessage(NametagHiderPlus.MAIN_PREFIX + "§7] §f/hider hide - скрыть ники игроков");
        sender.sendMessage(NametagHiderPlus.MAIN_PREFIX + "§7] §f/hider hide - скрыть ники игроков");
    }
}