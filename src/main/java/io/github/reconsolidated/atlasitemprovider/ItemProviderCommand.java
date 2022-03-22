package io.github.reconsolidated.atlasitemprovider;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

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
        return true;
    }

    private void onHelp(CommandSender sender) {
        sender.sendMessage(ChatColor.AQUA + "ItemProvider help: ");
        sender.sendMessage(ChatColor.AQUA + "/itemprovider add <category> [name] - adds item from hand");
    }
}
