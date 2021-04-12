package com.gmail.alexdion93.inventoryequipevent.listeners;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.gmail.alexdion93.inventoryequipevent.InventoryEquipEventPlugin;
import com.gmail.alexdion93.inventoryequipevent.events.UpdateCause;
import com.gmail.alexdion93.inventoryequipevent.util.ItemUtil;

public class PlayerSpawnEntityListener implements Listener {

  // Variables
  private InventoryEquipEventPlugin plugin;

  /**
   * Constructor
   *
   * @param plugin The plugin instance
   */
  public PlayerSpawnEntityListener(InventoryEquipEventPlugin plugin) {
    this.plugin = plugin;
    plugin.getPluginManager().registerEvents(this, plugin);
  }

  /**
   * Listens for a player interacting with a block to spawn an entity
   *
   * @param event The event being fired.
   */
  @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
  public void onInteract(PlayerInteractEvent event) {
    // Skip if left click
    if ((event.getAction() == Action.LEFT_CLICK_AIR) || (event.getAction() == Action.LEFT_CLICK_BLOCK)) {
      return;
    }

    // Skip if the player is in creative mode
    if (event.getPlayer().getGameMode() == GameMode.CREATIVE) {
      return;
    }

    // Skip if null
    if (event.getItem() == null) {
      return;
    }

    // Skip if the item has more than 1 left.
    if (event.getItem().getAmount() > 1) {
      return;
    }

    // Variables
    final Player player = event.getPlayer();
    final ItemStack item = event.getItem();
    final EquipmentSlot slot = event.getHand();

    // Exit if not placeable item (item frame, painting, etc.) and not spawn egg
    if (!ItemUtil.isCategory(item, ItemUtil.PLACEABLES) && !ItemUtil.isCategory(item, ItemUtil.SPAWN_EGGS)) {
      return;
    }

    // Send an update to the main and off hand slot
    new BukkitRunnable() {
      @Override
      public void run() {
        ItemStack equip = ItemUtil.getItemFromSlot(player, slot);
        if (item == equip) {
          return;
        }
        plugin.updateOne(player, slot, equip, UpdateCause.INVENTORY_CLICK);
      }
    }.runTaskLater(plugin, 1L);
  }
}
