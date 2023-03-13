package me.sk8ingduck.magicblocks.listeners;

import me.sk8ingduck.magicblocks.MagicBlocks;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (MagicBlocks.getInstance().getBlocksConfig().canMoveBlocksInInventory()
				&& event.getSlot() == MagicBlocks.getInstance().getBlocksConfig().getBlockSlot()
				&& !event.getWhoClicked().getGameMode().equals(GameMode.CREATIVE)) {
			event.setCancelled(true);
		}
	}
}
