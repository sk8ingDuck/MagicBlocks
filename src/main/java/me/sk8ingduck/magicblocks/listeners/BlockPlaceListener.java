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
import java.util.concurrent.ThreadLocalRandom;


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

        //let entityid be a random number, not so elegant but needed
        entityId = ThreadLocalRandom.current().nextInt(1, 99999);


        new BukkitRunnable() {
            int state = 0;
            int secondsElapsed = 0;
            final int secondsToRun = config.getDestroyDelay();
            @Override
            public void run() {
                // Aggiorna lo stato del blocco
                blockBreak.getBlockPositionModifier().write(0, blockPosition);
                blockBreak.getIntegers().write(0, entityId).write(1, state);

                // Invia il pacchetto ai giocatori online
                Bukkit.getOnlinePlayers().forEach(player -> {
                    try {
                        ProtocolLibrary.getProtocolManager().sendServerPacket(player, blockBreak);
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                });

                // Controlla se il tempo è scaduto
                if (++secondsElapsed >= secondsToRun) {
                    block.setType(Material.AIR);
                    this.cancel();
                    return;
                }

                // Aggiorna lo stato proporzionale ai secondi passati
                double progress = (double)secondsElapsed / secondsToRun;
                state = (int) Math.floor(progress * 10);

            }
        }.runTaskTimer(MagicBlocks.getInstance(), 0, 20L);
    }
}
