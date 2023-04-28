package org.demonuk.nametaghiderplus.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.scoreboard.Team;
import org.demonuk.nametaghiderplus.NametagHiderPlus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.demonuk.nametaghiderplus.NametagHiderPlus.instance;


public class HiderCommand extends CommandAbstract {


    public HiderCommand() {
        super("nthider");
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
            sender.sendMessage(NametagHiderPlus.MAIN_PREFIX + " §fConfig reloaded!");
        } else if (args[0].equalsIgnoreCase("hide")) {
            if (instance.hideNicknames) {
                sender.sendMessage(NametagHiderPlus.MAIN_PREFIX + " §fNicknames are already hidden!");
                return;
            }
            sender.sendMessage(NametagHiderPlus.MAIN_PREFIX + " §fNicknames are now hidden!");
            instance.hideNicknames = true;
            instance.team.setOption(org.bukkit.scoreboard.Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.NEVER);
        } else if (args[0].equalsIgnoreCase("show")) {
            if (!instance.hideNicknames) {
                sender.sendMessage(NametagHiderPlus.MAIN_PREFIX + " §fNicknames are already shown!");
                return;
            }
            sender.sendMessage(NametagHiderPlus.MAIN_PREFIX + " §fNicknames are now shown!");
            instance.hideNicknames = false;
            instance.team.setOption(org.bukkit.scoreboard.Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);
        }
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            List<String> completions = new ArrayList<>();
            completions.add("hide");
            completions.add("reload");
            completions.add("show");
            return completions.stream()
                    .filter(completion -> completion.startsWith(args[0]))
                    .collect(Collectors.toList());
        }
        return null;
    }

    private void helpCommand(CommandSender sender) {
        sender.sendMessage(NametagHiderPlus.MAIN_PREFIX + " §fКоманды:");
        sender.sendMessage(NametagHiderPlus.MAIN_PREFIX + " §f/hider reload - перезагрузить плагин");
        sender.sendMessage(NametagHiderPlus.MAIN_PREFIX + " §f/hider hide - скрывать ники игроков");
        sender.sendMessage(NametagHiderPlus.MAIN_PREFIX + " §f/hider show - показывать ники игроков");
    }
}