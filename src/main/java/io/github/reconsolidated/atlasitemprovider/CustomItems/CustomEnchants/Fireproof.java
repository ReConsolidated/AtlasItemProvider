package io.github.reconsolidated.atlasitemprovider.CustomItems.CustomEnchants;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

import static io.github.reconsolidated.atlasitemprovider.AtlasItemProvider.plugin;

public class Fireproof extends CustomEnchant implements Listener {
    private static Fireproof instance = null;


    public Fireproof() {
        super("fireproof", ChatColor.YELLOW + "" + ChatColor.BOLD + "Fireproof");
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("Attempted to create 2nd copy of Fireproof Enchant (report this to developer)");
        }

        setAcceptsArmors(true);
        setAcceptsSwords(true);
        setAcceptsBows(true);
        setAcceptsGloves(true);
        setAcceptsHoes(true);

        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }


    @EventHandler
    public void onDeath(EntityDamageEvent event) {
        if (event.getEntity() instanceof Item) {
            if (event.getCause().equals(EntityDamageEvent.DamageCause.LAVA)) {
                Item item = (Item) event.getEntity();
                ItemStack itemStack = item.getItemStack();
                if (get(itemStack) > 0) {
                    event.setCancelled(true);
                }
            }
        }
    }


    public static Fireproof getInstance() {
        return instance;
    }
}
