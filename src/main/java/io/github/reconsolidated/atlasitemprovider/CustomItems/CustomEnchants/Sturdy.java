package io.github.reconsolidated.atlasitemprovider.CustomItems.CustomEnchants;

import io.github.reconsolidated.atlasitemprovider.CustomItems.Rarity;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

import static io.github.reconsolidated.atlasitemprovider.AtlasItemProvider.plugin;

public class Sturdy extends CustomEnchant implements Listener {
    private static Sturdy instance = null;
    private final int baseChance = 10;

    public Sturdy() {
        super("sturdy", ChatColor.YELLOW + "" + ChatColor.BOLD + "Sturdy", Rarity.COMMON);
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("Attempted to create 2nd copy of Sturdy Enchant (report this to developer)");
        }
        maxLevel = 5;

        setAcceptsArmors(true);
        setAcceptsSwords(true);
        setAcceptsBows(true);
        setAcceptsGloves(true);
        setAcceptsHoes(true);
        setAcceptsPickaxes(true);

        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }


    @EventHandler
    public void onItemDamage(PlayerItemDamageEvent event) {
        ItemStack item = event.getItem();
        int level = get(item);
        if (level > 0) {
            Random random = new Random();
            if (baseChance * level > random.nextInt(100)) {
                event.setCancelled(true);
            }
        }

    }


    public static Sturdy getInstance() {
        return instance;
    }
}
