package me.sk8ingduck.magicblocks.listeners;

import me.sk8ingduck.magicblocks.MagicBlocks;
import me.sk8ingduck.magicblocks.config.BlocksConfig;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerDropItemListener implements Listener {

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        BlocksConfig config = MagicBlocks.getInstance().getBlocksConfig();
        if (!config.canDropBlocks() && event.getItemDrop().getItemStack().getType().equals(config.getBlock().getType())) {
            event.setCancelled(true);
        }
    }
}
