package io.github.reconsolidated.atlasitemprovider.CustomItems.MysteryEnchantedBook;

import io.github.reconsolidated.atlasitemprovider.CustomItems.Anvil.EnchantmentsAnvil;
import io.github.reconsolidated.atlasitemprovider.CustomItems.CustomEnchants.CustomEnchant;
import io.github.reconsolidated.atlasitemprovider.CustomItems.Rarity;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static io.github.reconsolidated.atlasitemprovider.Utils.PersistenceUtils.getInt;
import static io.github.reconsolidated.atlasitemprovider.Utils.Utils.tr;

public class MysteryBook {

    private final MysteryBookType type;
    private final ItemStack item;
    private final int chance;

    public MysteryBook(ItemStack book) {
        this.item = book;
        this.type = MysteryBookType.valueOf(book.getItemMeta().getPersistentDataContainer().get(MysteryBookManager.bookTypeKey, PersistentDataType.STRING));
        this.chance = getInt(book.getItemMeta(), MysteryBookManager.bookLuckKey);
    }

    public static ItemStack getMysteryBookItem(MysteryBookType type, int chance) {
        ItemStack book = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta meta = book.getItemMeta();
        meta.getPersistentDataContainer().set(MysteryBookManager.bookTypeKey, PersistentDataType.STRING, type.name());
        meta.getPersistentDataContainer().set(MysteryBookManager.bookLuckKey, PersistentDataType.INTEGER, chance);

        meta.displayName(getDisplayName(type));
        meta.lore(getLore(type, chance));


        book.setItemMeta(meta);
        return book;
    }

    private static List<Component> getLore(MysteryBookType type, int chance) {
        return List.of(
                Component.text(tr("")),
                Component.text(tr("&e%s Chance".formatted(getChanceName(chance)))),
                Component.text(tr("")),
                Component.text(tr("&eClick to open!"))
                );
    }

    private static String getChanceName(int chance) {
        if (chance < 16) {
            return "Poor";
        }
        if (chance < 31) {
            return "Bad";
        }
        if (chance < 51) {
            return "Decent";
        }
        if (chance < 71) {
            return "Good";
        }
        if (chance < 86) {
            return "Great";
        }
        return "Amazing";
    }

    private static Component getDisplayName(MysteryBookType type) {
        switch (type) {
            case COMMON -> {
                return Component.text(tr("&fCommon Mystery Enchanted Book"));
            }
            case UNCOMMON -> {
                return Component.text(tr("&aUncommon Mystery Enchanted Book"));
            }
            case RARE -> {
                return Component.text(tr("&bRare Mystery Enchanted Book"));
            }
            case EPIC -> {
                return Component.text(tr("&e&lEpic Mystery Enchanted Book"));
            }
            case MULTI -> {
                return Component.text(tr("&4Mystery Enchanted Book"));
            }

        }
        return Component.text(tr("&7Unknown Type Enchanted Book"));
    }

    public void open(Player player) {
        ItemStack result = EnchantmentsAnvil.getEnchantedBook(chance);
        Random random = new Random();
        List<CustomEnchant> enchants = getEnchants();
        CustomEnchant enchant = enchants.get(random.nextInt(enchants.size()));
        enchant.set(result, random.nextInt(enchant.getMaxLevel()+1));

        player.getInventory().removeItem(item);
        player.getInventory().addItem(result);
        player.sendMessage(tr("&aMystery Enchanted Book opened!"));

        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_TRADE, 3, 5);
    }

    private List<CustomEnchant> getEnchants() {
        List<CustomEnchant> enchants = new ArrayList<>();
        Rarity rarity = Rarity.valueOf(type.name());
        if (type != MysteryBookType.MULTI) {
            for (CustomEnchant enchant : CustomEnchant.allEnchants) {
                if (enchant.getRarity() == rarity) {
                    enchants.add(enchant);
                }
            }
        } else {
            enchants.addAll(CustomEnchant.allEnchants);
        }
        return enchants;
    }

}
