package jat9119.inventory.auto.totem.client;

import jat9119.inventory.auto.totem.Global;
import jat9119.inventory.auto.totem.config.AutoTotemConfig;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;

public class AutoTotemController implements Global {
    private final AutoTotemKeybinds keybinds = new AutoTotemKeybinds();
    private final TotemRestock restocker = new TotemRestock();
    private final RestockDelay delay = new RestockDelay();

    public void initialize() {
        AutoTotemConfig.load();
        keybinds.register();

        ClientTickEvents.END_CLIENT_TICK.register(this::tick);

    }

    private void tick(Minecraft client) {
        keybinds.handleTick(client);

        if (client.player == null || client.player.gameMode() == null) return;
        if (!Settings.modEnabled) return;
        if (!(client.gui.screen() instanceof InventoryScreen)) return;
        if (delay.shouldWait()) return;

        if (restocker.restock()) {
            delay.reset();
            if (Settings.debugMessagesEnabled) {
                DebugMessages.sendDebugMessage();
            }
        }
    }
}
