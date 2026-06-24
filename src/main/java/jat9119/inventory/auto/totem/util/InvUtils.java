package jat9119.inventory.auto.totem.util;

import jat9119.inventory.auto.totem.Global;
import jat9119.inventory.auto.totem.inventory.InventoryActions;
import jat9119.inventory.auto.totem.inventory.InventorySearch;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class InvUtils implements Global {


    public static boolean hotbarSlotHasItem(final Item item, final int targetSlot) {
        ItemStack stack = mc.player.getInventory().getItem(targetSlot);
        return stack.is(item);
    }

    public static boolean offHandHasItem(final Item item) {
        ItemStack stack = mc.player.getOffhandItem();
        return stack.is(item);
    }



    public static boolean hotbarQuickMove(final Item item, final int targetSlot) {
        int slot = InventorySearch.searchClosestCenterItem(item);
        if (slot == -1) {
            return false;
        }
        InventoryActions.quickMoveItem(slot, targetSlot);
        return true;
    }


    public static int inventoryItemCount(final Item item) {
        int itemCount = 0;
        for (int slot = 9; slot <= 35; slot++) {
            ItemStack stack = mc.player.getInventory().getItem(slot);
            if (stack.is(item)) {
                itemCount++;
            }
        }
        return itemCount;
    }

    public static int hotbarItemCount(final Item item) {
        int itemCount = 0;
        for (int slot = 0; slot <= 8; slot++) {
            ItemStack stack = mc.player.getInventory().getItem(slot);
            if (stack.is(item)) {
                itemCount++;
            }
        }
        return itemCount;
    }

    public static boolean ignoreQuickmoveItems(final int slot) {
        ItemStack stack = mc.player.getInventory().getItem(slot);

        if (stack.isEmpty()) {
            return false;
        }
        return stack.is(Items.EXPERIENCE_BOTTLE)
                || stack.is(ItemTags.PICKAXES)
                || stack.is(ItemTags.AXES)
                || stack.is(Items.MACE)
                || stack.is(Items.SHIELD)
                || stack.is(Items.CROSSBOW)
                || stack.is(Items.BOW);
    }

}