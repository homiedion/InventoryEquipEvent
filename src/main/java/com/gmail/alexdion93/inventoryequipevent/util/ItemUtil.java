package com.gmail.alexdion93.inventoryequipevent.util;

import java.util.HashSet;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class ItemUtil {

  public static final Material[] ARROWS = { Material.ARROW, Material.TIPPED_ARROW, Material.SPECTRAL_ARROW, };

  public static final Material[] AXES = { Material.DIAMOND_AXE, Material.GOLDEN_AXE, Material.IRON_AXE,
      Material.NETHERITE_AXE, Material.STONE_AXE, Material.WOODEN_AXE, };

  public static final Material[] BOATS = { Material.ACACIA_BOAT, Material.BIRCH_BOAT, Material.DARK_OAK_BOAT,
      Material.JUNGLE_BOAT, Material.OAK_BOAT, Material.SPRUCE_BOAT };

  public static final Material[] BONEMEAL_TARGET = { Material.GRASS_BLOCK, Material.WHEAT, Material.CARROTS,
      Material.POTATOES, Material.BAMBOO, Material.MELON_STEM, Material.PUMPKIN_STEM, Material.ACACIA_SAPLING,
      Material.BAMBOO_SAPLING, Material.BIRCH_SAPLING, Material.DARK_OAK_SAPLING, Material.JUNGLE_SAPLING,
      Material.OAK_SAPLING, Material.SUNFLOWER, Material.LILAC, Material.ROSE_BUSH, Material.PEONY, Material.TALL_GRASS,
      Material.FERN, Material.SEAGRASS, Material.TALL_SEAGRASS, Material.BROWN_MUSHROOM, Material.RED_MUSHROOM,
      Material.COCOA, Material.SWEET_BERRY_BUSH, Material.SEA_PICKLE, Material.KELP_PLANT,
      // 1.16 Additions
      Material.CRIMSON_FUNGUS, Material.WARPED_FUNGUS, Material.WEEPING_VINES_PLANT, Material.TWISTING_VINES_PLANT, };

  public static final Material[] BOOTS = { Material.CHAINMAIL_BOOTS, Material.DIAMOND_BOOTS, Material.GOLDEN_BOOTS,
      Material.IRON_BOOTS, Material.LEATHER_BOOTS,
      // 1.16 Additions
      Material.NETHERITE_BOOTS, };

  public static final Material[] CHESTPLATES = { Material.CHAINMAIL_CHESTPLATE, Material.DIAMOND_CHESTPLATE,
      Material.GOLDEN_CHESTPLATE, Material.IRON_CHESTPLATE, Material.LEATHER_CHESTPLATE,
      // 1.16 Additions
      Material.NETHERITE_CHESTPLATE, };

  public static final Material[] DYES = { Material.BLACK_DYE, Material.BLUE_DYE, Material.BROWN_DYE, Material.CYAN_DYE,
      Material.GRAY_DYE, Material.GREEN_DYE, Material.LIGHT_BLUE_DYE, Material.LIGHT_GRAY_DYE, Material.LIME_DYE,
      Material.MAGENTA_DYE, Material.ORANGE_DYE, Material.PINK_DYE, Material.PURPLE_DYE, Material.RED_DYE,
      Material.WHITE_DYE, Material.YELLOW_DYE };

  public static final Material[] FARMLAND_PLANTS = { Material.BEETROOT_SEEDS, Material.MELON_SEEDS,
      Material.PUMPKIN_SEEDS, Material.WHEAT_SEEDS, };

  public static final Material[] HEADGEAR = { Material.CHAINMAIL_HELMET, Material.DIAMOND_HELMET,
      Material.GOLDEN_HELMET, Material.IRON_HELMET, Material.LEATHER_HELMET, Material.CARVED_PUMPKIN,
      Material.SKELETON_SKULL, Material.WITHER_SKELETON_SKULL, Material.CREEPER_HEAD, Material.DRAGON_HEAD,
      Material.PLAYER_HEAD, Material.ZOMBIE_HEAD, Material.TURTLE_HELMET,
      // 1.16 Additions
      Material.NETHERITE_HELMET, };

  public static final Material[] HELMETS = { Material.CHAINMAIL_HELMET, Material.DIAMOND_HELMET, Material.GOLDEN_HELMET,
      Material.IRON_HELMET, Material.LEATHER_HELMET, Material.TURTLE_HELMET,
      // 1.16 Additions
      Material.NETHERITE_HELMET, };

  public static final Material[] HOES = { Material.DIAMOND_HOE, Material.GOLDEN_HOE, Material.IRON_HOE,
      Material.NETHERITE_HOE, Material.STONE_HOE, Material.WOODEN_HOE, };

  public static final Material[] LEGGINGS = { Material.CHAINMAIL_LEGGINGS, Material.DIAMOND_LEGGINGS,
      Material.GOLDEN_LEGGINGS, Material.IRON_LEGGINGS, Material.LEATHER_LEGGINGS,
      // 1.16 Additions
      Material.NETHERITE_LEGGINGS, };

  public static final Material[] MINECART_RAILS = { Material.ACTIVATOR_RAIL, Material.DETECTOR_RAIL,
      Material.POWERED_RAIL, Material.RAIL };

  public static final Material[] MINECARTS = { Material.CHEST_MINECART, Material.COMMAND_BLOCK_MINECART,
      Material.FURNACE_MINECART, Material.HOPPER_MINECART, Material.MINECART, Material.TNT_MINECART };

  public static final Material[] OFFHANDS = { Material.SHIELD, };

  public static final Material[] PICKAXES = { Material.DIAMOND_PICKAXE, Material.GOLDEN_PICKAXE, Material.IRON_PICKAXE,
      Material.NETHERITE_PICKAXE, Material.STONE_PICKAXE, Material.WOODEN_PICKAXE, };

  public static final Material[] PLACEABLE = { Material.FIREWORK_ROCKET };

  public static final Material[] PLACEABLES = { Material.ARMOR_STAND, Material.END_CRYSTAL, Material.ITEM_FRAME,
      Material.PAINTING, };

  public static final Material[] PLANTABLE = { Material.BEETROOT_SEEDS, Material.MELON_SEEDS, Material.PUMPKIN_SEEDS,
      Material.WHEAT_SEEDS, Material.COCOA_BEANS, Material.CHORUS_FRUIT };

  public static final Material[] SHOOTABLE = { Material.BOW, Material.CROSSBOW };

  public static final Material[] SHOVELS = { Material.DIAMOND_SHOVEL, Material.GOLDEN_SHOVEL, Material.IRON_SHOVEL,
      Material.NETHERITE_SHOVEL, Material.STONE_SHOVEL, Material.WOODEN_SHOVEL, };

  public static final Material[] SPAWN_EGGS = { Material.BAT_SPAWN_EGG, Material.BEE_SPAWN_EGG,
      Material.BLAZE_SPAWN_EGG, Material.CAT_SPAWN_EGG, Material.CAVE_SPIDER_SPAWN_EGG, Material.CHICKEN_SPAWN_EGG,
      Material.COD_SPAWN_EGG, Material.COW_SPAWN_EGG, Material.CREEPER_SPAWN_EGG, Material.DOLPHIN_SPAWN_EGG,
      Material.DONKEY_SPAWN_EGG, Material.DROWNED_SPAWN_EGG, Material.ELDER_GUARDIAN_SPAWN_EGG,
      Material.ENDERMAN_SPAWN_EGG, Material.ENDERMITE_SPAWN_EGG, Material.EVOKER_SPAWN_EGG, Material.FOX_SPAWN_EGG,
      Material.GHAST_SPAWN_EGG, Material.GUARDIAN_SPAWN_EGG, Material.HORSE_SPAWN_EGG, Material.HUSK_SPAWN_EGG,
      Material.LLAMA_SPAWN_EGG, Material.MAGMA_CUBE_SPAWN_EGG, Material.MOOSHROOM_SPAWN_EGG, Material.MULE_SPAWN_EGG,
      Material.OCELOT_SPAWN_EGG, Material.PANDA_SPAWN_EGG, Material.PARROT_SPAWN_EGG, Material.PHANTOM_SPAWN_EGG,
      Material.PIG_SPAWN_EGG, Material.PILLAGER_SPAWN_EGG, Material.POLAR_BEAR_SPAWN_EGG, Material.PUFFERFISH_SPAWN_EGG,
      Material.RABBIT_SPAWN_EGG, Material.RAVAGER_SPAWN_EGG, Material.SALMON_SPAWN_EGG, Material.SHEEP_SPAWN_EGG,
      Material.SHULKER_SPAWN_EGG, Material.SKELETON_HORSE_SPAWN_EGG, Material.SKELETON_SPAWN_EGG,
      Material.SLIME_SPAWN_EGG, Material.SPIDER_SPAWN_EGG, Material.SQUID_SPAWN_EGG, Material.STRAY_SPAWN_EGG,
      Material.TRADER_LLAMA_SPAWN_EGG, Material.TROPICAL_FISH_SPAWN_EGG, Material.TURTLE_SPAWN_EGG,
      Material.VEX_SPAWN_EGG, Material.VILLAGER_SPAWN_EGG, Material.VINDICATOR_SPAWN_EGG,
      Material.WANDERING_TRADER_SPAWN_EGG, Material.WITCH_SPAWN_EGG, Material.WITHER_SKELETON_SPAWN_EGG,
      Material.WOLF_SPAWN_EGG, Material.ZOMBIE_HORSE_SPAWN_EGG, Material.ZOMBIE_SPAWN_EGG,
      Material.ZOMBIE_VILLAGER_SPAWN_EGG,
      // 1.16 Additions
      Material.HOGLIN_SPAWN_EGG, Material.STRIDER_SPAWN_EGG, Material.ZOGLIN_SPAWN_EGG,
      Material.ZOMBIFIED_PIGLIN_SPAWN_EGG, };

  public static final Material[] SWORDS = { Material.DIAMOND_SWORD, Material.GOLDEN_SWORD, Material.IRON_SWORD,
      Material.NETHERITE_SWORD, Material.STONE_SWORD, Material.WOODEN_SWORD, };

  public static final Material[] THROWABLE = { Material.EGG, // ThrowableProjectile
      Material.ENDER_PEARL, // ThrowableProjectile
      Material.ENDER_EYE, // EnderSignal
      Material.FIREWORK_ROCKET, // Firework
      Material.SNOWBALL, // ThrowableProjectile
      Material.SPLASH_POTION, // ThrownPotion
      Material.LINGERING_POTION, // ThrownPotion
      Material.EXPERIENCE_BOTTLE, // ThrowableProjectile
      Material.TRIDENT // Trident
  };

  /**
   * Returns the foods that an entity will consume to bread.
   *
   * @param type The entity type we're looking for.
   * @return An array of materials the entity will eat or null.
   */
  public static HashSet<Material> getConsumedInteractableMaterials(EntityType type) {
    HashSet<Material> materials = new HashSet<Material>();

    switch (type) {
    case HORSE:
    case DONKEY:
    case MULE:
      materials.add(Material.APPLE);
      materials.add(Material.ENCHANTED_GOLDEN_APPLE);
      materials.add(Material.GOLDEN_APPLE);
      materials.add(Material.GOLDEN_CARROT);
      materials.add(Material.HAY_BLOCK);
      materials.add(Material.SUGAR);
      materials.add(Material.WHEAT);
      materials.add(Material.HAY_BLOCK);
      break;
    case PIG:
      materials.add(Material.CARROT);
      materials.add(Material.POTATO);
      materials.add(Material.BEETROOT);
      materials.add(Material.SADDLE);
      break;
    case SHEEP:
      materials.add(Material.WHEAT);
      materials.add(Material.SHEARS);
      for (Material material : DYES) {
        materials.add(material);
      }
      break;
    case COW:
      materials.add(Material.WHEAT);
      materials.add(Material.BUCKET);
      break;
    case MUSHROOM_COW:
      materials.add(Material.WHEAT);
      materials.add(Material.BUCKET);
      materials.add(Material.SHEARS);
      break;
    case CHICKEN:
      materials.add(Material.BEETROOT_SEEDS);
      materials.add(Material.MELON_SEEDS);
      materials.add(Material.PUMPKIN_SEEDS);
      materials.add(Material.WHEAT_SEEDS);
      break;
    case WOLF:
      materials.add(Material.BEEF);
      materials.add(Material.BONE);
      materials.add(Material.CHICKEN);
      materials.add(Material.COOKED_BEEF);
      materials.add(Material.COOKED_CHICKEN);
      materials.add(Material.COOKED_MUTTON);
      materials.add(Material.COOKED_PORKCHOP);
      materials.add(Material.COOKED_RABBIT);
      materials.add(Material.MUTTON);
      materials.add(Material.PORKCHOP);
      materials.add(Material.RABBIT);
      materials.add(Material.ROTTEN_FLESH);
      for (Material material : DYES) {
        materials.add(material);
      }
      break;
    case CAT:
      materials.add(Material.COD);
      materials.add(Material.COOKED_COD);
      for (Material material : DYES) {
        materials.add(material);
      }
      break;
    case OCELOT:
      materials.add(Material.COD);
      materials.add(Material.COOKED_COD);
      break;
    case RABBIT:
      materials.add(Material.CARROT);
      materials.add(Material.DANDELION);
      materials.add(Material.GOLDEN_CARROT);
      break;
    case TRADER_LLAMA:
    case LLAMA:
      materials.add(Material.HAY_BLOCK);
      materials.add(Material.WHEAT);
      break;
    case TURTLE:
      materials.add(Material.SEAGRASS);
      break;
    case PANDA:
      materials.add(Material.BAMBOO);
      break;
    case FOX:
      materials.add(Material.SWEET_BERRIES);
      break;
    case PARROT:
      materials.add(Material.COOKIE);
    case BEE:
      materials.add(Material.DANDELION);
      materials.add(Material.POPPY);
      materials.add(Material.BLUE_ORCHID);
      materials.add(Material.ALLIUM);
      materials.add(Material.AZURE_BLUET);
      materials.add(Material.RED_TULIP);
      materials.add(Material.ORANGE_TULIP);
      materials.add(Material.WHITE_TULIP);
      materials.add(Material.PINK_TULIP);
      materials.add(Material.OXEYE_DAISY);
      materials.add(Material.CORNFLOWER);
      materials.add(Material.LILY_OF_THE_VALLEY);
      materials.add(Material.WITHER_ROSE);
      break;
    default:
      break;
    }

    return materials;
  }

  /**
   * Returns the dye color produced by the material
   *
   * @param material The target material.
   * @return The dye color produced.
   */
  public static DyeColor getDyeColor(Material material) {
    switch (material) {
    case BLACK_DYE:
      return DyeColor.BLACK;
    case BLUE_DYE:
      return DyeColor.BLUE;
    case BROWN_DYE:
      return DyeColor.BROWN;
    case CYAN_DYE:
      return DyeColor.CYAN;
    case GRAY_DYE:
      return DyeColor.GRAY;
    case GREEN_DYE:
      return DyeColor.GREEN;
    case LIGHT_BLUE_DYE:
      return DyeColor.LIGHT_BLUE;
    case LIGHT_GRAY_DYE:
      return DyeColor.LIGHT_GRAY;
    case LIME_DYE:
      return DyeColor.LIME;
    case MAGENTA_DYE:
      return DyeColor.MAGENTA;
    case ORANGE_DYE:
      return DyeColor.ORANGE;
    case PINK_DYE:
      return DyeColor.PINK;
    case PURPLE_DYE:
      return DyeColor.PURPLE;
    case RED_DYE:
      return DyeColor.RED;
    case WHITE_DYE:
      return DyeColor.WHITE;
    case YELLOW_DYE:
      return DyeColor.YELLOW;
    default:
      return null;
    }
  }

  /**
   * Returns the equipment slot the item belongs to.
   *
   * @param item The target item.
   * @return An equipment slot or null.
   */
  public static EquipmentSlot getEquipmentSlot(ItemStack item) {
    if ((item == null) || (item.getType() == null)) {
      return null;
    }
    return getEquipmentSlot(item.getType());
  }

  /**
   * Returns the equipment slot the material belongs to.
   *
   * @param material The target material.
   * @return An equipment slot or null.
   */
  public static EquipmentSlot getEquipmentSlot(Material material) {
    if (isCategory(material, HEADGEAR)) {
      return EquipmentSlot.HEAD;
    } else if (isCategory(material, CHESTPLATES)) {
      return EquipmentSlot.CHEST;
    } else if (isCategory(material, LEGGINGS)) {
      return EquipmentSlot.LEGS;
    } else if (isCategory(material, BOOTS)) {
      return EquipmentSlot.FEET;
    } else if (isCategory(material, OFFHANDS)) {
      return EquipmentSlot.OFF_HAND;
    }
    return null;
  }

  /**
   * Returns the full inventory of a player.
   *
   * @param player The player being targeted.
   * @return An array of item stacks.
   */
  public static ItemStack[] getFullInventory(Player player) throws IllegalArgumentException, NullPointerException {
    if (player == null) {
      throw new NullPointerException("Player argument cannot be null");
    }
    return getFullInventory(player.getInventory());
  }

  /**
   * Returns the full inventory of a player.
   *
   * @param inventory The inventory being used.
   * @return An array of item stacks.
   */
  public static ItemStack[] getFullInventory(PlayerInventory inventory)
      throws IllegalArgumentException, NullPointerException {
    return getItemFromSlots(inventory, EquipmentSlot.values());
  }

  /**
   * Returns an item from a player's inventory.
   *
   * @param player The player being targeted.
   * @param slot   The target slot.
   * @return An or null.
   */
  public static ItemStack getItemFromSlot(Player player, EquipmentSlot slot)
      throws IllegalArgumentException, NullPointerException {
    if (player == null) {
      throw new NullPointerException("Player argument cannot be null");
    }
    return getItemFromSlot(player.getInventory(), slot);
  }

  /**
   * Returns an item from a player's inventory.
   *
   * @param inventory The inventory being targeted.
   * @param slot      The target slot.
   * @return An or null.
   */
  public static ItemStack getItemFromSlot(PlayerInventory inventory, EquipmentSlot slot)
      throws IllegalArgumentException, NullPointerException {
    if (inventory == null) {
      throw new NullPointerException("PlayerInventory argument cannot be null");
    }
    if (slot == null) {
      throw new NullPointerException("EquipmentSlot argument cannot be null");
    }

    switch (slot) {
    case CHEST:
      return inventory.getChestplate();
    case FEET:
      return inventory.getBoots();
    case HAND:
      return inventory.getItemInMainHand();
    case HEAD:
      return inventory.getHelmet();
    case LEGS:
      return inventory.getLeggings();
    case OFF_HAND:
      return inventory.getItemInOffHand();
    default:
      return null;
    }
  }

  /**
   * Returns specific slots from the inventory of a player.
   *
   * @param player The player being targeted.
   * @param slots  The slots being updated.
   * @return An array of item stacks.
   */
  public static ItemStack[] getItemFromSlots(Player player, EquipmentSlot[] slots)
      throws IllegalArgumentException, NullPointerException {
    if (player == null) {
      throw new NullPointerException("Player argument cannot be null");
    }

    return getItemFromSlots(player.getInventory(), slots);
  }

  /**
   * Returns specific slots from the inventory of a player.
   *
   * @param inventory The inventory being used.
   * @param slots The slots we're looking for
   * @return An array of item stacks.
   */
  public static ItemStack[] getItemFromSlots(PlayerInventory inventory, EquipmentSlot[] slots)
      throws IllegalArgumentException, NullPointerException {
    if (inventory == null) {
      throw new NullPointerException("PlayerInventory argument cannot be null");
    }
    ItemStack items[] = new ItemStack[slots.length];
    for (int i = 0; i < slots.length; i++) {
      items[i] = getItemFromSlot(inventory, slots[i]);
    }
    return items;
  }

  /**
   * Checks to see if an item's material falls under a certain category.
   *
   * @param item     The target item stack
   * @param category The category of possible materials
   * @return True if the material is found in the array.
   */
  public static boolean isCategory(ItemStack item, Material[] category) {
    if ((item == null) || (item.getType() == null)) {
      return false;
    }
    return isCategory(item.getType(), category);
  }

  /**
   * Checks to see if material falls under a certain category.
   *
   * @param target The category of possible materials
   * @param category An array of materials we're looking for.
   * @return True if the material is found in the array.
   */
  public static boolean isCategory(Material target, Material[] category) {
    for (Material material : category) {
      if (target == material) {
        return true;
      }
    }
    return false;
  }
}
