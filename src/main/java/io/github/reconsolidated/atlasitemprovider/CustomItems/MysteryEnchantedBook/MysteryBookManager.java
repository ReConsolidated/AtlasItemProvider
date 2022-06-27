package io.github.reconsolidated.atlasitemprovider.CustomItems.MysteryEnchantedBook;

import io.github.reconsolidated.atlasitemprovider.AtlasItemProvider;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class MysteryBookManager implements Listener {

    private final AtlasItemProvider plugin;
    public static NamespacedKey bookTypeKey;
    public static NamespacedKey bookLuckKey;


    public MysteryBookManager(AtlasItemProvider plugin) {
        this.plugin = plugin;
        bookTypeKey = new NamespacedKey(plugin, "mystery_book_type");
        bookLuckKey = new NamespacedKey(plugin, "mystery_book_luck");

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (!event.getAction().isRightClick()) return;

        if (event.getItem() == null) return;

        ItemStack book = event.getItem();
        if (isMysteryBook(book)) {
            MysteryBook mysteryBook = getMysteryBook(book);
            mysteryBook.open(event.getPlayer());
        }
    }

    private MysteryBook getMysteryBook(ItemStack book) {
        return new MysteryBook(book);
    }

    private boolean isMysteryBook(ItemStack book) {
        return book.getItemMeta() != null &&
                book.getItemMeta().getPersistentDataContainer().get(bookTypeKey, PersistentDataType.STRING) != null;
    }
}
