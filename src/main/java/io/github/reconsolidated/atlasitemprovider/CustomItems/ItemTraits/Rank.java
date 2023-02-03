package io.github.reconsolidated.atlasitemprovider.CustomItems.ItemTraits;

import io.github.reconsolidated.atlasitemprovider.AtlasItemProvider;
import io.github.reconsolidated.atlasitemprovider.rankedItems.RankedItem;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

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
        String itemName = AtlasItemProvider.plugin.getItemName(item);
        long ID = getID(item);
        List<RankedItem> items = AtlasItemProvider.plugin.getRankedItemsService().getAllByItemName(itemName);
        RankedItem currentItem = null;
        for (RankedItem ri : items) {
            if (ri.getId() == ID) {
                currentItem = ri;
            }
        }
        double score = getRankScore(item);
        score = Math.min(100.0, score);
        return 100-score;

//        if (currentItem == null) {
//            currentItem = new RankedItem(itemName, score);
//        }
//
//        AtlasCoreDataAPI.instance().rankedItemStorage().save(newItem);
//        items.add(newItem);
//        items.sort(Comparator.comparing(RankedItem::score));
//        for (int i = 0; i<items.size(); i++) {
//            RankedItem ri = items.get(i);
//            if (ri.id() == ID) {
//                return (double)i+1;
//            }
//        }
 //       throw new RuntimeException("Exception at getRank method, there is no item in list despite being added.");
    }

    private double getRankScore(ItemStack item) {
        double score = 0;
        for (ItemTrait trait : ItemTrait.getTraitsSet()) {
            if (trait instanceof Rank) continue;
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
