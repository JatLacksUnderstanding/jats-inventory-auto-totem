package jat9119.inventory.auto.totem.inventory;

import jat9119.inventory.auto.totem.Global;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;

public final class OpenInventoryRequest implements Global {

    public static void request() {
        mc.setScreen(new InventoryScreen(mc.player));
        mc.player.sendSystemMessage(Component.literal("e"));
    }
}
