package me.sk8ingduck.magicblocks;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import me.sk8ingduck.magicblocks.config.BlocksConfig;
import me.sk8ingduck.magicblocks.listeners.*;
import org.bukkit.plugin.java.JavaPlugin;


public final class MagicBlocks extends JavaPlugin {

    private static MagicBlocks instance;
    private BlocksConfig blocksConfig;
    private ProtocolManager protocolManager;

    @Override
    public void onEnable() {
        instance = this;

        blocksConfig = new BlocksConfig("config.yml", getDataFolder());
        protocolManager = ProtocolLibrary.getProtocolManager();

        getServer().getPluginManager().registerEvents(new BlockPlaceListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryClickListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerDropItemListener(), this);
    }

    public BlocksConfig getBlocksConfig() {
        return blocksConfig;
    }

    public ProtocolManager getProtocolManager() {
        return protocolManager;
    }

    public static MagicBlocks getInstance() {
        return instance;
    }
}
