package jat9119.inventory.auto.totem.util;

import jat9119.inventory.auto.totem.Global;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.Random;

public class InvUtils implements Global {
    private static final Random random = new Random();

    public static boolean hotbarSlotHasItem(final Item item, final int targetSlot) {
        ItemStack stack = mc.player.getInventory().getItem(targetSlot);
        return stack.is(item);
    }

    public static boolean offHandHasItem(final Item item) {
        ItemStack stack = mc.player.getOffhandItem();
        return stack.is(item);
    }

    public static boolean offHandQuickMove(final Item item) {
        int slot = ItemSeachUtils.searchClosestCenterItem(item);
        if (slot == -1) {
            slot = ItemSeachUtils.searchItem(item);
        }
        if (slot == -1) {
            return false;
        }
        quickMoveItem(toInventoryIndex(slot), 40);
        return true;
    }

    public static boolean hotbarQuickMove(final Item item, final int targetSlot) {
        int slot = ItemSeachUtils.searchClosestCenterItem(item);
        if (slot == -1) {
            return false;
        }
        quickMoveItem(slot, targetSlot);
        return true;
    }

    public static boolean quickMoveItem(final int sourceSlot, final int targetSlot) {
        mc.gameMode.handleContainerInput(
                mc.player.containerMenu.containerId,
                sourceSlot,
                targetSlot,
                ContainerInput.SWAP,
                mc.player);
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

    public static int toHotbarIndex(final int setSlot) {
        if (setSlot > 0) {
            return setSlot - 1;
        }
        return setSlot;
    }

    public static int toInventoryIndex(final int slot) {
        if (slot >= 0 && slot <= 8) {
            return slot + 36;
        }
        return slot;
    }

    public static int mixedDistributionDelay(Integer min, Integer max) {
        int lo = min, hi = max;
        if (hi <= lo) return lo;

        double roll = random.nextDouble();

        if (roll < 0.15) {
            return lo;
        } else if (roll < 0.25) {
            return hi + 1 + random.nextInt(Math.max(1, (hi - lo) * 2));
        } else {
            double u1 = 1.0 - random.nextDouble();
            double u2 = 1.0 - random.nextDouble();
            double gaussian = Math.sqrt(-2.0 * Math.log(u1)) * Math.cos(2.0 * Math.PI * u2);
            double mid = (lo + hi) / 2.0;
            double sigma = (hi - lo) / 3.0;
            int result = (int) Math.round(mid + gaussian * sigma);
            return Math.max(lo, Math.min(hi, result));
        }
    }

}