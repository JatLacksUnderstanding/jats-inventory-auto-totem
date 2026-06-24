package jat9119.inventory.auto.totem.mixin;

import com.mojang.blaze3d.platform.InputConstants;
import jat9119.inventory.auto.totem.Global;
import jat9119.inventory.auto.totem.client.Settings;
import jat9119.inventory.auto.totem.inventory.OpenInventoryRequest;
import jat9119.inventory.auto.totem.inventory.SlotConversion;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.input.KeyEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardHandler.class)
public class KeyBoardHandlerMixin implements Global {
    @Inject(at = @At("HEAD"), method = "keyPress", cancellable = true)
    private void KeyPress(long handle, int action, KeyEvent keyEvent, CallbackInfo ci) {
        if (action != InputConstants.PRESS) {
            return;
        }

        if (mc.player != null && mc.gui.screen() == null && Settings.modEnabled) {
            int selectedHotbarSlot = mc.player.getInventory().getSelectedSlot();
            if (mc.options.keyInventory.matches(keyEvent)) {
                if (selectedHotbarSlot != SlotConversion.toHotbarIndex(Settings.hotbarSlotPrimary)) {
                    ci.cancel();
                    mc.player.getInventory().setSelectedSlot(SlotConversion.toHotbarIndex(Settings.hotbarSlotPrimary));
                    OpenInventoryRequest.request();
                }
            }
        }
    }
}
