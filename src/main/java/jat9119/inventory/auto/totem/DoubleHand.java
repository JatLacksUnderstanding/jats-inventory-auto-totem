package jat9119.inventory.auto.totem;

import jat9119.inventory.auto.totem.util.InventoryUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.Items;

public class DoubleHand {
    private final static Minecraft mc = Minecraft.getInstance();

    public static void tick() {
        if (mc.player == null || mc.level == null || mc.gameMode == null) {
            return;
        }

        LocalPlayer localPlayer = mc.player;

        if (InventoryUtils.hotbarSlotHasItem(Items.TOTEM_OF_UNDYING, 8) || InventoryUtils.hotbarSlotHasItem(Items.TOTEM_OF_UNDYING, 4)) {
            return;
        }

    }


}
