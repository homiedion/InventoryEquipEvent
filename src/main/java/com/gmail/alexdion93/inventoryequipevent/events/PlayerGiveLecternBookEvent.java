package com.gmail.alexdion93.inventoryequipevent.events;

import org.bukkit.block.Lectern;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class PlayerGiveLecternBookEvent extends Event implements Cancellable {

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
  private Lectern lecturn;
  private Player player;

  private EquipmentSlot hand;

  /**
   * Constructor
   *
   * @param player The player triggering this event.
   * @param lecturn The lecturn being interacted with
   * @param item The new item.
   * @param hand The hand slot being interacted with
   */
  public PlayerGiveLecternBookEvent(Player player, Lectern lecturn, ItemStack item, EquipmentSlot hand) {
    this.player = player;
    this.item = item;
    this.lecturn = lecturn;
    this.hand = hand;
  }

  /**
   * Returns the hand being used to feed.
   *
   * @return An equipment slot
   */
  public EquipmentSlot getHand() {
    return hand;
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
   * Returns the item tied to this event.
   *
   * @return The item tied to this event.
   */
  public ItemStack getItem() {
    return item;
  }

  /**
   * Returns the entity being fed.
   *
   * @return An entity or null.
   */
  public Lectern getLecturn() {
    return lecturn;
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