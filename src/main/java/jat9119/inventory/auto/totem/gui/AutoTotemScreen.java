package jat9119.inventory.auto.totem.gui;

import jat9119.inventory.auto.totem.JatsInventoryAutoTotem;
import jat9119.inventory.auto.totem.client.Settings;
import jat9119.inventory.auto.totem.config.AutoTotemConfig;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class AutoTotemScreen extends Screen {
    private final Screen parent;
    public AutoTotemScreen(Screen parent) {
        super(Component.literal("AutoTotem Settings"));
        this.parent = parent;
    }
    @Override
    protected void init() {
        int centerX = this.width / 2;
        int centerY = this.height / 2;

        this.addRenderableWidget(Button.builder(Component.literal("Auto Totem: " + (Settings.modEnabled ? "§aEnabled" : "§cDisabled")), button -> {
            Settings.modEnabled = !Settings.modEnabled;
            button.setMessage(Component.literal("Auto Totem " + (Settings.modEnabled ? "§aEnabled" : "§cDisabled")));
        }).bounds(centerX - 75, centerY - 40, 150, 20).build());

        this.addRenderableWidget((Button.builder(Component.literal("Delay Type: " + (Settings.mixedDistributionDelayEnabled ? "Weighted Distribution" : "Uniform Distribution")), button -> {
            Settings.mixedDistributionDelayEnabled = !Settings.mixedDistributionDelayEnabled;
            button.setMessage(Component.literal("Delay Type: " + (Settings.mixedDistributionDelayEnabled ? "Weighted Distribution" : "Uniform Distribution")));
        }).bounds(centerX - 100, centerY - 15, 200, 20).build()));

        this.addRenderableWidget(Button.builder(Component.literal("Debug Messages: " + (Settings.debugMessagesEnabled ? "§aEnabled" : "§cDisabled")), button -> {
            Settings.debugMessagesEnabled = !Settings.debugMessagesEnabled;
            button.setMessage(Component.literal("Debug Messages " + (Settings.debugMessagesEnabled ? "§aEnabled" : "§cDisabled")));
        }).bounds(centerX + 320, centerY + 220, 150, 20).build());


        this.addRenderableWidget(new SliderWidget(centerX - 170,
                centerY + 20,
                160,
                20,
                "Minimum Delay In Ticks",
                Settings.minDelayTicks,
                1,
                20,
                "%.0f ticks",
                value -> {
                    Settings.minDelayTicks = value.intValue();

                    if (Settings.minDelayTicks > Settings.maxDelayTicks) {
                        Settings.maxDelayTicks = Settings.minDelayTicks + 1;
                    }
                }));
        this.addRenderableWidget(new SliderWidget(
                centerX + 10,
                centerY + 20 ,
                160,
                20,
                "Maximum Delay In Ticks",
                Settings.maxDelayTicks,
                1,
                20,
                "%.0f ticks",
                value -> {
                    Settings.maxDelayTicks = value.intValue();
                }
        ));

        this.addRenderableWidget(new SliderWidget(
                centerX - 170,
                centerY + 50,
                160,
                20,
                "Primary Hotbar Slot",
                Settings.hotbarSlotPrimary,
                1,
                9,
                "%.0f",
                value -> Settings.hotbarSlotPrimary = (int) Math.round(value)
        ));
        this.addRenderableWidget(new SliderWidget(
                centerX + 10,
                centerY + 50,
                160,
                20,
                "Secondary Hotbar Slot",
                Settings.hotbarSlotSecondary,
                0,
                9,
                "%.0f",
                value -> Settings.hotbarSlotSecondary = (int) Math.round(value)
        ));
    }

    @Override
    public void onClose() {
        this.minecraft.setScreen(parent);
    }
    @Override
    public boolean shouldCloseOnEsc() {
       this.onClose();
       return true;
    }
    @Override
    public void removed() {
        AutoTotemConfig.save();
    }
    @Override
    public boolean isPauseScreen() {
        return false;
    }

}
