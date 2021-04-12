package com.gmail.alexdion93.inventoryequipevent.listeners;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import com.gmail.alexdion93.inventoryequipevent.InventoryEquipEventPlugin;
import com.gmail.alexdion93.inventoryequipevent.events.UpdateCause;

public class BlockPlaceListener implements Listener {

  // Variables
  private InventoryEquipEventPlugin plugin;

  /**
   * Constructor
   *
   * @param plugin The plugin instance.
   */
  public BlockPlaceListener(InventoryEquipEventPlugin plugin) {
    this.plugin = plugin;
    plugin.getPluginManager().registerEvents(this, plugin);
  }

  /**
   * Listens for a player placing a block.
   *
   * @param event The event being fired.
   */
  @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
  public void onBlockPlace(BlockPlaceEvent event) {
    Player player = event.getPlayer();
    ItemStack item = event.getItemInHand();
    EquipmentSlot slot = (player.getInventory().getItemInMainHand().isSimilar(item) ? EquipmentSlot.HAND
        : EquipmentSlot.OFF_HAND);

    if (player.getGameMode() == GameMode.CREATIVE) {
      return;
    }

    // Skip if null
    if (item == null) {
      return;
    }

    // Skip if more than 1 item left
    if (item.getAmount() > 1) {
      return;
    }

    // Skip if not a block being placed (stripping logs, making grass path)
    if (!item.getType().isBlock()) {
      return;
    }

    // Call Update
    plugin.updateOne(player, slot, null, UpdateCause.BLOCK_PLACE);
  }
}
