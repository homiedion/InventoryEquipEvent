package com.gmail.alexdion93.inventoryequipevent.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupArrowEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import com.gmail.alexdion93.inventoryequipevent.InventoryEquipEventPlugin;
import com.gmail.alexdion93.inventoryequipevent.events.UpdateCause;

public class ArrowPickupListener implements Listener {

  // Variables
  private InventoryEquipEventPlugin plugin;

  /**
   * Constructor
   *
   * @param plugin The plugin instance.
   */
  public ArrowPickupListener(InventoryEquipEventPlugin plugin) {
    this.plugin = plugin;
    plugin.getPluginManager().registerEvents(this, plugin);
  }

  /**
   * Listens for a player picking up an arrow or trident. TODO: The method
   * getItem() tied to this event's parent is considered DEPRECATED however it
   * hasn't been replaced. As a result this method shall be used until a
   * replacement exists.
   *
   * @param event The event being fired.
   */
  @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
  @SuppressWarnings("deprecation")
  public void onArrowPickup(PlayerPickupArrowEvent event) {
    // Variables
    Player player = event.getPlayer();
    ItemStack item = event.getItem().getItemStack();
    int slot = player.getInventory().getHeldItemSlot();

    // Skip if one of these items already exists and that stack can hold this item.
    if (plugin.getMatchingSlot(player, item) != -1) {
      return;
    }

    // Skip if the slot isn't equal to the held item slot.
    if (slot != plugin.getHotbarEmptySlot(player)) {
      return;
    }

    // Call Update
    plugin.updateOne(player, EquipmentSlot.HAND, item, UpdateCause.ARROW_PICKUP);
  }
}
