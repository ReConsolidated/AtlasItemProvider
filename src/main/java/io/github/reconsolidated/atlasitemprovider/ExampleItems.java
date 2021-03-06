package io.github.reconsolidated.atlasitemprovider;

import io.github.reconsolidated.atlasitemprovider.CustomItems.Anvil.EnchantmentsAnvil;
import io.github.reconsolidated.atlasitemprovider.CustomItems.CustomEnchants.ChaosPrison;
import io.github.reconsolidated.atlasitemprovider.CustomItems.CustomEnchants.CustomEnchant;
import io.github.reconsolidated.atlasitemprovider.CustomItems.CustomEnchants.HadeSoulTrade;
import io.github.reconsolidated.atlasitemprovider.CustomItems.CustomEnchants.ZeusWrath;
import io.github.reconsolidated.atlasitemprovider.CustomItems.Items.CustomBow;
import io.github.reconsolidated.atlasitemprovider.CustomItems.Items.CustomSword;
import io.github.reconsolidated.atlasitemprovider.CustomItems.Items.CustomTool;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.List;

public class ExampleItems {
    public static void init(Plugin plugin, File dataFolder) {
        YamlConfiguration config = CustomConfig.loadCustomConfig("example", dataFolder, true);
        assert config != null : "Couldn't load config: example";
        ItemStack plainPickaxe = new ItemStack(Material.DIAMOND_PICKAXE);
        plainPickaxe.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        config.set("diamond_pickaxe_prot_1", plainPickaxe);

        ItemStack chestPlateWithKey = new ItemStack(Material.IRON_CHESTPLATE);
        ItemMeta meta = chestPlateWithKey.getItemMeta();
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "test"),
                PersistentDataType.STRING, "example persistent data");
        meta.getPersistentDataContainer().set(new NamespacedKey(plugin, "test_2"),
                PersistentDataType.INTEGER, 15);
        meta.setUnbreakable(true);
        meta.lore(List.of(Component.text("Line 1").color(TextColor.color(150, 12, 143)),
                Component.text("Line 2")));
        chestPlateWithKey.setItemMeta(meta);

        chestPlateWithKey.setAmount(5);
        chestPlateWithKey.addItemFlags(ItemFlag.HIDE_DESTROYS);
        config.set("chestplate_buffed", chestPlateWithKey);


        ItemStack customSword = CustomSword.createCustomSword("&#00FF22&lExcalibur", Material.DIAMOND_SWORD,
                7, 100, 50, 40);
        ChaosPrison.getInstance().set(customSword, 2);
        ZeusWrath.getInstance().set(customSword, 3);
        HadeSoulTrade.getInstance().set(customSword, 3);
        config.set("custom_sword",
                customSword);

        config.set("custom_bow",
                CustomBow.createCustomBow("&cDangerous Bow", Material.BOW,
                        30, 100, 10, 40));

        config.set("custom_crossbow",
                CustomBow.createCustomBow("&#55FF66Dangerous crossbow", Material.CROSSBOW,
                        30, 100, 50, 40));


        config.set("custom_shovel",
                CustomTool.createCustomTool("&#55FF66Good Shovel", Material.DIAMOND_SHOVEL,
                         100, 50));


        config.set("enchant_dust_30",
                EnchantmentsAnvil.getChanceDust(30));


        CustomConfig.saveCustomConfig("example", dataFolder, config);
    }
}
