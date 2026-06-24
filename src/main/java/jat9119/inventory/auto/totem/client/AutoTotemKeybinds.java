package jat9119.inventory.auto.totem.client;

import com.mojang.blaze3d.platform.InputConstants;
import jat9119.inventory.auto.totem.Global;
import jat9119.inventory.auto.totem.gui.AutoTotemScreen;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;

public class AutoTotemKeybinds implements Global {

    private KeyMapping toggleBind;
    private KeyMapping openGuiBind;

    private static final KeyMapping.Category INVENTORY_AUTO_TOTEM = KeyMapping.Category.register(Identifier.fromNamespaceAndPath(
            MOD_ID,
            "inventory_auto_totem"
    ));

    public void register() {


        toggleBind = KeyMappingHelper.registerKeyMapping(new KeyMapping("key.jats-inventory-auto-totem.toggle_bind",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_F9,
                INVENTORY_AUTO_TOTEM));
        openGuiBind = KeyMappingHelper.registerKeyMapping(new KeyMapping("key.jats-inventory-auto-totem.open_gui",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                INVENTORY_AUTO_TOTEM));

    }

    public void handleTick(Minecraft client) {
        while (openGuiBind.consumeClick()) {
            client.setScreenAndShow(new AutoTotemScreen(client.gui.screen()));
        }


        while (toggleBind.consumeClick()) {
            Settings.modEnabled = !Settings.modEnabled;
            client.player.sendSystemMessage(Component.literal("Auto Totem " + (Settings.modEnabled ? "§aEnabled" : "§cDisabled")));
        }
    }

}
