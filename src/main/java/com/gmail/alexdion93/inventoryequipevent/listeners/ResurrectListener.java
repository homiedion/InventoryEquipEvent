package com.gmail.alexdion93.inventoryequipevent.listeners;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import com.gmail.alexdion93.inventoryequipevent.InventoryEquipEventPlugin;
import com.gmail.alexdion93.inventoryequipevent.events.UpdateCause;

public class ResurrectListener implements Listener {

  // Variables
  private InventoryEquipEventPlugin plugin;

  /**
   * Constructor
   *
   * @param plugin The plugin instance
   */
  public ResurrectListener(InventoryEquipEventPlugin plugin) {
    this.plugin = plugin;
    plugin.getPluginManager().registerEvents(this, plugin);
  }

  /**
   * Listens for a player resurrecting.
   *
   * @param event The event being fired.
   */
  @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
  public void onResurrect(EntityResurrectEvent event) {
    // Skip if not a player
    if (event.getEntityType() != EntityType.PLAYER) {
      return;
    }

    // Variables
    Player player = (Player) event.getEntity();
    ItemStack item = player.getInventory().getItemInMainHand();
    EquipmentSlot slot = ((item != null) && (item.getType() == Material.TOTEM_OF_UNDYING) ? EquipmentSlot.HAND
        : EquipmentSlot.OFF_HAND);

    // Skip if more than 1 item
    if (item.getAmount() > 1) {
      return;
    }

    // Call Update
    plugin.updateOne(player, slot, null, UpdateCause.RESURRECT);
  }
}
