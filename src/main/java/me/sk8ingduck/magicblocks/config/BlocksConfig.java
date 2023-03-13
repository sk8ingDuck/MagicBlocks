package me.sk8ingduck.magicblocks.config;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.io.File;

public class BlocksConfig extends Config {

    private final boolean permissionEnabled;
    private final boolean giveBlockOnJoin;
    private final boolean unlimitedBlocks;
    private final boolean canDropBlocks;
    private final boolean canMoveBlocksInInventory;
    private final int destroyDelay;
    private final int blockSlot;
    private final ItemStack block;
    public BlocksConfig(String name, File path) {
        super(name, path);

        this.permissionEnabled = (Boolean) getPathOrSet("permissionEnabled", false);
        this.giveBlockOnJoin = (Boolean) getPathOrSet("giveBlocksOnJoin", true);
        this.unlimitedBlocks = (Boolean) getPathOrSet("unlimitedBlocks", true);
        this.canDropBlocks = (Boolean) getPathOrSet("canDropBlocks", true);
        this.canMoveBlocksInInventory = (Boolean) getPathOrSet("canMoveBlocksInInventory", true);
        this.destroyDelay = (int) getPathOrSet("destroyDelay", 5);
        this.blockSlot = (int) getPathOrSet("blockSlot", 4);
        this.block = (ItemStack) getPathOrSet("block", new ItemStack(Material.SANDSTONE, 64));
    }

    public boolean isPermissionEnabled() {
        return permissionEnabled;
    }

    public boolean isGiveBlockOnJoin() {
        return giveBlockOnJoin;
    }

    public boolean isUnlimitedBlocks() {
        return unlimitedBlocks;
    }

    public boolean canDropBlocks() {
        return canDropBlocks;
    }

    public boolean canMoveBlocksInInventory() {
        return canMoveBlocksInInventory;
    }

    public int getDestroyDelay() {
        return destroyDelay;
    }

    public int getBlockSlot() {
        return blockSlot;
    }

    public ItemStack getBlock() {
        return block;
    }

}
