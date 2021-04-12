package com.gmail.alexdion93.inventoryequipevent.listeners;

import java.util.HashSet;

import org.bukkit.Material;
import org.bukkit.entity.Cat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.gmail.alexdion93.inventoryequipevent.InventoryEquipEventPlugin;
import com.gmail.alexdion93.inventoryequipevent.events.PlayerFeedEntityEvent;
import com.gmail.alexdion93.inventoryequipevent.events.UpdateCause;
import com.gmail.alexdion93.inventoryequipevent.util.ItemUtil;

public class PlayerFeedEntityListener implements Listener {

  // Variables
  private InventoryEquipEventPlugin plugin;

  /**
   * Constructor
   *
   * @param plugin The plugin instance
   */
  public PlayerFeedEntityListener(InventoryEquipEventPlugin plugin) {
    this.plugin = plugin;
    plugin.getPluginManager().registerEvents(this, plugin);
  }

  /**
   * Listens for a player interacting with an entity
   *
   * @param event The event being fired.
   */
  @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
  public void onInteractEntity(PlayerInteractEntityEvent event) {
    // Variables
    PlayerFeedEntityEvent e = null;
    EquipmentSlot hand = event.getHand();
    Player player = event.getPlayer();
    PlayerInventory inventory = player.getInventory();
    ItemStack item = (hand == EquipmentSlot.HAND ? inventory.getItemInMainHand() : inventory.getItemInOffHand());
    Material material = (item != null ? item.getType() : null);
    Entity entity = event.getRightClicked();
    EntityType type = entity.getType();
    HashSet<Material> feedableMaterials = ItemUtil.getConsumedInteractableMaterials(type);

    // Skip if no item or material
    if ((item == null) || (item.getType() == Material.AIR)) {
      return;
    }
    if (material == null) {
      return;
    }

    // Skip if not a useable material
    if (!feedableMaterials.contains(material)) {
      return;
    }

    // Pig Interactions
    if (type == EntityType.PIG) {
      Pig pig = (Pig) entity;

      // Saddle Interaction for PIG
      if (pig.hasSaddle() && (material == Material.SADDLE)) {
        return;
      }
    }

    // Wolf Interactions
    else if (type == EntityType.WOLF) {
      Wolf wolf = (Wolf) entity;

      // Dye interactions for WOLF
      if (wolf.isTamed()) {
        if (wolf.getCollarColor() == ItemUtil.getDyeColor(material)) {
          return;
        }
      }
    }

    // Cat Interactions
    else if (type == EntityType.CAT) {
      Cat cat = (Cat) entity;

      // Dye interactions for CAT
      if (cat.isTamed()) {
        if (cat.getCollarColor() == ItemUtil.getDyeColor(material)) {
          return;
        }
      }
    }

    // Sheep Interactions
    else if (type == EntityType.SHEEP) {
      Sheep sheep = (Sheep) entity;

      // Dye interactions for SHEEP
      if (sheep.getColor() == ItemUtil.getDyeColor(material)) {
        return;
      }
    }

    // Call the event and cancel this one if cancelled.
    e = new PlayerFeedEntityEvent(player, entity, item, hand);
    plugin.getPluginManager().callEvent(e);

    if (e.isCancelled()) {
      event.setCancelled(true);
    }
  }

  /**
   * Listens for a player feeding an entity.
   *
   * @param event The event being fired.
   */
  @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
  public void onPlayerFeedEntity(PlayerFeedEntityEvent event) {
    // Variables
    Player player = event.getPlayer();
    EquipmentSlot slot = event.getHand();
    ItemStack item = event.getItem();

    // Skip if null
    if (item == null) {
      return;
    }

    // Skip if too many items.
    if (item.getAmount() > 1) {
      return;
    }

    // Call Update
    plugin.updateOne(player, slot, null, UpdateCause.ENTITY_FEED);
  }
}
