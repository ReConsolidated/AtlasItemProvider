package io.github.reconsolidated.atlasitemprovider.CustomItems.ItemTraits;

import dev.simplix.plugins.atlascoredata.AtlasCoreDataAPI;
import dev.simplix.plugins.atlascoredata.model.RankedItem;
import io.github.reconsolidated.atlasitemprovider.AtlasItemProvider;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public class Rank extends ItemTrait {
    private static Rank instance;

    public static Rank getInstance() {
        return instance;
    }

    public Rank() {
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("Attempted to create second instance of Rank ItemTrait (this is a bug, report this to the developer)");
        }
    }

    @Override
    public Double get(ItemStack item) {
        List<RankedItem> items = AtlasCoreDataAPI.instance().rankedItemStorage().items();

        for (RankedItem ri : items) {
            if (ri.id() == getID(item)) {
                AtlasCoreDataAPI.instance().rankedItemStorage().delete(ri);
            }
        }
        RankedItem newItem = new RankedItem(AtlasItemProvider.plugin.getItemName(item), getID(item), (int) getRankScore(item));
        AtlasCoreDataAPI.instance().rankedItemStorage().save(newItem);
        items.sort(Comparator.comparing(RankedItem::score));
        for (int i = 0; i<items.size(); i++) {
            RankedItem ri = items.get(i);
            if (ri.id() == getID(item)) {
                return (double)i+1;
            }
        }
        throw new RuntimeException("Exception at getRank method, there is no item in list despite being added.");
    }

    private double getRankScore(ItemStack item) {
        double score = 0;
        for (ItemTrait trait : ItemTrait.getTraitsSet()) {
            Double value = trait.get(item);
            if (value != null) {
                score += value;
            }
        }
        return score;
    }


    /**
     *
     * @param item
     * @return ID of the item. If the item doesn't have any it assigns a new one
     */
    public static long getID(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return System.currentTimeMillis();
        if (meta.getPersistentDataContainer().get(getIDKey(), PersistentDataType.LONG) != null) {
            return meta.getPersistentDataContainer().get(getIDKey(), PersistentDataType.LONG);
        }
        long id = System.currentTimeMillis();
        meta.getPersistentDataContainer().set(getIDKey(), PersistentDataType.LONG, id);
        item.setItemMeta(meta);
        return id;
    }

    private static NamespacedKey getIDKey() {
        return new NamespacedKey(AtlasItemProvider.plugin, "item_id");
    }

    @Override
    public NamespacedKey getKey() {
        return new NamespacedKey(AtlasItemProvider.plugin, "item_rank");
    }
}
