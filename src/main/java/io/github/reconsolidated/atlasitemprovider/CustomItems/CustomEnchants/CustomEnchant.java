package io.github.reconsolidated.atlasitemprovider.CustomItems.CustomEnchants;

import io.github.reconsolidated.atlasitemprovider.AtlasItemProvider;
import io.github.reconsolidated.atlasitemprovider.CustomItems.LoreProvider;
import io.github.reconsolidated.atlasitemprovider.CustomItems.Rarity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public abstract class CustomEnchant {
    protected static List<CustomEnchant> allEnchants = new ArrayList<>();
    protected int maxLevel = 4;

    private final String name;
    private final String displayName;
    @Getter
    protected final Rarity rarity;

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

    public CustomEnchant(String name, String displayName, Rarity rarity) {
        this.name = name;
        this.displayName = displayName;
        this.rarity = rarity;
    }

    public void set(ItemStack item, int value) {
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(getKey(), PersistentDataType.INTEGER, value);
        item.setItemMeta(meta);
        meta.lore(LoreProvider.getLore(item));
        item.setItemMeta(meta);

        item.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        item.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
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
        return rarity.getChatStyle() + displayName;
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
        allEnchants.add(new AquaAffinity());
        allEnchants.add(new Aquatic());
        allEnchants.add(new BlastProtection());
        allEnchants.add(new Bully());
        allEnchants.add(new ChaosPrison());
        allEnchants.add(new ChimareasFireblast());
        allEnchants.add(new CronusCropduster());
        allEnchants.add(new DarkKnight());
        allEnchants.add(new Destruction());
        allEnchants.add(new Efficiency());
        allEnchants.add(new Evacuate());
        allEnchants.add(new ExplosiveArrows());
        allEnchants.add(new FeatherFalling());
        allEnchants.add(new FinancialLoss());
        allEnchants.add(new FireAspect());
        allEnchants.add(new Fireproof());
        allEnchants.add(new FireProtection());
        allEnchants.add(new Flame());
        allEnchants.add(new Forge());
        allEnchants.add(new FrostWalker());
        allEnchants.add(new Getaway());
        allEnchants.add(new GlowingAura());
        allEnchants.add(new GuardianAngel());
        allEnchants.add(new HadeSoulTrade());
        allEnchants.add(new Haste());
        allEnchants.add(new Headless());
        allEnchants.add(new Infinity());
        allEnchants.add(new Juggernaut());
        allEnchants.add(new Knockback());
        allEnchants.add(new LightsOut());
        allEnchants.add(new LightWeight());
        allEnchants.add(new LuckOfTheSea());
        allEnchants.add(new Magma());
        allEnchants.add(new MedusaMadness());
        allEnchants.add(new PegasusDoubleJump());
        allEnchants.add(new PoseidonCurse());
        allEnchants.add(new Power());
        allEnchants.add(new ProjectileProtection());
        allEnchants.add(new Robber());
        allEnchants.add(new Riptide());
        allEnchants.add(new Smite());
        allEnchants.add(new Speed());
        allEnchants.add(new StrongWilled());
        allEnchants.add(new Sturdy());
        allEnchants.add(new SuperMiner());
        allEnchants.add(new SweepingEdge());
        allEnchants.add(new Swole());
        allEnchants.add(new Telepathy());
        allEnchants.add(new Thief());
        allEnchants.add(new Thorns());
        allEnchants.add(new Unbreakable());
        allEnchants.add(new WellFed());
        allEnchants.add(new Wizard());
        allEnchants.add(new ZeusWrath());

        allEnchants.sort(Comparator.comparing(o -> o.rarity));
    }

    public int getMaxLevel() {
        return maxLevel;
    }
}
