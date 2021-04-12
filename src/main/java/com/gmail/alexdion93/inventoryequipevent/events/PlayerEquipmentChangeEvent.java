package com.gmail.alexdion93.inventoryequipevent.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class PlayerEquipmentChangeEvent extends Event implements Cancellable {

  private static HandlerList handlers = new HandlerList();

  /**
   * Returns the handlers of this event.
   *
   * @return The handlers.
   */
  public static HandlerList getHandlerList() {
    return handlers;
  }

  private boolean cancelled = false;
  private UpdateCause cause;
  private ItemStack item[];
  private Player player;

  private EquipmentSlot slot[];

  /**
   * Constructor
   *
   * @param player The player triggering this event.
   * @param item   The new item.
   * @param slot   The slot that changed.
   * @param cause  The cause of the update.
   */
  public PlayerEquipmentChangeEvent(Player player, ItemStack item, EquipmentSlot slot, UpdateCause cause) {
    this.player = player;
    this.item = new ItemStack[1];
    this.slot = new EquipmentSlot[1];
    this.item[0] = item;
    this.slot[0] = slot;
    this.cause = cause;
  }

  /**
   * Constructor
   *
   * @param player The player triggering this event.
   * @param items  The new items.
   * @param slots  The slots that changed.
   * @param cause  The cause of the update.
   * @author HomieDion
   * @since 1.0.0
   */
  public PlayerEquipmentChangeEvent(Player player, ItemStack[] items, EquipmentSlot[] slots, UpdateCause cause) {
    this.player = player;
    item = items;
    slot = slots;
    this.cause = cause;
  }

  /**
   * Returns the cause of the update.
   *
   * @return The cause of the update.
   */
  public UpdateCause getCause() {
    return cause;
  }

  /*
   * (non-Javadoc)
   *
   * @see org.bukkit.event.Event#getHandlers()
   */
  @Override
  public HandlerList getHandlers() {
    return handlers;
  }

  /**
   * Returns the items tied to this event.
   *
   * @return The items tied to this event.
   */
  public ItemStack[] getItems() {
    return item;
  }

  /**
   * Returns the player who triggered this event.
   *
   * @return The event's player.
   */
  public Player getPlayer() {
    return player;
  }

  /**
   * Return the equipment slots that were modified.
   *
   * @return The target equipment slot
   */
  public EquipmentSlot[] getSlots() {
    return slot;
  }

  /*
   * (non-Javadoc)
   *
   * @see org.bukkit.event.Cancellable#isCancelled()
   */
  @Override
  public boolean isCancelled() {
    return cancelled;
  }

  /*
   * (non-Javadoc)
   *
   * @see org.bukkit.event.Cancellable#setCancelled(boolean)
   */
  @Override
  public void setCancelled(boolean cancelled) {
    this.cancelled = cancelled;
  }

  /*
   * (non-Javadoc)
   *
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    // No Player
    if (player == null) {
      return "No one updated!";
    }

    // No slots
    if ((slot == null) || (slot.length == 0)) {
      return player.getName() + " didn't update!";
    }

    // Variables
    String str = player.getName() + "'s ";

    // One Slot
    if (slot.length == 1) {
      str += slot[0].name();
    } else {
      str += slot[0].name();
      for (int i = 1; i < (slot.length - 1); i++) {
        str += ", " + slot[i].name();
      }
      str += " and " + slot[slot.length - 1].name();
    }

    // Append Ending
    str += " updated due to " + cause.name();

    // Return
    return str;
  }
}