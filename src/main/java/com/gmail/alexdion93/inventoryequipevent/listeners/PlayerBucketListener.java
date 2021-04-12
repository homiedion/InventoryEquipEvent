package com.gmail.alexdion93.inventoryequipevent.listeners;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.gmail.alexdion93.inventoryequipevent.InventoryEquipEventPlugin;
import com.gmail.alexdion93.inventoryequipevent.events.UpdateCause;

public class PlayerBucketListener implements Listener {

  // Variables
  private InventoryEquipEventPlugin plugin;

  /**
   * Constructor
   *
   * @param plugin The plugin instance
   */
  public PlayerBucketListener(InventoryEquipEventPlugin plugin) {
    this.plugin = plugin;
    plugin.getPluginManager().registerEvents(this, plugin);
  }

  /**
   * Listens for a player changing a bucket. NOTE: You cannot register
   * PlayerBucketEvent and must instead use its sub events.
   *
   * @param event The event being fired.
   */
  public void onBucketChange(PlayerBucketEvent event) {
    // Variables
    Player player = event.getPlayer();
    PlayerInventory inventory = player.getInventory();
    EquipmentSlot slot = null;
    ItemStack item = event.getItemStack();

    // Ignore if creative
    if (player.getGameMode() == GameMode.CREATIVE) {
      return;
    }

    // Get the hand being targetted
    if ((inventory.getItemInMainHand() != null) && (inventory.getItemInMainHand().getType() == event.getBucket())) {
      slot = EquipmentSlot.HAND;
    } else {
      slot = EquipmentSlot.OFF_HAND;
    }

    // Skip if null
    if (item == null) {
      return;
    }

    // Skip if more than 1 item left
    if (item.getAmount() > 1) {
      return;
    }

    // Call Update
    plugin.updateOne(player, slot, item, UpdateCause.BUCKET_CHANGE);
  }

  /**
   * Listens for a player emptying a bucket
   *
   * @param event The event being fired.
   */
  @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
  public void onBucketEmpty(PlayerBucketEmptyEvent event) {
    onBucketChange(event);
  }

  /**
   * Listens for a player filling a bucket
   *
   * @param event The event being fired.
   */
  @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
  public void onBucketFill(PlayerBucketFillEvent event) {
    onBucketChange(event);
  }
}
