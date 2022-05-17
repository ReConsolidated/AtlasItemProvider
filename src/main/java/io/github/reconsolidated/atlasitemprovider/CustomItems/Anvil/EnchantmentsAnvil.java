package io.github.reconsolidated.atlasitemprovider.CustomItems.Anvil;

import io.github.reconsolidated.atlasitemprovider.AtlasItemProvider;
import io.github.reconsolidated.atlasitemprovider.ColorHelper;
import io.github.reconsolidated.atlasitemprovider.CustomItems.CustomEnchants.CustomEnchant;
import io.github.reconsolidated.atlasitemprovider.CustomItems.ItemTraits.Durability;
import io.github.reconsolidated.atlasitemprovider.CustomItems.ItemTraits.Rarity;
import io.github.reconsolidated.atlasitemprovider.CustomItems.LoreProvider;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.inventory.PrepareSmithingEvent;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class EnchantmentsAnvil implements Listener {

    public static ItemStack getUpgradeResult(ItemStack inputEquipment, ItemStack inputMineral) {
        int currentChance = getAnvilChance(inputEquipment);
        int addedChance = getAnvilChance(inputMineral);
        ItemStack copy = inputEquipment.clone();
        setAnvilChance(copy, Math.min(100, currentChance + addedChance));
        return copy;
    }

    @EventHandler
    public void onAnvilUse(InventoryClickEvent event) {
        if (event.getClickedInventory() == null
                || event.getClickedInventory().getType() != InventoryType.ANVIL) return;


        AnvilInventory inventory = (AnvilInventory) event.getClickedInventory();
        ItemStack clicked = inventory.getItem(event.getSlot());
        if (clicked != null && clicked.equals(inventory.getResult())) {
            if (event.getClick().isLeftClick()) {
                Random random = new Random();
                int chance = 100;
                if (inventory.getSecondItem() != null && inventory.getSecondItem().getItemMeta() != null) {
                    Integer potentialChance = inventory.getSecondItem().getItemMeta().getPersistentDataContainer().get(
                            getAnvilSuccessChanceKey(),
                            PersistentDataType.INTEGER);
                    if (potentialChance != null) {
                        chance = potentialChance;
                        event.getWhoClicked().sendMessage("chance: " + potentialChance);
                    }
                }
                if (random.nextInt(100) >= chance) {
                    event.setCancelled(true);
                    event.getWhoClicked().setItemOnCursor(inventory.getFirstItem());
                    inventory.setFirstItem(new ItemStack(Material.AIR));
                    inventory.setSecondItem(new ItemStack(Material.AIR));
                    Player player = (Player) event.getWhoClicked();
                    player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_BREAK, 3, 1);

                    event.getWhoClicked().sendMessage("unlucky");
                } else {
                    event.getWhoClicked().sendMessage("lucky");
//                    event.setCancelled(true);
//                    event.getWhoClicked().setItemOnCursor(getJoinResult(inventory.getFirstItem(), inventory.getSecondItem()));
//                    inventory.setFirstItem(new ItemStack(Material.AIR));
//                    inventory.setSecondItem(new ItemStack(Material.AIR));
//                    Player player = (Player) event.getWhoClicked();
//                    player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 3, 1);
                }
            } else {
                event.setCancelled(true);
            }
        }
    }


    @EventHandler
    public void onPrepareAnvil(PrepareAnvilEvent event) {
        ItemStack result = getJoinResult(event.getInventory().getFirstItem(), event.getInventory().getSecondItem());
        if (result != null) {
            event.setResult(result);
        }
    }


    private ItemStack getJoinResult(ItemStack firstItem, ItemStack secondItem) {
        if (firstItem == null || secondItem == null) return null;

        if (firstItem.getType().equals(secondItem.getType())) {
            ItemStack result = firstItem.clone();

            Double maxDurability = Durability.getInstance().get(firstItem);
            if (maxDurability != null) {
                int currentDurability1 = Durability.getInstance().getCurrent(firstItem);
                int currentDurability2 = Durability.getInstance().getCurrent(secondItem);
                Durability.getInstance().setCurrent(result, Math.min((int) (double) maxDurability, currentDurability1 + currentDurability2));
            }


            Map<CustomEnchant, Integer> firstItemEnchants = CustomEnchant.getEnchants(firstItem);
            Map<CustomEnchant, Integer> secondItemEnchants = CustomEnchant.getEnchants(secondItem);
            for (CustomEnchant enchant : firstItemEnchants.keySet()) {
                if (secondItemEnchants.containsKey(enchant)) {
                    int firstLvl = firstItemEnchants.get(enchant);
                    int secondLvl = secondItemEnchants.get(enchant);
                    // first enchant is bigger - nothing happens
                    if (firstLvl > secondLvl) {
                        continue;
                    }
                    // second enchant is bigger - we apply second enchant
                    else if (secondLvl > firstLvl) {
                        enchant.set(result, secondLvl);
                    }
                    // levels are equal - we apply enchant 1 bigger if possible
                    else {
                        if (enchant.getMaxLevel() > firstLvl) {
                            enchant.set(result, firstLvl + 1);
                        }
                    }
                }
            }

            LoreProvider.updateLore(result);
            return result;
        }
        return null;

    }

    public static NamespacedKey getAnvilSuccessChanceKey() {
        return new NamespacedKey(AtlasItemProvider.plugin, "anvil_success_chance");
    }

    public static ItemStack getEnchantedBook(int chance) {
        ItemStack item = new ItemStack(Material.ENCHANTED_BOOK);
        setAnvilChance(item, chance);

        ItemMeta meta = item.getItemMeta();

        meta.displayName(Component.text("§e§lEnchanted Book §a§l(%d%% success chance)".formatted(chance)).decoration(TextDecoration.ITALIC, false));

        item.setItemMeta(meta);

        Rarity.getInstance().set(item, 0);

        LoreProvider.updateLore(item);

        return item;
    }

    public static void setAnvilChance(ItemStack item, int chance) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        meta.getPersistentDataContainer().set(getAnvilSuccessChanceKey(), PersistentDataType.INTEGER, chance);
        item.setItemMeta(meta);
    }

    /**
     *
     * @param item
     * @return item's anvil chance, if anvil chance is not specified returns 100 (always successful)
     */
    public static int getAnvilChance(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return 100;

        Integer chance = meta.getPersistentDataContainer().get(getAnvilSuccessChanceKey(), PersistentDataType.INTEGER);
        if (chance != null) return chance;
        return 100;
    }

    public static ItemStack getChanceDust(int chance) {
        ItemStack item = new ItemStack(Material.SUGAR);

        ItemMeta meta = item.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
        meta.displayName(Component.text(ColorHelper.translate("&#33d45e&l" + chance + "% &#e86868&lEnchant Dust")));
        item.setItemMeta(meta);

        setAnvilChance(item, chance);

        return item;
    }

    public static boolean isChanceDust(ItemStack item) {
        return item.getItemMeta() != null && item.getType().equals(Material.SUGAR)
                && item.getItemMeta().getPersistentDataContainer().get(getAnvilSuccessChanceKey(), PersistentDataType.INTEGER) != null;
    }
}

