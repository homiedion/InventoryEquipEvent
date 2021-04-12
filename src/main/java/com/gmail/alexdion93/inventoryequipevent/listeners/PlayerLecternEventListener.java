package com.gmail.alexdion93.inventoryequipevent.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Lectern;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTakeLecternBookEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import com.gmail.alexdion93.inventoryequipevent.InventoryEquipEventPlugin;
import com.gmail.alexdion93.inventoryequipevent.events.PlayerGiveLecternBookEvent;
import com.gmail.alexdion93.inventoryequipevent.events.UpdateCause;

public class PlayerLecternEventListener implements Listener {

  // Variables
  private InventoryEquipEventPlugin plugin;

  /**
   * Constructor
   *
   * @param plugin The plugin instance
   */
  public PlayerLecternEventListener(InventoryEquipEventPlugin plugin) {
    this.plugin = plugin;
    plugin.getPluginManager().registerEvents(this, plugin);
  }

  /**
   * Listens for a player interacting with a block.
   *
   * @param event The event being fired.
   */
  @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
  public void onInteractBlock(PlayerInteractEvent event) {
    // Skip if left click
    if ((event.getAction() == Action.LEFT_CLICK_AIR) || (event.getAction() == Action.LEFT_CLICK_BLOCK)) {
      return;
    }

    // Variables
    Block block = event.getClickedBlock();
    PlayerGiveLecternBookEvent e = null;
    ItemStack item = event.getItem();
    Lectern lectern = null;

    // Skip if not lectern
    if (block.getType() != Material.LECTERN) {
      return;
    }
    lectern = (Lectern) block.getBlockData();

    // Skip if already has a book
    if (lectern.hasBook()) {
      return;
    }

    // Skip if item is null
    if (item == null) {
      return;
    }

    // Skip if not holding book
    if ((item.getType() != Material.WRITABLE_BOOK) && (item.getType() != Material.WRITTEN_BOOK)) {
      return;
    }

    // Call the event and cancel this one if cancelled.
    e = new PlayerGiveLecternBookEvent(event.getPlayer(), (org.bukkit.block.Lectern) block.getState(), item,
        event.getHand());
    plugin.getPluginManager().callEvent(e);

    if (e.isCancelled()) {
      event.setCancelled(true);
    }
  }

  /**
   * Listens for a player giving to a lectern.
   *
   * @param event The event being fired.
   */
  @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
  public void onPlayerGiveLecternBook(PlayerGiveLecternBookEvent event) {
    // Variables
    Player player = event.getPlayer();
    ItemStack item = event.getItem();

    // Skip if too many of the item remain
    if (item.getAmount() > 1) {
      return;
    }

    // Call Update
    plugin.updateOne(player, event.getHand(), null, UpdateCause.LECTURE_GIVE);
  }

  /**
   * Listens for a player taking from a lectern.
   *
   * @param event The event being fired.
   */
  @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
  public void onPlayerTakeLecternBook(PlayerTakeLecternBookEvent event) {
    // Variables
    Player player = event.getPlayer();
    ItemStack item = event.getBook();
    int slot = player.getInventory().getHeldItemSlot();

    // Skip if one of these items already exists and that stack can hold this item.
    if (plugin.getMatchingSlot(player, item) != -1) {
      return;
    }

    // Skip if not the hotbar slot
    if (slot != plugin.getHotbarEmptySlot(player)) {
      return;
    }

    // Call Update
    plugin.updateOne(player, EquipmentSlot.HAND, item, UpdateCause.LECTURE_TAKE);
  }
}
