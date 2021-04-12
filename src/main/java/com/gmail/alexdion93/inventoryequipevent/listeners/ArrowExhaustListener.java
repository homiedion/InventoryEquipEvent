package com.gmail.alexdion93.inventoryequipevent.listeners;

import org.bukkit.GameMode;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.gmail.alexdion93.inventoryequipevent.InventoryEquipEventPlugin;
import com.gmail.alexdion93.inventoryequipevent.events.UpdateCause;
import com.gmail.alexdion93.inventoryequipevent.util.ItemUtil;

public class ArrowExhaustListener implements Listener {

  // Variables
  private InventoryEquipEventPlugin plugin;

  /**
   * Constructor
   *
   * @param plugin The plugin instance.
   */
  public ArrowExhaustListener(InventoryEquipEventPlugin plugin) {
    this.plugin = plugin;
    plugin.getPluginManager().registerEvents(this, plugin);
  }

  /**
   * Listens for a player running out of arrows in either hand.
   *
   * @param event The event being fired.
   */
  @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
  public void onExhaustArrows(EntityShootBowEvent event) {
    // Skip if not a player
    if (event.getEntityType() != EntityType.PLAYER) {
      return;
    }

    // Variables
    Player player = (Player) event.getEntity();
    PlayerInventory inventory = player.getInventory();
    ItemStack main = inventory.getItemInMainHand();
    ItemStack off = inventory.getItemInOffHand();
    EquipmentSlot slot = null;

    // Skip if the player is in creative mode.
    if (player.getGameMode() == GameMode.CREATIVE) {
      return;
    }

    // Set the slot to the mainhand if appropriate
    if (slot == null) {
      if (ItemUtil.isCategory(main, ItemUtil.ARROWS)) {
        slot = EquipmentSlot.HAND;
      }
    }

    // Set the slot to the offhand if appropriate
    if (slot == null) {
      if (ItemUtil.isCategory(off, ItemUtil.ARROWS)) {
        slot = EquipmentSlot.OFF_HAND;
      }
    }

    // Skip if no slot or too many remain
    if (slot == null) {
      return;
    }
    if ((slot == EquipmentSlot.HAND ? main.getAmount() : off.getAmount()) > 1) {
      return;
    }

    // Call Update
    plugin.updateOne(player, slot, null, UpdateCause.ARROW_EXHUAST);
  }
}
