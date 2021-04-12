package com.gmail.alexdion93.inventoryequipevent.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.gmail.alexdion93.inventoryequipevent.InventoryEquipEventPlugin;
import com.gmail.alexdion93.inventoryequipevent.events.UpdateCause;

public class ItemConsumeListener implements Listener {

  // Variables
  private InventoryEquipEventPlugin plugin;

  /**
   * Constructor
   *
   * @param plugin The plugin instance.
   */
  public ItemConsumeListener(InventoryEquipEventPlugin plugin) {
    this.plugin = plugin;
    plugin.getPluginManager().registerEvents(this, plugin);
  }

  /**
   * Listens for a player consume their held item.
   *
   * @param event The event being fired.
   */
  @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
  public void onItemConsume(PlayerItemConsumeEvent event) {
    // Variables
    Player player = event.getPlayer();
    PlayerInventory inventory = player.getInventory();
    ItemStack item = event.getItem();
    EquipmentSlot slot = (inventory.getItemInMainHand().equals(item) ? EquipmentSlot.HAND : EquipmentSlot.OFF_HAND);

    // Skip if too many remain
    if (item.getAmount() > 1) {
      return;
    }

    // Call Update
    plugin.updateOne(player, slot, null, UpdateCause.ITEM_CONSUME);
  }
}
