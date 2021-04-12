package com.gmail.alexdion93.inventoryequipevent.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import com.gmail.alexdion93.inventoryequipevent.InventoryEquipEventPlugin;
import com.gmail.alexdion93.inventoryequipevent.events.UpdateCause;

public class ArmorStandManipulateListener implements Listener {

  // Variables
  private InventoryEquipEventPlugin plugin;

  /**
   * Constructor
   *
   * @param plugin The plugin this is tied to.
   */
  public ArmorStandManipulateListener(InventoryEquipEventPlugin plugin) {
    this.plugin = plugin;
    plugin.getPluginManager().registerEvents(this, plugin);
  }

  /**
   * Listens for a player manipulating an armor stand.
   *
   * @param event The event being fired.
   */
  @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
  public void onArmorStandManipulate(PlayerArmorStandManipulateEvent event) {
    // Variables
    Player player = event.getPlayer();
    ItemStack armorStandItem = event.getArmorStandItem();
    ItemStack playerItem = event.getPlayerItem();
    ItemStack item = null;
    UpdateCause cause = null;
    boolean playerItemEmpty = ((playerItem != null) && (playerItem.getType() == Material.AIR));
    boolean armorStandItemEmpty = ((armorStandItem != null) && (armorStandItem.getType() == Material.AIR));

    // Swap Items
    if (!playerItemEmpty && !armorStandItemEmpty) {
      item = armorStandItem;
      cause = UpdateCause.ARMOR_STAND_SWAP;
    }

    // Transfer item to player
    else if (playerItemEmpty && !armorStandItemEmpty) {
      item = armorStandItem;
      cause = UpdateCause.ARMOR_STAND_TAKE;
    }

    // Transfer item from player
    else if (!playerItemEmpty && armorStandItemEmpty) {
      item = null;
      cause = UpdateCause.ARMOR_STAND_GIVE;
    }

    // No Transaction
    else {
      return;
    }

    // Send the update
    plugin.updateOne(player, EquipmentSlot.HAND, item, cause);
  }
}
