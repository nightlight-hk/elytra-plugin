package org.nightcat.elytraPlugin;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public final class ElytraDropRateCommand implements CommandExecutor, TabCompleter {

    private final DragonDeathListener dragonDeathListener;
    private final List<String> suggestedChances = List.of("0", "0.25", "0.5", "0.75", "1");

    public ElytraDropRateCommand(DragonDeathListener dragonDeathListener) {
        this.dragonDeathListener = dragonDeathListener;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player && !((Player) sender).isOp()) {
            sender.sendMessage("You must be OP to use this command.");
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage("Usage: /" + label + " <0.0-1.0>");
            return true;
        }

        double newDropRate;
        try {
            newDropRate = Double.parseDouble(args[0]);
        } catch (NumberFormatException exception) {
            sender.sendMessage("Invalid number. Use a value between 0.0 and 1.0.");
            return true;
        }

        if (newDropRate < 0.0D || newDropRate > 1.0D) {
            sender.sendMessage("Drop rate must be between 0.0 and 1.0.");
            return true;
        }

        dragonDeathListener.setElytraDropRate(newDropRate);
        sender.sendMessage("Elytra drop rate set to " + newDropRate + ".");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length > 1) {
            return Collections.emptyList();
        }
        String lastArg = args[args.length - 1].toLowerCase();
        return suggestedChances.stream()
                .filter(option -> option.toLowerCase().startsWith(lastArg))
                .collect(Collectors.toList());
    }
}

