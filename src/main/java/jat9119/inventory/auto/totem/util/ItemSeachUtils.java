package jat9119.inventory.auto.totem.util;

import jat9119.inventory.auto.totem.Global;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ItemSeachUtils implements Global {

    private static final int[] CENTER_OUT_INDEXES = {
            22, 21, 23,
            13, 31,
            12, 14, 30, 32,
            20, 24,
            11, 15, 29, 33,
            19, 25,
            10, 16, 28, 34,
            18, 26,
            9, 17, 27, 35
    };

    public static int searchClosestCenterItem(final Item item) {
        for (int slot : CENTER_OUT_INDEXES) {
            if (mc.player.getInventory().getItem(slot).is(item)) {
                return slot;
            }
        }
        return -1;
    }

    public static int searchItem(final Item item) {
        for (int slot = 0; slot <= 35; slot++) {
            ItemStack stack = mc.player.getInventory().getItem(slot);
            if (stack.is(item)) {
                return slot;
            }
        }
        return -1;
    }
}
