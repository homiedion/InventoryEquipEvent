package com.gmail.alexdion93.inventoryequipevent.listeners;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.ThrowableProjectile;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.projectiles.ProjectileSource;

import com.gmail.alexdion93.inventoryequipevent.InventoryEquipEventPlugin;
import com.gmail.alexdion93.inventoryequipevent.events.UpdateCause;

public class ProjectileThrowListener implements Listener {

  // Variables
  private InventoryEquipEventPlugin plugin;

  /**
   * Constructor
   *
   * @param plugin The plugin instance
   */
  public ProjectileThrowListener(InventoryEquipEventPlugin plugin) {
    this.plugin = plugin;
    plugin.getPluginManager().registerEvents(this, plugin);
  }

  /**
   * Listens for a player launching a projectile.
   *
   * @param event The event being fired.
   */
  @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
  public void onProjectileThrow(ProjectileLaunchEvent event) {

    // Variables
    Projectile projectile = event.getEntity();
    ProjectileSource shooter = projectile.getShooter();
    Player player = null;

    // Skip if not a player shooting
    if (!(shooter instanceof Player)) {
      return;
    }
    player = (Player) shooter;

    // Skip if the player is in creative.
    if (player.getGameMode() == GameMode.CREATIVE) {
      return;
    }

    // Variables
    Material type = null;
    ItemStack item = null;
    EquipmentSlot slot = null;

    // Attempt to fetch the material type of the item
    if (projectile instanceof ThrowableProjectile) {
      type = ((ThrowableProjectile) projectile).getItem().getType();
    } else if (projectile instanceof ThrownPotion) {
      type = ((ThrownPotion) projectile).getItem().getType();
    } else if (projectile instanceof Firework) {
      type = Material.FIREWORK_ROCKET;
    } else if (projectile.getType() == EntityType.ENDER_SIGNAL) {
      type = Material.ENDER_EYE;
    } else if (projectile.getType() == EntityType.TRIDENT) {
      type = Material.TRIDENT;
    }

    // Skip if no type
    if (type == null) {
      return;
    }

    // Fetch the correct item
    if (player.getInventory().getItemInMainHand().getType() == type) {
      item = player.getInventory().getItemInMainHand();
      slot = EquipmentSlot.HAND;
    } else if (player.getInventory().getItemInOffHand().getType() == type) {
      item = player.getInventory().getItemInOffHand();
      slot = EquipmentSlot.OFF_HAND;
    }

    // Skip if null
    if ((item == null) || (slot == null)) {
      return;
    }

    // Skip if more than 1 item in the appropriate hand.
    if (item.getAmount() > 1) {
      return;
    }

    // Call Update
    plugin.updateOne(player, slot, null, UpdateCause.PROJECTILE_THROW);
  }
}
