package io.github.reconsolidated.atlasitemprovider.MiningEntities.OilDrill;

import io.github.reconsolidated.atlasitemprovider.AtlasItemProvider;
import io.github.reconsolidated.atlasitemprovider.Translations;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.UUID;

import static io.github.reconsolidated.atlasitemprovider.Utils.PersistenceUtils.*;

public class OilDrill {
    public static NamespacedKey levelKey = new NamespacedKey(AtlasItemProvider.plugin, "oil_drill_level");
    public static NamespacedKey ownerUUIDKey = new NamespacedKey(AtlasItemProvider.plugin, "oil_drill_owner_uuid");
    private static final int maxLevel = 10;

    private final ArmorStand entity;
    private final UUID ownerUUID;
    @Getter
    private int level;


    public OilDrill(Entity entity) {
        this.entity = (ArmorStand) entity;
        this.level = getInt(entity, levelKey);
        this.ownerUUID = UUID.fromString(getString(entity, ownerUUIDKey));
    }

    public void interact(Player player) {
        if (!player.getUniqueId().equals(ownerUUID)) {
            player.sendMessage(Translations.OIL_DRILL_NOT_OWNED);
            return;
        }
        new OilDrillInventory(AtlasItemProvider.plugin, player, this);
    }

    public boolean canUpgrade(Player player) {
        return level < maxLevel && isOwnedBy(player);
    }

    private boolean isOwnedBy(Player player) {
        return ownerUUID.equals(player.getUniqueId());
    }

    public void upgrade(Player player) {
        // TODO remove cash from player
        setInt(entity, levelKey, level+1);
        level++;
    }

    public int getUpgradeCost() {
        return 2000 * level;
    }

    public ItemStack getItemStack() {
        ItemStack item = new ItemStack(Material.ARMOR_STAND);
        ItemMeta meta = item.getItemMeta();
        meta.displayName(Component.text("Oil Drill"));
        meta.lore(List.of(
                Component.text(ChatColor.GRAY + "Level: " + ChatColor.WHITE + level),
                Component.text(ChatColor.GRAY + "Place to drill!")
        ));
        item.setItemMeta(meta);
        return item;
    }
}
