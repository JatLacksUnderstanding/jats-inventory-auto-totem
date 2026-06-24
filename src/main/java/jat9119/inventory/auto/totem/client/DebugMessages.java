package jat9119.inventory.auto.totem.client;

import jat9119.inventory.auto.totem.Global;
import net.minecraft.network.chat.Component;

public class DebugMessages implements Global {

    public static void sendDebugMessage() {
        if (!Settings.debugMessagesEnabled || mc.player == null) {
            return;
        }

        mc.player.sendSystemMessage(Component.literal(
                "§6§l[Auto Totem] §r"
                        + (Settings.mixedDistributionDelayEnabled ? "§bWeighted Distribution" : "§aUniform Distribution")
                        + "§7. Random Delay: §e" + RestockDelay.tickDelay
                        + "§7 Ticks. Min: §e" + Settings.minDelayTicks
                        + "§7 Max: §e" + Settings.maxDelayTicks
                        + "§7 Slots: §d" + Settings.hotbarSlotPrimary
                        + "§7 & §d" + Settings.hotbarSlotSecondary
        ));
    }
}
