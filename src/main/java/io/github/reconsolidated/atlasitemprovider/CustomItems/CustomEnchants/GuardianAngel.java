package io.github.reconsolidated.atlasitemprovider.CustomItems.CustomEnchants;

import io.github.reconsolidated.atlasitemprovider.CustomItems.Rarity;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

import static io.github.reconsolidated.atlasitemprovider.AtlasItemProvider.plugin;

public class GuardianAngel extends CustomEnchant implements Listener {
    private static GuardianAngel instance = null;


    public GuardianAngel() {
        super("guardian_angel", "Guardian Angel", Rarity.UNCOMMON);
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("Attempted to create 2nd copy of GuardianAngel Enchant (report this to developer)");
        }

        setAcceptsChestplates(true);
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
    }


    @EventHandler
    public void onPotionEffect(EntityPotionEffectEvent event) {
        if (event.getNewEffect() == null) return;
        if (!(event.getEntity() instanceof Player)) return;
        if (isNegativeEffect(event.getNewEffect().getType())) {
            Player player = (Player) event.getEntity();
            for (ItemStack item : player.getInventory().getArmorContents()) {
                if (get(item) > 0) {
                    Random random = new Random();
                    if (random.nextBoolean()) {
                        event.setCancelled(true);
                    }
                    return;
                }
            }
        }
    }

    private boolean isNegativeEffect(PotionEffectType type) {
        return type == PotionEffectType.BAD_OMEN ||
                type == PotionEffectType.BLINDNESS ||
                type == PotionEffectType.CONFUSION ||
                type == PotionEffectType.HARM ||
                type == PotionEffectType.HUNGER ||
                type == PotionEffectType.POISON ||
                type == PotionEffectType.SLOW ||
                type == PotionEffectType.SLOW_DIGGING ||
                type == PotionEffectType.WEAKNESS ||
                type == PotionEffectType.WITHER;
    }


    public static GuardianAngel getInstance() {
        return instance;
    }
}

