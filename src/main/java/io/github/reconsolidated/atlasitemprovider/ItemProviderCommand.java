package io.github.reconsolidated.atlasitemprovider;

import io.github.reconsolidated.atlasitemprovider.CustomItems.Anvil.EnchantmentsAnvil;
import io.github.reconsolidated.atlasitemprovider.CustomItems.CustomEnchants.CustomEnchant;
import io.github.reconsolidated.atlasitemprovider.CustomItems.LoreProvider;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Random;
import java.util.UUID;

public class ItemProviderCommand implements CommandExecutor {
    private final AtlasItemProvider itemProvider;

    public ItemProviderCommand(AtlasItemProvider itemProvider) {
        this.itemProvider = itemProvider;
        itemProvider.getCommand("itemprovider").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            onHelp(sender);
            return true;
        }
        if (args[0].equalsIgnoreCase("enchant")) {
            if (!(sender instanceof Player)) return true;
            if (args.length == 1) {
                sender.sendMessage(ChatColor.RED + "Correct usage: ");
                sender.sendMessage(ChatColor.AQUA + "/itemprovider enchant <enchant_name> [level=1]");
            } else {
                Player player = (Player) sender;
                ItemStack item = player.getInventory().getItemInMainHand();

                int value = 1;
                if (args.length > 2) {
                    try {
                        value = Integer.parseInt(args[2]);
                    } catch (NumberFormatException exception) {
                        sender.sendMessage(ChatColor.RED + "Please input a valid number");
                    }
                }

                CustomEnchant enchant = CustomEnchant.getEnchant(args[1]);
                if (enchant == null) {
                    sender.sendMessage(ChatColor.RED + "No such enchant.");
                } else {
                    enchant.set(item, value);
                    ItemMeta meta = item.getItemMeta();
                    meta.lore(LoreProvider.getLore(item));
                    item.setItemMeta(meta);
                    sender.sendMessage(ChatColor.GREEN + "Successfully applied enchant " + enchant.getDisplayName() + " " + value + ".");
                }
            }
        }

        if (args[0].equalsIgnoreCase("add")) {
            if (args.length < 2) {
                sender.sendMessage(ChatColor.RED + "Correct usage: ");
                sender.sendMessage(ChatColor.AQUA + "/itemprovider add <category> [name]  - adds item from hand");
            } else {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    if (player.getInventory().getItemInMainHand() == null
                            || player.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
                        sender.sendMessage(ChatColor.RED + "You have to hold the item in hand!");
                    } else {
                        String name = UUID.randomUUID().toString();
                        if (args.length >= 3) {
                            name = args[2];
                        }
                        String category = args[1];
                        itemProvider.addItem(player.getInventory().getItemInMainHand(), name, category);
                        sender.sendMessage(ChatColor.GREEN + "Item added!");
                    }
                }
            }
        }
        if (args[0].equalsIgnoreCase("get")) {
            if (args.length < 3) {
                sender.sendMessage(ChatColor.RED + "Correct usage: ");
                sender.sendMessage(ChatColor.AQUA + "/itemprovider get <category> <name>  - gives you item");
            } else {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    String name = args[2];
                    String category = args[1];
                    player.getInventory().addItem(itemProvider.getItem(category, name));
                    sender.sendMessage(ChatColor.GREEN + "Item given!");
                }
            }
        }
        if (args[0].equalsIgnoreCase("getbook")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (args.length == 1) {
                    Random random = new Random();
                    player.getInventory().addItem(EnchantmentsAnvil.getEnchantedBook(random.nextInt(94) + 5));
                    sender.sendMessage(ChatColor.GREEN + "Received empty enchanted book with random chance.");
                }
                else {
                    try {
                        int chance = Integer.parseInt(args[1]);
                        player.getInventory().addItem(EnchantmentsAnvil.getEnchantedBook(chance));
                        sender.sendMessage(ChatColor.GREEN + "Received empty enchanted book.");
                    } catch (NumberFormatException exception) {
                        sender.sendMessage(ChatColor.RED + "Input valid number!");
                    }
                }
            } else {
                sender.sendMessage(ChatColor.RED + "Only players can use this command!");
            }

        }
        return true;
    }

    private void onHelp(CommandSender sender) {
        sender.sendMessage(ChatColor.AQUA + "ItemProvider help: ");
        sender.sendMessage(ChatColor.AQUA + "/itemprovider add <category> [name] - adds item from hand");
        sender.sendMessage(ChatColor.AQUA + "/itemprovider get <category> <name> - gives you item");
        sender.sendMessage(ChatColor.AQUA + "/itemprovider getbook [chance] - gives you empty enchanted book");
    }
}
