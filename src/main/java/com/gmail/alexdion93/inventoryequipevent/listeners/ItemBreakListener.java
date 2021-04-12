package com.gmail.alexdion93.inventoryequipevent.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.gmail.alexdion93.inventoryequipevent.InventoryEquipEventPlugin;
import com.gmail.alexdion93.inventoryequipevent.events.UpdateCause;

public class ItemBreakListener implements Listener {

  // Variables
  private InventoryEquipEventPlugin plugin;

  /**
   * Constructor
   *
   * @param plugin The plugin instance.
   */
  public ItemBreakListener(InventoryEquipEventPlugin plugin) {
    this.plugin = plugin;
    plugin.getPluginManager().registerEvents(this, plugin);
  }

  /**
   * Listens for a player's item breaking.
   *
   * @param event The event being fired.
   */
  @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
  public void onItemBreak(PlayerItemBreakEvent event) {
    // Variables
    Player player = event.getPlayer();
    PlayerInventory inventory = player.getInventory();
    ItemStack brokenItem = event.getBrokenItem();
    ItemStack[] armorContents = inventory.getArmorContents();
    EquipmentSlot[] armorSlots = { EquipmentSlot.FEET, EquipmentSlot.LEGS, EquipmentSlot.CHEST, EquipmentSlot.HEAD };
    EquipmentSlot slot = null;

    // Check if the item is one of the armor slots
    for (int i = 0; i < 4; i++) {
      if (!brokenItem.isSimilar(armorContents[i])) {
        continue;
      }
      slot = armorSlots[i];
      break;
    }

    // Check if its the main or off hand.
    if (slot == null) {
      if ((brokenItem.isSimilar(inventory.getItemInMainHand()))) {
        slot = EquipmentSlot.HAND;
      } else if ((brokenItem.isSimilar(inventory.getItemInOffHand()))) {
        slot = EquipmentSlot.OFF_HAND;
      }
    }

    // Send the update
    if (slot == null) {
      return;
    }
    plugin.updateOne(player, slot, null, UpdateCause.ITEM_BREAK);
  }

}
