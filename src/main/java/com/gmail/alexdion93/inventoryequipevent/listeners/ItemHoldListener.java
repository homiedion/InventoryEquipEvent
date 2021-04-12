package com.gmail.alexdion93.inventoryequipevent.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.gmail.alexdion93.inventoryequipevent.InventoryEquipEventPlugin;
import com.gmail.alexdion93.inventoryequipevent.events.UpdateCause;

public class ItemHoldListener implements Listener {

  // Variables
  private InventoryEquipEventPlugin plugin;

  /**
   * Constructor
   *
   * @param plugin The plugin instance
   */
  public ItemHoldListener(InventoryEquipEventPlugin plugin) {
    this.plugin = plugin;
    plugin.getPluginManager().registerEvents(this, plugin);
  }

  /**
   * Listens for a player swap their held item.
   *
   * @param event The event being fired.
   */
  @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
  public void onItemHold(PlayerItemHeldEvent event) {
    // Variables
    Player player = event.getPlayer();
    EquipmentSlot slot = EquipmentSlot.HAND;
    PlayerInventory inventory = player.getInventory();
    ItemStack newItem = inventory.getItem(event.getNewSlot());
    ItemStack oldItem = inventory.getItem(event.getPreviousSlot());

    // Skip if both items are null
    if ((newItem == null) && (oldItem == null)) {
      return;
    }

    // Call Update
    plugin.updateOne(player, slot, newItem, UpdateCause.TOOL_CHANGE);
  }

}
