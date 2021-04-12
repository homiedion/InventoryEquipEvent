package com.gmail.alexdion93.inventoryequipevent.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import com.gmail.alexdion93.inventoryequipevent.InventoryEquipEventPlugin;
import com.gmail.alexdion93.inventoryequipevent.events.UpdateCause;

public class ItemSwapListener implements Listener {

  // Variables
  private InventoryEquipEventPlugin plugin;

  /**
   * Constructor
   *
   * @param plugin The plugin instance
   */
  public ItemSwapListener(InventoryEquipEventPlugin plugin) {
    this.plugin = plugin;
    plugin.getPluginManager().registerEvents(this, plugin);
  }

  /**
   * Listens for a player swapping their hand items.
   *
   * @param event The event being fired.
   */
  @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
  public void onItemSwap(PlayerSwapHandItemsEvent event) {
    // Variables
    Player player = event.getPlayer();
    EquipmentSlot slots[] = { EquipmentSlot.HAND, EquipmentSlot.OFF_HAND };
    ItemStack items[] = { event.getMainHandItem(), event.getOffHandItem() };

    // Skip if both items are empty
    if (((items[0] == null) || (items[0].getType() == Material.AIR))
        && ((items[1] == null) || (items[1].getType() == Material.AIR))) {
      return;
    }

    // Call Update
    plugin.updateSome(player, slots, items, UpdateCause.TOOL_SWAP);
  }
}
