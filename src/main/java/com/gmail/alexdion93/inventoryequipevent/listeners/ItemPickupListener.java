package com.gmail.alexdion93.inventoryequipevent.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import com.gmail.alexdion93.inventoryequipevent.InventoryEquipEventPlugin;
import com.gmail.alexdion93.inventoryequipevent.events.UpdateCause;

public class ItemPickupListener implements Listener {

  // Variables
  private InventoryEquipEventPlugin plugin;

  /**
   * Constructor
   *
   * @param plugin The plugin instance.
   */
  public ItemPickupListener(InventoryEquipEventPlugin plugin) {
    this.plugin = plugin;
    plugin.getPluginManager().registerEvents(this, plugin);
  }

  /**
   * Listens for a player picking up an item.
   *
   * @param event The event being fired.
   */
  @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
  public void onItemPickup(EntityPickupItemEvent event) {
    // Skip if not the player
    if (event.getEntityType() != EntityType.PLAYER) {
      return;
    }

    // Variables
    Player player = (Player) event.getEntity();
    ItemStack item = event.getItem().getItemStack();
    int slot = player.getInventory().getHeldItemSlot();

    // Skip if one of these items already exists and that stack can hold this item.
    if (plugin.getMatchingSlot(player, item) != -1) {
      return;
    }

    // Skip if not the hotbar slot
    if (slot != plugin.getHotbarEmptySlot(player)) {
      return;
    }

    // Call Update
    plugin.updateOne(player, EquipmentSlot.HAND, item, UpdateCause.ITEM_PICKUP);
  }
}
