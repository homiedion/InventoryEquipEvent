package com.gmail.alexdion93.inventoryequipevent;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.alexdion93.inventoryequipevent.events.PlayerEquipmentChangeEvent;
import com.gmail.alexdion93.inventoryequipevent.events.UpdateCause;
import com.gmail.alexdion93.inventoryequipevent.listeners.ArmorStandManipulateListener;
import com.gmail.alexdion93.inventoryequipevent.listeners.ArrowExhaustListener;
import com.gmail.alexdion93.inventoryequipevent.listeners.ArrowPickupListener;
import com.gmail.alexdion93.inventoryequipevent.listeners.BlockPlaceListener;
import com.gmail.alexdion93.inventoryequipevent.listeners.BoneMealListener;
import com.gmail.alexdion93.inventoryequipevent.listeners.DispenserFireListener;
import com.gmail.alexdion93.inventoryequipevent.listeners.InventoryClickListener;
import com.gmail.alexdion93.inventoryequipevent.listeners.ItemBreakListener;
import com.gmail.alexdion93.inventoryequipevent.listeners.ItemConsumeListener;
import com.gmail.alexdion93.inventoryequipevent.listeners.ItemDropListener;
import com.gmail.alexdion93.inventoryequipevent.listeners.ItemHoldListener;
import com.gmail.alexdion93.inventoryequipevent.listeners.ItemPickupListener;
import com.gmail.alexdion93.inventoryequipevent.listeners.ItemSwapListener;
import com.gmail.alexdion93.inventoryequipevent.listeners.PlayerBucketListener;
import com.gmail.alexdion93.inventoryequipevent.listeners.PlayerEquipmentChangeListener;
import com.gmail.alexdion93.inventoryequipevent.listeners.PlayerFeedEntityListener;
import com.gmail.alexdion93.inventoryequipevent.listeners.PlayerLecternEventListener;
import com.gmail.alexdion93.inventoryequipevent.listeners.PlayerRightClickEquipListener;
import com.gmail.alexdion93.inventoryequipevent.listeners.PlayerSpawnEntityListener;
import com.gmail.alexdion93.inventoryequipevent.listeners.ProjectileThrowListener;
import com.gmail.alexdion93.inventoryequipevent.listeners.RespawnListener;
import com.gmail.alexdion93.inventoryequipevent.listeners.ResurrectListener;
import com.gmail.alexdion93.inventoryequipevent.listeners.VehiclePlaceListener;
import com.gmail.alexdion93.inventoryequipevent.util.ItemUtil;

public class InventoryEquipEventPlugin extends JavaPlugin {

  private static final boolean DEBUG = false;
  private PluginManager pm;

  /**
   * Returns the first available slot in a player's hot bar.
   *
   * @param player The player inventory.
   * @return A positive integer or -1 on failure.
   */
  public int getHotbarEmptySlot(Player player) {
    return getHotbarEmptySlot(player.getInventory());
  }

  /**
   * Returns the first available slot in a player's hot bar.
   *
   * @param inventory The target inventory.
   * @return A positive integer or -1 on failure.
   */
  public int getHotbarEmptySlot(PlayerInventory inventory) {
    ItemStack contents[] = inventory.getContents();

    /*
     * 0 to 8 is the hot bar 9 to 35 is the inventory 36 are the boots 37 is the
     * leggings 36 is the chest plate 39 is the helmet 40 is the off hand
     */
    for (int i = 0; i < 9; i++) {
      if ((contents[i] == null) || (contents[i].getType() == Material.AIR)) {
        return i;
      }
    }

    // Default Return
    return -1;
  }

  /**
   * Returns the first matching itemslot that the item can fit into.
   *
   * @param player The target player.
   * @param item   The item being checked.
   * @return The slot number or -1 if not found
   */
  public int getMatchingSlot(Player player, ItemStack item) {
    if (player == null) {
      return -1;
    }
    return getMatchingSlot(player.getInventory(), item);
  }

  /**
   * Returns the first matching itemslot that the item can fit into.
   *
   * @param inventory The target inventory.
   * @param item      The item being checked.
   * @return The slot number or -1 if not found
   */
  public int getMatchingSlot(PlayerInventory inventory, ItemStack item) {
    // Variables
    int x = 0;

    // Null Check
    if (inventory == null) {
      return -1;
    }
    if (item == null) {
      return -1;
    }

    // Loop
    for (ItemStack stack : inventory.getContents()) {
      x++;
      // Skip if null or AIR
      if ((stack == null) || (stack.getType() == Material.AIR)) {
        continue;
      }

      // If the items are not similar
      if (!item.isSimilar(stack)) {
        continue;
      }

      // If the stack is at its maximum amount
      if (stack.getAmount() >= stack.getMaxStackSize()) {
        continue;
      }

      return x;
    }

    // Default
    return -1;
  }

  /**
   * Returns the plugin manager
   *
   * @return the plugin manager
   */
  public PluginManager getPluginManager() {
    return pm;
  }

  @Override
  public void onDisable() {
  }

  @Override
  public void onEnable() {
    // Initialize Variables
    pm = getServer().getPluginManager();

    // Initialize Listeners
    new ArmorStandManipulateListener(this);
    new ArrowExhaustListener(this);
    new ArrowPickupListener(this);
    new BlockPlaceListener(this);
    new BoneMealListener(this);
    new DispenserFireListener(this);
    new InventoryClickListener(this);
    new ItemBreakListener(this);
    new ItemConsumeListener(this);
    new ItemDropListener(this);
    new ItemHoldListener(this);
    new ItemPickupListener(this);
    new ItemSwapListener(this);
    new PlayerBucketListener(this);
    if (DEBUG) {
      new PlayerEquipmentChangeListener(this);
    }
    new PlayerFeedEntityListener(this);
    new PlayerRightClickEquipListener(this);
    new PlayerLecternEventListener(this);
    new PlayerSpawnEntityListener(this);
    new RespawnListener(this);
    new ResurrectListener(this);
    new ProjectileThrowListener(this);
    new VehiclePlaceListener(this);
  }

  /**
   * Calls an update for all values of a player.
   *
   * @param player The target player.
   * @param cause  The cause of the update.
   */
  public void updateAll(Player player, UpdateCause cause) {
    pm.callEvent(
        new PlayerEquipmentChangeEvent(player, ItemUtil.getFullInventory(player), EquipmentSlot.values(), cause));
  }

  /**
   * Calls an update for one slot of a player while providing the item.
   *
   * @param player The target player.
   * @param slot   The slot being updated.
   * @param item   The item used for updating.
   * @param cause  The cause of the update.
   */
  public void updateOne(Player player, EquipmentSlot slot, ItemStack item, UpdateCause cause) {
    pm.callEvent(new PlayerEquipmentChangeEvent(player, item, slot, cause));
  }

  /**
   * Calls an update for one slot of a player.
   *
   * @param player The target player.
   * @param slot   The target slot.
   * @param cause  The cause of the update.
   */
  public void updateOne(Player player, EquipmentSlot slot, UpdateCause cause) {
    pm.callEvent(new PlayerEquipmentChangeEvent(player, ItemUtil.getItemFromSlot(player, slot), slot, cause));
  }

  /**
   * Calls an update for one slot of a player.
   *
   * @param player The target player.
   * @param slots  The slots being updated.
   * @param items  The items used for updating.
   * @param cause  The cause of the update.
   */
  public void updateSome(Player player, EquipmentSlot[] slots, ItemStack[] items, UpdateCause cause) {
    pm.callEvent(new PlayerEquipmentChangeEvent(player, items, slots, cause));
  }

  /**
   * Calls an update for one slot of a player.
   *
   * @param player The target player.
   * @param slots  The slots being updated.
   * @param cause  The cause of the update.
   */
  public void updateSome(Player player, EquipmentSlot[] slots, UpdateCause cause) {
    pm.callEvent(new PlayerEquipmentChangeEvent(player, ItemUtil.getItemFromSlots(player, slots), slots, cause));
  }
}
