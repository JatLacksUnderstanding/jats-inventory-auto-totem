package jat9119.inventory.auto.totem.inventory;

public class SlotConversion {
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
}
