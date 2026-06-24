package jat9119.inventory.auto.totem.client;

import jat9119.inventory.auto.totem.Global;
import jat9119.inventory.auto.totem.inventory.InventoryActions;
import jat9119.inventory.auto.totem.inventory.SlotConversion;
import jat9119.inventory.auto.totem.util.InvUtils;
import net.minecraft.world.item.Items;

public class TotemRestock implements Global {

    public static boolean restock() {
        if (InvUtils.inventoryItemCount(Items.TOTEM_OF_UNDYING) <= 0
                && InvUtils.hotbarItemCount(Items.TOTEM_OF_UNDYING) <= 0) {
            return false;
        }

        int primarySlot = SlotConversion.toHotbarIndex(Settings.hotbarSlotPrimary);
        int secondarySlot = SlotConversion.toHotbarIndex(Settings.hotbarSlotSecondary);


        if (!(InvUtils.offHandHasItem(Items.TOTEM_OF_UNDYING))) {
            InventoryActions.offHandQuickMove(Items.TOTEM_OF_UNDYING);
            return true;
        }
        if (!(InvUtils.hotbarSlotHasItem(Items.TOTEM_OF_UNDYING, primarySlot))) {
            InvUtils.hotbarQuickMove(Items.TOTEM_OF_UNDYING, primarySlot);
            return true;
        }

        if (Settings.hotbarSlotSecondary != 0 && !(InvUtils.ignoreQuickmoveItems(secondarySlot))
                && InvUtils.offHandHasItem(Items.TOTEM_OF_UNDYING)
                && InvUtils.hotbarSlotHasItem(Items.TOTEM_OF_UNDYING, primarySlot)
                && !InvUtils.hotbarSlotHasItem(Items.TOTEM_OF_UNDYING, secondarySlot)) {

            InvUtils.hotbarQuickMove(Items.TOTEM_OF_UNDYING, secondarySlot);
            return true;
        }
        return false;
    }
}
