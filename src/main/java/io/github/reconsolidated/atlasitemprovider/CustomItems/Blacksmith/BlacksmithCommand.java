package io.github.reconsolidated.atlasitemprovider.CustomItems.Blacksmith;

import io.github.reconsolidated.atlasitemprovider.AtlasItemProvider;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BlacksmithCommand implements CommandExecutor {

    public BlacksmithCommand() {
        AtlasItemProvider.plugin.getCommand("blacksmith").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        new CustomAnvil(player);

        return true;
    }
}
