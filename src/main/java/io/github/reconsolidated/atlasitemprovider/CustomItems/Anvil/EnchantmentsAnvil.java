package io.github.reconsolidated.atlasitemprovider.CustomItems.Anvil;

import io.github.reconsolidated.atlasitemprovider.AtlasItemProvider;
import io.github.reconsolidated.atlasitemprovider.ColorHelper;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;
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
                    }
                }
                if (random.nextInt(100) < chance) {
                    event.setCancelled(true);
                    event.getWhoClicked().setItemOnCursor(inventory.getFirstItem());
                    inventory.setFirstItem(new ItemStack(Material.AIR));
                    inventory.setSecondItem(new ItemStack(Material.AIR));
                    Player player = (Player) event.getWhoClicked();
                    player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_BREAK, 3, 1);

                    event.getWhoClicked().sendMessage("unlucky");
                } else {
                    event.getWhoClicked().sendMessage("lucky");
                }
            } else {
                event.setCancelled(true);
            }
        }
    }

    public static NamespacedKey getAnvilSuccessChanceKey() {
        return new NamespacedKey(AtlasItemProvider.plugin, "anvil_success_chance");
    }

    public static ItemStack getEnchantedBook(int chance) {
        ItemStack item = new ItemStack(Material.ENCHANTED_BOOK);
        setAnvilChance(item, chance);
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

