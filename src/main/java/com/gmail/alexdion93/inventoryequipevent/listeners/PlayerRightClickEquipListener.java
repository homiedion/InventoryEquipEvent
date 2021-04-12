package com.gmail.alexdion93.inventoryequipevent.listeners;

import java.util.List;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import com.gmail.alexdion93.inventoryequipevent.InventoryEquipEventPlugin;
import com.gmail.alexdion93.inventoryequipevent.events.PlayerRightClickEquipEvent;
import com.gmail.alexdion93.inventoryequipevent.events.UpdateCause;
import com.gmail.alexdion93.inventoryequipevent.util.ItemUtil;

public class PlayerRightClickEquipListener implements Listener {

  // Variables
  private InventoryEquipEventPlugin plugin;

  /**
   * Constructor
   *
   * @param plugin The plugin instance
   */
  public PlayerRightClickEquipListener(InventoryEquipEventPlugin plugin) {
    this.plugin = plugin;
    plugin.getPluginManager().registerEvents(this, plugin);
  }

  /**
   * Determines if the player is looking at the target entity. * ORIGINAL:
   * https://www.spigotmc.org/threads/get-player-a-player-is-looking-at.212814/
   *
   * @param player The player looking.
   * @param entity The entity being looked at.
   * @return True or False.
   */
  private Entity getEntityLookingAt(Player player, int range) {
    // Variables
    List<Entity> entities = player.getNearbyEntities(range, range, range);
    List<Block> sightBlock = player.getLineOfSight((Set<Material>) null, range);

    // Loop blocks
    for (Block block : sightBlock) {

      // Loop entities
      for (Entity entity : entities) {
        // Location
        Location location = block.getLocation();

        // Skip if the entity is out of range
        if (Math.abs(entity.getLocation().getX() - location.getX()) >= 1.3) {
          continue;
        }
        if (Math.abs(entity.getLocation().getY() - location.getY()) >= 1.5) {
          continue;
        }
        if (Math.abs(entity.getLocation().getZ() - location.getZ()) >= 1.3) {
          continue;
        }

        // Return the entity
        return entity;
      }
    }

    // Failed to find entity
    return null;
  }

  /**
   * Listens for a player right clicking to equip an item
   *
   * @param event The event being fired.
   */
  @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
  public void onRightClickEquip(PlayerRightClickEquipEvent event) {
    // Variables
    Player player = event.getPlayer();
    EquipmentSlot slots[] = new EquipmentSlot[] { event.getHandSlot(), event.getArmorSlot() };
    ItemStack items[] = new ItemStack[] { (event.getItem().getAmount() > 1 ? event.getItem() : null), event.getItem() };

    // Call Update
    plugin.updateSome(player, slots, items, UpdateCause.RIGHT_CLICK_EQUIP);
  }

  /**
   * Listens for a player using their hand item.
   *
   * @param event The event being fired.
   */
  @EventHandler(priority = EventPriority.MONITOR)
  public void onUseItemInHand(PlayerInteractEvent event) {
    // Skip if left click
    if ((event.getAction() == Action.LEFT_CLICK_AIR) || (event.getAction() == Action.LEFT_CLICK_BLOCK)) {
      return;
    }

    // Variables
    Player player = event.getPlayer();
    ItemStack item = event.getItem();
    EquipmentSlot hand = event.getHand();
    EquipmentSlot slot = null;
    PlayerRightClickEquipEvent e = null;
    ItemStack head = ItemUtil.getItemFromSlot(player, EquipmentSlot.HEAD);
    ItemStack body = ItemUtil.getItemFromSlot(player, EquipmentSlot.CHEST);
    ItemStack legs = ItemUtil.getItemFromSlot(player, EquipmentSlot.LEGS);
    ItemStack feet = ItemUtil.getItemFromSlot(player, EquipmentSlot.FEET);
    Entity entity = getEntityLookingAt(player, 4);

    // Skip if we're interacting with an ARMOR_STAND
    if ((entity != null) && (entity.getType() == EntityType.ARMOR_STAND)) {
      return;
    }

    // Skip if null or air
    if ((item == null) || (item.getType() == Material.AIR)) {
      return;
    }

    // Skip if item isn't armor, else determine which slot it uses.
    boolean isHelmet = ItemUtil.isCategory(item, ItemUtil.HEADGEAR);
    boolean isChestplate = ItemUtil.isCategory(item, ItemUtil.CHESTPLATES);
    boolean isLeggings = ItemUtil.isCategory(item, ItemUtil.LEGGINGS);
    boolean isBoots = ItemUtil.isCategory(item, ItemUtil.BOOTS);
    if (!isHelmet && !isChestplate && !isLeggings && !isBoots) {
      return;
    }

    if (isHelmet && (head == null)) {
      slot = EquipmentSlot.HEAD;
    } else if (isChestplate && (body == null)) {
      slot = EquipmentSlot.CHEST;
    } else if (isLeggings && (legs == null)) {
      slot = EquipmentSlot.LEGS;
    } else if (isBoots && (feet == null)) {
      slot = EquipmentSlot.FEET;
    }

    // Skip if no slot, hand or player
    if ((slot == null) || (hand == null) || (player == null)) {
      return;
    }

    // Call the event and cancel this one if cancelled.
    e = new PlayerRightClickEquipEvent(player, item, slot, hand);
    plugin.getPluginManager().callEvent(e);

    if (e.isCancelled()) {
      event.setCancelled(true);
    }
  }
}
