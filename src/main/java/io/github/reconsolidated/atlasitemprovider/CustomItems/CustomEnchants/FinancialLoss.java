package io.github.reconsolidated.atlasitemprovider.CustomItems.CustomEnchants;

import io.github.reconsolidated.atlasitemprovider.AtlasItemProvider;
import io.github.reconsolidated.atlasitemprovider.CustomItems.Rarity;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FinancialLoss  extends CustomEnchant implements Listener {
    private static FinancialLoss instance = null;

    private final Map<UUID, Double> arrowBonusDamage;


    public FinancialLoss() {
        super("financial_loss", "Financial Loss", Rarity.COMMON);
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("Attempted to create 2nd copy of FinancialLoss Enchant (report this to developer)");
        }

        maxLevel = 5;

        setAcceptsBows(true);

        arrowBonusDamage = new HashMap<>();
        Bukkit.getServer().getPluginManager().registerEvents(this, AtlasItemProvider.plugin);

    }

    @EventHandler
    public void onArrowHit(ProjectileHitEvent event) {
        if (event.getEntity().getShooter() == null) return;
        if (!(event.getEntity().getShooter() instanceof Player)) return;
        if (arrowBonusDamage.size() > 1000) {
            arrowBonusDamage.clear();
            return;
        }
        Double bonusDamage = arrowBonusDamage.get(event.getEntity().getUniqueId());

        if (bonusDamage != null && bonusDamage > 0) {
            if (event.getHitEntity() instanceof LivingEntity) {
                LivingEntity le = (LivingEntity) event.getHitEntity();
                le.damage(bonusDamage, (Player) event.getEntity().getShooter());
            }
        }
    }

    @EventHandler
    public void onArrowShot(EntityShootBowEvent event) {
        if (event.getEntity() instanceof Player && event.getBow() != null) {
            Player player = (Player) event.getEntity();
            int level = get(event.getBow());
            if (level > 0) {
                double percent = 0.04 * level;
                double money = AtlasItemProvider.economy.getBalance(player);
                AtlasItemProvider.economy.withdrawPlayer(player, money * percent);
                player.sendMessage(ChatColor.GREEN + "[FINANCIAL LOSS]" + ChatColor.YELLOW + " Arrow shot with "
                        + ChatColor.RED + "%.2f".formatted(money * percent) + ChatColor.YELLOW + " bonus damage!" );
                arrowBonusDamage.put(event.getProjectile().getUniqueId(), money * percent);
            }
        }
    }

    public static FinancialLoss getInstance() {
        return instance;
    }
}