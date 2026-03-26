package org.nightcat.elytraPlugin;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class ElytraPlugin extends JavaPlugin {

    private DragonDeathListener dragonDeathListener;

    @Override
    public void onEnable() {
        dragonDeathListener = new DragonDeathListener(this);
        getServer().getPluginManager().registerEvents(dragonDeathListener, this);

        PluginCommand elytraDropRateCommand = getCommand("elytradroprate");
        if (elytraDropRateCommand == null) {
            getLogger().severe("Command 'elytradroprate' is not defined in plugin.yml.");
        } else {
            ElytraDropRateCommand commandHandler = new ElytraDropRateCommand(dragonDeathListener);
            elytraDropRateCommand.setExecutor(commandHandler);
            elytraDropRateCommand.setTabCompleter(commandHandler);
        }

        // Plugin startup logic
        if (getDescription() != null) {
            getLogger().info(getDescription().getName() + " v" + getDescription().getVersion() + " enabled");
        } else {
            getLogger().info("ElytraPlugin enabled");
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        if (getDescription() != null) {
            getLogger().info(getDescription().getName() + " v" + getDescription().getVersion() + " disabled");
        } else {
            getLogger().info("ElytraPlugin disabled");
        }
    }
}
