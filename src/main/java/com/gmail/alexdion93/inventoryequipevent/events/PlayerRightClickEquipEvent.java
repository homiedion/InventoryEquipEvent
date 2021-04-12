package com.gmail.alexdion93.inventoryequipevent.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class PlayerRightClickEquipEvent extends Event implements Cancellable {

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
  private ItemStack item;
  private Player player;
  private EquipmentSlot armorSlot;

  private EquipmentSlot handSlot;

  /**
   * Constructor
   *
   * @param player The player triggering this event.
   * @param item The new item.
   * @param armorSlot The armor slot that is being interacted with.
   * @param handSlot The hand slot being interacted with
   */
  public PlayerRightClickEquipEvent(Player player, ItemStack item, EquipmentSlot armorSlot, EquipmentSlot handSlot) {
    this.player = player;
    this.item = item;
    this.armorSlot = armorSlot;
    this.handSlot = handSlot;
  }

  /**
   * Return the equipment slot that were modified.
   *
   * @return The armor slot being equipped.
   */
  public EquipmentSlot getArmorSlot() {
    return armorSlot;
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
   * Return the hand slot that the item came from.
   *
   * @return The hand slot that is being equipped.
   */
  public EquipmentSlot getHandSlot() {
    return handSlot;
  }

  /**
   * Returns the item tied to this event.
   *
   * @return The item tied to this event.
   */
  public ItemStack getItem() {
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
}