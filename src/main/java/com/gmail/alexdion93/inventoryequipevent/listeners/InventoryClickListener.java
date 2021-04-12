package com.gmail.alexdion93.inventoryequipevent.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;

import com.gmail.alexdion93.inventoryequipevent.InventoryEquipEventPlugin;
import com.gmail.alexdion93.inventoryequipevent.events.UpdateCause;
import com.gmail.alexdion93.inventoryequipevent.util.ItemUtil;

public class InventoryClickListener implements Listener {

  // Variables
  private InventoryEquipEventPlugin plugin;

  private final int BOOTS_SLOT = 36;
  private final int LEGGINGS_SLOT = 37;
  private final int CHESTPLATE_SLOT = 38;
  private final int HELMET_SLOT = 39;
  private final int OFFHAND_SLOT = 40;

  /**
   * Constructor
   *
   * @param plugin The plugin instance.
   */
  public InventoryClickListener(InventoryEquipEventPlugin plugin) {
    this.plugin = plugin;
    plugin.getPluginManager().registerEvents(this, plugin);
  }

  /**
   * Returns the equipment slot that we should be targetting.
   *
   * @param slot_type     The slot type we're looking at.
   * @param slot          The slot's index.
   * @param hotbar_active The active hotbar's index.
   * @return An EquipmentSlot or null
   */
  public EquipmentSlot getTargetEquipmentSlot(SlotType slot_type, int slot, int hotbar_active) {
    // Modifying the ARMOR
    if (slot_type == SlotType.ARMOR) {

      // Boots Slot
      if (slot == BOOTS_SLOT) {
        return EquipmentSlot.FEET;
      }

      // Leggings Slot
      else if (slot == LEGGINGS_SLOT) {
        return EquipmentSlot.LEGS;
      }

      // Chestplate Slot
      else if (slot == CHESTPLATE_SLOT) {
        return EquipmentSlot.CHEST;
      }

      // Helmet Slot
      else if (slot == HELMET_SLOT) {
        return EquipmentSlot.HEAD;
      }
    }

    // Modifying the QUICKBAR
    else if (slot_type == SlotType.QUICKBAR) {
      // Main Hand Slot
      if (slot == hotbar_active) {
        return EquipmentSlot.HAND;
      }

      // Offhand Slot
      else if (slot == OFFHAND_SLOT) {
        return EquipmentSlot.OFF_HAND;
      }
    }

    // Base Case
    return null;
  }

  /**
   * Listens for inventory clicking.
   *
   * @param event The event being fired.
   */
  @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
  public void onInventoryClick(InventoryClickEvent event) {

    // Variables
    HumanEntity whoClicked = event.getWhoClicked();
    InventoryAction action = event.getAction();
    ClickType click = event.getClick();
    ItemStack cursor = event.getCursor();
    ItemStack current = event.getCurrentItem();
    Inventory clickedInventory = event.getClickedInventory();
    int slot = event.getSlot();
    int hotbar_button = event.getHotbarButton();
    int hotbar_active = whoClicked.getInventory().getHeldItemSlot();
    SlotType slot_type = event.getSlotType();
    EquipmentSlot equipment_slot = null;
    final Player player = (Player) whoClicked;
    final ItemStack mainHand = player.getInventory().getItemInMainHand();
    final ItemStack offHand = player.getInventory().getItemInOffHand();

    // Exit if null
    if (clickedInventory == null) {
      return;
    }

    // Exit if no action
    if ((action == null) || (action == InventoryAction.NOTHING)) {
      return;
    }

    // ACTION: Collect to cursor
    if (action == InventoryAction.COLLECT_TO_CURSOR) {

      // Send an update to the main and off hand slot
      // The alternative involves checking all potential slots, gathing a list of what
      // could be pulled and determining if those slots are modified.
      new BukkitRunnable() {
        @Override
        public void run() {
          PlayerInventory inventory = player.getInventory();
          List<EquipmentSlot> slots = new ArrayList<EquipmentSlot>();
          List<ItemStack> items = new ArrayList<ItemStack>();

          // Check if main hand needs updating
          if (inventory.getItemInMainHand() != mainHand) {
            slots.add(EquipmentSlot.HAND);
            items.add(inventory.getItemInMainHand());
          }

          // Check if off hand needs updating
          if (inventory.getItemInOffHand() != offHand) {
            slots.add(EquipmentSlot.OFF_HAND);
            items.add(inventory.getItemInOffHand());
          }

          // Skip if no updates
          if (slots.isEmpty()) {
            return;
          }

          // Both need to be updated
          plugin.updateSome(player, slots.toArray(new EquipmentSlot[slots.size()]),
              items.toArray(new ItemStack[items.size()]), UpdateCause.INVENTORY_CLICK);
        }
      }.runTaskLater(plugin, 1L);
    }

    // ACTION: Drop from Slot
    else if ((action == InventoryAction.DROP_ALL_SLOT) || (action == InventoryAction.DROP_ONE_SLOT)) {

      // Exit if we're not emptying the slot
      if ((current.getAmount() > 1) && (action == InventoryAction.DROP_ONE_SLOT)) {
        return;
      }

      // Get the equipment slot
      equipment_slot = getTargetEquipmentSlot(slot_type, slot, hotbar_active);

      // Skip if no slot
      if (equipment_slot == null) {
        return;
      }

      // Call the update
      plugin.updateOne(player, equipment_slot, null, UpdateCause.INVENTORY_CLICK);
    }

    // ACTION: Hotbar Swap
    else if ((action == InventoryAction.HOTBAR_SWAP) || (action == InventoryAction.HOTBAR_MOVE_AND_READD)) {

      // If the button pressed is the active slot
      if (hotbar_button == hotbar_active) {

        // Call the update
        plugin.updateOne(player, EquipmentSlot.HAND, current, UpdateCause.INVENTORY_CLICK);
      }

      // If the button pressed on the active slot
      else if (slot == hotbar_active) {

        if (hotbar_button < 0) {
          return;
        }

        // Call the update
        plugin.updateOne(player, EquipmentSlot.HAND, clickedInventory.getItem(hotbar_button),
            UpdateCause.INVENTORY_CLICK);
      }

    }

    // ACTION: Move to other inventory (shift click)
    else if (action == InventoryAction.MOVE_TO_OTHER_INVENTORY) {

      // Shift clicking within the player's inventory
      if (clickedInventory.getType() == InventoryType.PLAYER) {

        // This is the slot we're taking from
        equipment_slot = getTargetEquipmentSlot(slot_type, slot, hotbar_active);

        // Variables
        List<EquipmentSlot> slots = new ArrayList<EquipmentSlot>();
        List<ItemStack> items = new ArrayList<ItemStack>();

        // If the slot we're targetting is empty
        if ((equipment_slot == null) || (equipment_slot == EquipmentSlot.HAND)
            || (equipment_slot == EquipmentSlot.OFF_HAND)) {

          // Equipping a Helmet
          if (ItemUtil.isCategory(current, ItemUtil.HEADGEAR)) {
            slots.add(EquipmentSlot.HEAD);
            items.add(current);
          }

          // Equipping a Chestplate
          else if (ItemUtil.isCategory(current, ItemUtil.CHESTPLATES)) {
            slots.add(EquipmentSlot.CHEST);
            items.add(current);
          }

          // Equipping Leggings
          else if (ItemUtil.isCategory(current, ItemUtil.LEGGINGS)) {
            slots.add(EquipmentSlot.LEGS);
            items.add(current);
          }

          // Equipping Boots
          else if (ItemUtil.isCategory(current, ItemUtil.BOOTS)) {
            slots.add(EquipmentSlot.FEET);
            items.add(current);
          }
        }

        // Check if we've updated the main hand in addition to the armor slot
        else if (slot_type == SlotType.ARMOR) {
          new BukkitRunnable() {
            @Override
            public void run() {
              plugin.updateOne(player, EquipmentSlot.HAND, player.getInventory().getItemInMainHand(),
                  UpdateCause.INVENTORY_CLICK);
            }
          }.runTaskLater(plugin, 1L);
        }

        // If we found a slot call the update on the target slot
        if (equipment_slot != null) {
          slots.add(equipment_slot);
          if (current.getAmount() == 1) {
            items.add(null);
          }
        }

        // Exit if empty
        if (slots.isEmpty() || items.isEmpty()) {
          return;
        }

        // Call the update
        plugin.updateSome(player, slots.toArray(new EquipmentSlot[slots.size()]),
            items.toArray(new ItemStack[slots.size()]), UpdateCause.INVENTORY_CLICK);
      }

      // Shift clicking in any other inventory
      else {

        // Send an update to the main and off hand slot
        new BukkitRunnable() {
          @Override
          public void run() {
            if (mainHand == player.getInventory().getItemInMainHand()) {
              return;
            }
            plugin.updateOne(player, EquipmentSlot.HAND, player.getInventory().getItemInMainHand(),
                UpdateCause.INVENTORY_CLICK);
          }
        }.runTaskLater(plugin, 1L);
      }
    }

    // ACTION: Pickup All
    else if (action == InventoryAction.PICKUP_ALL) {

      // Get the equipment slot
      equipment_slot = getTargetEquipmentSlot(slot_type, slot, hotbar_active);

      // Skip if no slot
      if (equipment_slot == null) {
        return;
      }

      // Call the update
      plugin.updateOne(player, equipment_slot, null, UpdateCause.INVENTORY_CLICK);
    }

    // ACTION: Place All
    else if ((action == InventoryAction.PLACE_ALL) || (action == InventoryAction.PLACE_SOME)
        || (action == InventoryAction.PLACE_ONE)) {

      // Get the equipment slot
      equipment_slot = getTargetEquipmentSlot(slot_type, slot, hotbar_active);

      // Check to ensure we aren't placing something in the wrong slot
      if (slot_type == SlotType.ARMOR) {

        // Creative Behavior
        // BUG: Spigot Inventory Behavior; While in creative a player's armor clicks
        // only trigger PLACE_ALL instead of pickup.
        if (click == ClickType.CREATIVE) {
          if ((equipment_slot == EquipmentSlot.HEAD)
              && !(ItemUtil.isCategory(cursor, ItemUtil.HEADGEAR) || ItemUtil.isCategory(current, ItemUtil.HEADGEAR))) {
            return;
          }
          if ((equipment_slot == EquipmentSlot.CHEST) && !(ItemUtil.isCategory(cursor, ItemUtil.CHESTPLATES)
              || ItemUtil.isCategory(current, ItemUtil.CHESTPLATES))) {
            return;
          }
          if ((equipment_slot == EquipmentSlot.LEGS)
              && !(ItemUtil.isCategory(cursor, ItemUtil.LEGGINGS) || ItemUtil.isCategory(current, ItemUtil.LEGGINGS))) {
            return;
          }
          if ((equipment_slot == EquipmentSlot.FEET)
              && !(ItemUtil.isCategory(cursor, ItemUtil.BOOTS) || ItemUtil.isCategory(current, ItemUtil.BOOTS))) {
            return;
          }
        }

        // Normal Behavior
        else {
          if ((equipment_slot == EquipmentSlot.HEAD) && !ItemUtil.isCategory(cursor, ItemUtil.HEADGEAR)) {
            return;
          }
          if ((equipment_slot == EquipmentSlot.CHEST) && !ItemUtil.isCategory(cursor, ItemUtil.CHESTPLATES)) {
            return;
          }
          if ((equipment_slot == EquipmentSlot.LEGS) && !ItemUtil.isCategory(cursor, ItemUtil.LEGGINGS)) {
            return;
          }
          if ((equipment_slot == EquipmentSlot.FEET) && !ItemUtil.isCategory(cursor, ItemUtil.BOOTS)) {
            return;
          }
        }
      }

      // Skip if no slot
      if (equipment_slot == null) {
        return;
      }

      // Call the update
      plugin.updateOne(player, equipment_slot, cursor, UpdateCause.INVENTORY_CLICK);
    }

    // ACTION: Swap with cursor
    else if (action == InventoryAction.SWAP_WITH_CURSOR) {

      // Get the equipment slot
      equipment_slot = getTargetEquipmentSlot(slot_type, slot, hotbar_active);

      // Skip if no slot
      if (equipment_slot == null) {
        return;
      }

      // Call the update
      plugin.updateOne(player, equipment_slot, cursor, UpdateCause.INVENTORY_CLICK);
    }
  }
}
