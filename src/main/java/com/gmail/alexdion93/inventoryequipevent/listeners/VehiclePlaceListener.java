package com.gmail.alexdion93.inventoryequipevent.listeners;

import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import com.gmail.alexdion93.inventoryequipevent.InventoryEquipEventPlugin;
import com.gmail.alexdion93.inventoryequipevent.events.UpdateCause;
import com.gmail.alexdion93.inventoryequipevent.util.ItemUtil;

public class VehiclePlaceListener implements Listener {

  // Variables
  private InventoryEquipEventPlugin plugin;

  /**
   * Constructor
   *
   * @param plugin The plugin instance
   */
  public VehiclePlaceListener(InventoryEquipEventPlugin plugin) {
    this.plugin = plugin;
    plugin.getPluginManager().registerEvents(this, plugin);
  }

  /**
   * Listens for a player placing a block.
   *
   * @param event The event being fired.
   */
  @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
  public void onBoneMealUse(PlayerInteractEvent event) {
    // Skip if left click
    if ((event.getAction() == Action.LEFT_CLICK_AIR) || (event.getAction() == Action.LEFT_CLICK_BLOCK)) {
      return;
    }

    // Variables
    Player player = event.getPlayer();
    EquipmentSlot slot = event.getHand();
    ItemStack item = event.getItem();

    // Ignore if creative
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

    // Skip if not vehicle
    if (!ItemUtil.isCategory(item, ItemUtil.MINECARTS) && !ItemUtil.isCategory(item, ItemUtil.BOATS)) {
      return;
    }

    // Skip if placing minecart
    if (ItemUtil.isCategory(item, ItemUtil.MINECARTS)) {
      Block target = player.getTargetBlockExact(4);

      if (target == null) {
        return;
      }
      if (!ItemUtil.isCategory(target.getType(), ItemUtil.MINECART_RAILS)) {
        return;
      }
    }

    // Call Update
    plugin.updateOne(player, slot, null, UpdateCause.VEHICLE_PLACE);
  }
}
