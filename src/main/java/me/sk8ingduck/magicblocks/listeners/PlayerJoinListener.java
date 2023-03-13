package me.sk8ingduck.magicblocks.listeners;

import me.sk8ingduck.magicblocks.MagicBlocks;
import me.sk8ingduck.magicblocks.config.BlocksConfig;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        BlocksConfig config = MagicBlocks.getInstance().getBlocksConfig();

        if (config.isGiveBlockOnJoin()) {
            if (config.isPermissionEnabled()) {
                if (player.hasPermission("magicblocks.join")) {
                    player.getInventory().setItem(config.getBlockSlot(), config.getBlock());
                }
            } else {
                player.getInventory().setItem(config.getBlockSlot(), config.getBlock());
            }
        }
    }
}
