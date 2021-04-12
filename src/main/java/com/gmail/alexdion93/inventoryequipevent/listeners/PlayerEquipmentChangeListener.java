package com.gmail.alexdion93.inventoryequipevent.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.gmail.alexdion93.inventoryequipevent.InventoryEquipEventPlugin;
import com.gmail.alexdion93.inventoryequipevent.events.PlayerEquipmentChangeEvent;
import com.gmail.alexdion93.inventoryequipevent.events.UpdateCause;

public class PlayerEquipmentChangeListener implements Listener {

  /**
   * Constructor
   *
   * @param plugin The plugin instance
   */
  public PlayerEquipmentChangeListener(InventoryEquipEventPlugin plugin) {
    plugin.getPluginManager().registerEvents(this, plugin);
  }

  /**
   * Listens for the player being updated.
   *
   * @param event The event being fired.
   */
  @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
  public void onDebug(PlayerEquipmentChangeEvent event) {
    // Variables
    Player player = event.getPlayer();
    UpdateCause cause = event.getCause();

    // Debug Message
    for (int i = 0; i < 20; i++) {
      player.sendMessage("");
    }
    player.sendMessage("Your equipment updated due to " + cause.toString());
  }
}
