package jat9119.inventory.auto.totem.inventory;

import jat9119.inventory.auto.totem.Global;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.item.Item;

public class InventoryActions implements Global {
    public static boolean quickMoveItem(final int sourceSlot, final int targetSlot) {
        mc.gameMode.handleContainerInput(
                mc.player.containerMenu.containerId,
                sourceSlot,
                targetSlot,
                ContainerInput.SWAP,
                mc.player);
        return true;
    }
    public static boolean offHandQuickMove(final Item item) {
        int slot = InventorySearch.searchClosestCenterItem(item);
        if (slot == -1) {
            slot = InventorySearch.searchItem(item);
        }
        if (slot == -1) {
            return false;
        }
        quickMoveItem(SlotConversion.toInventoryIndex(slot), 40);
        return true;
    }
}
