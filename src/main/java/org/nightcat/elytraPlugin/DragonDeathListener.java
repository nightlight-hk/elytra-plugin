package org.nightcat.elytraPlugin;

import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.boss.DragonBattle;
import org.bukkit.entity.EnderDragon;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public final class DragonDeathListener implements Listener {

    private final JavaPlugin plugin;
    private double elytraDropRate = 0.25D;

    public DragonDeathListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public double getElytraDropRate() {
        return elytraDropRate;
    }

    public void setElytraDropRate(double elytraDropRate) {
        if (elytraDropRate < 0.0D || elytraDropRate > 1.0D) {
            throw new IllegalArgumentException("Elytra drop rate must be between 0 and 1.");
        }
        this.elytraDropRate = elytraDropRate;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (!(event.getEntity() instanceof EnderDragon)) {
            return;
        }

        World world = event.getEntity().getWorld();
        if (world.getEnvironment() != World.Environment.THE_END) {
            return;
        }

        double roll = ThreadLocalRandom.current().nextDouble();
        boolean shouldDrop = roll < elytraDropRate;
        plugin.getLogger().info("Elytra roll in world '" + world.getName() + "': " + roll
                + " (threshold " + elytraDropRate + ") -> " + (shouldDrop ? "SPAWN" : "NO_SPAWN"));

        if (!shouldDrop) {
            return;
        }

        DragonBattle dragonBattle = world.getEnderDragonBattle();
        if (dragonBattle == null || dragonBattle.getEndPortalLocation() == null) {
            plugin.getLogger().warning("Could not determine End portal location for Elytra drop.");
            return;
        }

        Location dropLocation = dragonBattle.getEndPortalLocation().clone().add(0.0D, 5.0D, 0.0D);
        plugin.getLogger().info("Spawning ELYTRA at " + world.getName() + " ["
                + dropLocation.getBlockX() + ", " + dropLocation.getBlockY() + ", " + dropLocation.getBlockZ() + "]");
        world.dropItem(dropLocation, new ItemStack(Material.ELYTRA));
    }
}

