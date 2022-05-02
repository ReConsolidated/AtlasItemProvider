package io.github.reconsolidated.atlasitemprovider.CustomItems.CustomEnchants;

import io.github.reconsolidated.atlasitemprovider.AtlasItemProvider;
import io.github.reconsolidated.atlasitemprovider.CustomItems.LoreProvider;
import lombok.AccessLevel;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class CustomEnchant {
    protected static List<CustomEnchant> allEnchants = new ArrayList<>();
    protected int maxLevel = 4;

    private final String name;
    private final String displayName;

    @Setter(AccessLevel.PROTECTED)
    private boolean acceptsSwords;
    @Setter(AccessLevel.PROTECTED)
    private boolean acceptsPickaxes;
    @Setter(AccessLevel.PROTECTED)
    private boolean acceptsAxes;
    @Setter(AccessLevel.PROTECTED)
    private boolean acceptsBows;
    @Setter(AccessLevel.PROTECTED)
    private boolean acceptsHoes;
    @Setter(AccessLevel.PROTECTED)
    private boolean acceptsBoots;
    @Setter(AccessLevel.PROTECTED)
    private boolean acceptsHelmets;
    @Setter(AccessLevel.PROTECTED)
    private boolean acceptsArmors;
    @Setter(AccessLevel.PROTECTED)
    private boolean acceptsChestplates;
    @Setter(AccessLevel.PROTECTED)
    private boolean acceptsTools;
    @Setter(AccessLevel.PROTECTED)
    private boolean acceptsGloves;

    public CustomEnchant(String name, String displayName) {
        this.name = name;
        this.displayName = displayName;
    }

    public void set(ItemStack item, int value) {
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(getKey(), PersistentDataType.INTEGER, value);
        item.setItemMeta(meta);
        meta.lore(LoreProvider.getLore(item));
        item.setItemMeta(meta);
    }

    public Integer get(ItemStack item) {
        if (item == null) return 0;
        if (item.getItemMeta() == null) return 0;
        Integer value = item.getItemMeta().getPersistentDataContainer().get(getKey(), PersistentDataType.INTEGER);
        if (value == null) return 0;
        return value;
    }

    public NamespacedKey getKey() {
        return new NamespacedKey(AtlasItemProvider.plugin, "enchant_" + name);
    }

    public String getName() {
        return name;
    }

    public static CustomEnchant getEnchant(String name) {
        for (CustomEnchant enchant : allEnchants) {
            if (enchant.getName().equals(name)) {
                return enchant;
            }
        }
        return null;
    }

    public static Map<CustomEnchant, Integer> getEnchants(ItemStack item) {
        Map<CustomEnchant, Integer> result = new HashMap<>();
        for (NamespacedKey key : item.getItemMeta().getPersistentDataContainer().getKeys()) {
            for (CustomEnchant enchant : allEnchants) {
                if (enchant.getKey().equals(key)) {
                    result.put(enchant, enchant.get(item));
                }
            }
        }
        return result;
    }


    public String getDisplayName() {
        return displayName;
    }

    public boolean canBeAppliedTo(ItemStack item) {
        if (item.getType().toString().contains("SWORD")) {
            return acceptsSwords;
        }
        if (item.getType().toString().contains("PICKAXE")) {
            return acceptsPickaxes || acceptsTools;
        }
        if (item.getType().toString().contains("AXE")) {
            return acceptsAxes || acceptsTools;
        }
        if (item.getType().toString().contains("SHOVEL")) {
            return acceptsTools;
        }
        if (item.getType() == Material.BOW) {
            return acceptsBows;
        }
        if (item.getType().toString().contains("HOE")) {
            return acceptsHoes;
        }
        if (item.getType().toString().contains("BOOTS")) {
            return acceptsBoots || acceptsArmors;
        }
        if (item.getType().toString().contains("HELMET")) {
            return acceptsHelmets || acceptsArmors;
        }
        if (item.getType().toString().contains("CHESTPLATE")) {
            return acceptsArmors || acceptsChestplates;
        }
        if (item.getType().toString().contains("LEGGINGS")) {
            return acceptsArmors;
        }
        return false;
    }

    public static void init() {
        allEnchants.add(new Absorb());
        allEnchants.add(new AchillesHeel());
        allEnchants.add(new Antigravity());
        allEnchants.add(new Aquatic());
        allEnchants.add(new Bully());
        allEnchants.add(new ChaosPrison());
        allEnchants.add(new ChimareasFireblast());
        allEnchants.add(new CronusCropduster());
        allEnchants.add(new DarkKnight());
        allEnchants.add(new Destruction());
        allEnchants.add(new Evacuate());
        allEnchants.add(new ExplosiveArrows());
        allEnchants.add(new Fireproof());
        allEnchants.add(new Forge());
        allEnchants.add(new GlowingAura());
        allEnchants.add(new GuardianAngel());
        allEnchants.add(new HadeSoulTrade());
        allEnchants.add(new Haste());
        allEnchants.add(new Headless());
        allEnchants.add(new Juggernaut());
        allEnchants.add(new LightsOut());
        allEnchants.add(new LightWeight());
        allEnchants.add(new Magma());
        allEnchants.add(new MedusaMadness());
        allEnchants.add(new PoseidonCurse());
        allEnchants.add(new Robber());
        allEnchants.add(new StrongWilled());
        allEnchants.add(new Telepathy());
        allEnchants.add(new Thief());
        allEnchants.add(new Unbreakable());
        allEnchants.add(new WellFed());
        allEnchants.add(new Wizard());
        allEnchants.add(new ZeusWrath());

    }

    public int getMaxLevel() {
        return maxLevel;
    }
}
