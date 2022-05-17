package io.github.reconsolidated.atlasitemprovider.CustomItems.CustomEnchants;

import io.github.reconsolidated.atlasitemprovider.AtlasItemProvider;
import io.github.reconsolidated.atlasitemprovider.CustomItems.Rarity;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

import java.util.Collection;
import java.util.Random;

public class SuperMiner extends CustomEnchant implements Listener {
    private static SuperMiner instance = null;
    private final int baseChance = 10;


    public SuperMiner() {
        super("super_miner", "Super Miner", Rarity.UNCOMMON);
        if (instance == null) {
            instance = this;
        } else {
            throw new RuntimeException("Attempted to create 2nd copy of SuperMiner Enchant (report this to developer)");
        }

        setAcceptsAxes(true);
        setAcceptsPickaxes(true);

        Bukkit.getServer().getPluginManager().registerEvents(this, AtlasItemProvider.plugin);

    }


    @EventHandler
    public void onDamage(PlayerInteractEvent event) {
        // TODO implement checking if it's personal world:
        // Super Miner (chance to instantly break any block inside your personal world)

        if (event.getClickedBlock() != null && event.getAction().isLeftClick()) {
            Block block = event.getClickedBlock();
            if (block.getType().equals(Material.BEDROCK)) return;
            Player player = event.getPlayer();
            ItemStack item = event.getItem();
            int level = get(item);
            if (level > 0) {
                int chance = baseChance * level;
                Random random = new Random();
                if (chance > random.nextInt(100)) {
                    instantBreak(player, item, block);
                    if (item != null && item.getItemMeta() instanceof Damageable) {
                        Damageable meta = (Damageable) item.getItemMeta();
                        if (!meta.isUnbreakable()) {
                            meta.setDamage(meta.getDamage()+1);
                        }
                        item.setItemMeta(meta);
                    }
                }
            }
        }

    }

    private void instantBreak(Player player, ItemStack item, Block block) {
        Collection<ItemStack> drops = block.getDrops(item, player);
        for (ItemStack drop : drops) {
            block.getWorld().dropItemNaturally(block.getLocation().toCenterLocation(), drop);
        }
        block.setType(Material.AIR);
    }


    public static SuperMiner getInstance() {
        return instance;
    }
}