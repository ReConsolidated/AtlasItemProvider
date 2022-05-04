package io.github.reconsolidated.atlasitemprovider.CustomItems.CustomEnchants;

import io.github.reconsolidated.atlasitemprovider.AtlasItemProvider;
import io.github.reconsolidated.atlasitemprovider.CustomItems.Rarity;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class Magma extends CustomEnchant implements Listener {
    private static Magma instance = null;

    private final Map<UUID, ItemStack> arrowBows;


    public Magma() {
        super("magma", ChatColor.YELLOW + "" + ChatColor.BOLD + "Magma", Rarity.UNCOMMON);
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("Attempted to create 2nd copy of Magma Enchant (report this to developer)");
        }

        setAcceptsBoots(true);
        arrowBows = new HashMap<>();
        Bukkit.getServer().getPluginManager().registerEvents(this, AtlasItemProvider.plugin);

    }

    @EventHandler
    public void onArrowHit(ProjectileHitEvent event) {
        if (event.getEntity().getShooter() == null) return;
        if (arrowBows.size() > 1000) {
            arrowBows.clear();
            return;
        }
        ItemStack bow = arrowBows.get(event.getEntity().getUniqueId());
        if (bow == null) return;
        int level = get(bow);
        if (level > 0) {
            int chance = 10 * level;
            Random random = new Random();
            if (chance > random.nextInt(100)) {
                apply(event.getHitEntity());

            }
        }
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            ItemStack item = player.getInventory().getItemInMainHand();
            int level = get(item);
            if (level > 0) {
                int chance = 10 * level;
                Random random = new Random();
                if (chance > random.nextInt(100)) {
                    apply(event.getEntity());
                }
            }
        }
    }

    private void apply(Entity entity) {
        if (entity != null) {
            Block block;
            if (entity.getHeight() < 1.5) {
                block = entity.getLocation().getBlock();
            } else {
                block = entity.getLocation().clone().add(0, -1, 0).getBlock();
            }
            if (block.getType() == Material.AIR) {
                block.setType(Material.LAVA);
            }
        }
    }

    @EventHandler
    public void onArrowShot(EntityShootBowEvent event) {
        if (event.getEntity() instanceof Player && event.getBow() != null) {
            arrowBows.put(event.getProjectile().getUniqueId(), event.getBow());
        }
    }

    public static Magma getInstance() {
        return instance;
    }
}
