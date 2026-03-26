package org.nightcat.elytraPlugin;

import org.bukkit.plugin.java.JavaPlugin;

public final class ElytraPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new DragonDeathListener(this), this);
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
