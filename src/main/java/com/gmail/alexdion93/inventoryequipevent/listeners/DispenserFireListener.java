package com.gmail.alexdion93.inventoryequipevent.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.Dispenser;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.gmail.alexdion93.inventoryequipevent.InventoryEquipEventPlugin;
import com.gmail.alexdion93.inventoryequipevent.events.UpdateCause;
import com.gmail.alexdion93.inventoryequipevent.util.ItemUtil;

public class DispenserFireListener implements Listener {

  // Variables
  private InventoryEquipEventPlugin plugin;

  /**
   * Constructor
   *
   * @param plugin The plugin instance.
   */
  public DispenserFireListener(InventoryEquipEventPlugin plugin) {
    this.plugin = plugin;
    plugin.getPluginManager().registerEvents(this, plugin);
  }

  /**
   * Returns the closest player to the dispenser.
   *
   * @param sourceBlock The dispenser block.
   * @param targetBlock The block being dispensed too.
   * @return A player or null.
   */
  public Player getNearestPlayer(Block sourceBlock, Block targetBlock) {
    // Variables
    Player player = null;
    World world = targetBlock.getWorld();
    Location sourceLocation = sourceBlock.getLocation();
    double distance = Double.MAX_VALUE;

    // Loop
    for (final Entity entity : world.getNearbyEntities(targetBlock.getLocation(), 1, 1, 1)) {
      // If the entity is a player.
      if (entity.getType() == EntityType.PLAYER) {
        // If we don't have a player
        if (player == null) {
          player = (Player) entity;
          distance = player.getLocation().distance(sourceLocation);
        }

        // Compare distances
        else {
          // Temp Variables
          double newDistance = entity.getLocation().distance(sourceLocation);

          // If the entity is closer
          if (newDistance < distance) {
            player = (Player) entity;
            distance = newDistance;
          }
        }
      }
    }

    // Return
    return player;
  }

  /**
   * Listens for a dispenser firing.
   *
   * @param event The event being fired.
   */
  @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
  public void onDispenserFire(BlockDispenseEvent event) {
    // Variables
    Block block = event.getBlock();
    BlockData data = block.getState().getBlockData();
    Dispenser dispenser = (Dispenser) data;
    Directional direction = dispenser;
    Block targetBlock = block.getRelative(direction.getFacing());
    Player player = getNearestPlayer(block, targetBlock);
    ItemStack item = event.getItem();
    PlayerInventory inventory;
    ItemStack[] armorContents = new ItemStack[5];
    EquipmentSlot slot = ItemUtil.getEquipmentSlot(item);
    EquipmentSlot[] armorSlots = { EquipmentSlot.FEET, EquipmentSlot.LEGS, EquipmentSlot.CHEST, EquipmentSlot.HEAD,
        EquipmentSlot.OFF_HAND };

    // Ignore empty or null items
    if ((item == null) || (item.getType() == Material.AIR)) {
      return;
    }

    // Exit if no player
    if (player == null) {
      return;
    }

    // Attempt to find slot.
    inventory = player.getInventory();

    armorContents[4] = inventory.getItemInOffHand();

    for (int i = 0; i < 4; i++) {
      armorContents[i] = inventory.getArmorContents()[i];
    }

    for (int i = 0; i < 5; i++) {

      // Skip if not the slot
      if (armorSlots[i] != slot) {
        continue;
      }

      // Exit if contents isn't null and contents isn't air
      if ((armorContents[i] != null) && (armorContents[i].getType() != Material.AIR)) {
        return;
      }
    }

    // Exit if no slot
    if (slot == null) {
      return;
    }

    // Call Event
    plugin.updateOne(player, slot, item, UpdateCause.DISPENSER_EQUIP);
  }
}
