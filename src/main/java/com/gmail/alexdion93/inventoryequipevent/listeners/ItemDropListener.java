package com.gmail.alexdion93.inventoryequipevent.listeners;

import java.util.HashSet;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import com.gmail.alexdion93.inventoryequipevent.InventoryEquipEventPlugin;
import com.gmail.alexdion93.inventoryequipevent.events.UpdateCause;
import com.gmail.alexdion93.inventoryequipevent.util.ItemUtil;

public class ItemDropListener implements Listener {

  // Variables
  private InventoryEquipEventPlugin plugin;

  /**
   * Constructor
   *
   * @param plugin The plugin instance
   */
  public ItemDropListener(InventoryEquipEventPlugin plugin) {
    this.plugin = plugin;
    plugin.getPluginManager().registerEvents(this, plugin);
  }

  /**
   * Listens for a player dropping an item and updates all empty slots. NOTE: When
   * this event is thrown the item has already been removed from the player making
   * it impossible to detect.
   *
   * @param event The event being fired.
   */
  @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
  public void onItemDrop(PlayerDropItemEvent event) {
    // Variables
    Player player = event.getPlayer();
    HashSet<EquipmentSlot> slots = new HashSet<EquipmentSlot>();

    // Add all slots that are being updated.
    for (EquipmentSlot slot : EquipmentSlot.values()) {
      ItemStack item = ItemUtil.getItemFromSlot(player, slot);
      if ((item == null) || (item.getType() == Material.AIR)) {
        slots.add(slot);
      }
    }

    // Skip if no slots
    if (slots.isEmpty()) {
      return;
    }

    // Call Update on empty slots
    plugin.updateSome(player, slots.toArray(new EquipmentSlot[slots.size()]), UpdateCause.ITEM_DROP);
  }
}
