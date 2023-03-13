package me.sk8ingduck.magicblocks.listeners;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;
import me.sk8ingduck.magicblocks.MagicBlocks;
import me.sk8ingduck.magicblocks.config.BlocksConfig;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.InvocationTargetException;


public class BlockPlaceListener implements Listener {

    private int entityId = 10000;

    @EventHandler
    public void placeBlockEvent(BlockPlaceEvent event) {
        BlocksConfig config = MagicBlocks.getInstance().getBlocksConfig();
        Block block = event.getBlockPlaced();
        Player player = event.getPlayer();
        if (!block.getType().equals(config.getBlock().getType())) {
            return;
        }

        if (config.isPermissionEnabled() && !player.hasPermission("magicblocks.build")) {
            return;
        }

        if (event.getBlockReplacedState().getType() != Material.AIR
        && player.getGameMode() != GameMode.CREATIVE) {
            return;
        }

        if (config.isUnlimitedBlocks()) {
            player.getInventory().setItemInHand(config.getBlock());
        }

        ProtocolManager protocolLibrary = MagicBlocks.getInstance().getProtocolManager();
        PacketContainer blockBreak = protocolLibrary.createPacket(PacketType.Play.Server.BLOCK_BREAK_ANIMATION);
        BlockPosition blockPosition = new BlockPosition(block.getX(), block.getY(), block.getZ());

        if (entityId > 100000)
            entityId = 10000;


        new BukkitRunnable() {
            int state = 0;

            @Override
            public void run() {
                blockBreak.getBlockPositionModifier().write(0, blockPosition);
                blockBreak.getIntegers().write(0, entityId).write(1, state);

                Bukkit.getOnlinePlayers().forEach(player -> {
                    try {
                        ProtocolLibrary.getProtocolManager().sendServerPacket(player, blockBreak);
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                });

                if (state == 10) {
                    block.setType(Material.AIR);
                    this.cancel();
                }

                state++;
            }
        }.runTaskTimer(MagicBlocks.getInstance(), 0, config.getDestroyDelay());
    }
}
