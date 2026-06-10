package jat9119.inventory.auto.totem.gui;

import jat9119.inventory.auto.totem.JatsInventoryAutoTotem;
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

        this.addRenderableWidget(Button.builder(Component.literal("Auto Totem " + (JatsInventoryAutoTotem.modEnabled ? "§aEnabled" : "§cDisabled")) , button -> {
            JatsInventoryAutoTotem.modEnabled = !JatsInventoryAutoTotem.modEnabled;
            button.setMessage(Component.literal("Auto Totem " + (JatsInventoryAutoTotem.modEnabled ? "§aEnabled" : "§cDisabled")));
        }).bounds(centerX - 75, centerY - 15, 150, 20).build());

        this.addRenderableWidget(new SliderWidget(
                centerX - 170,
                centerY + 20,
                160,
                20,
                "Minimum Delay In Ticks",
                JatsInventoryAutoTotem.minDelayTicks,
                1,
                20,
                "%.0f ticks",
                value -> {
                    JatsInventoryAutoTotem.minDelayTicks = value.intValue();

                    if (JatsInventoryAutoTotem.minDelayTicks > JatsInventoryAutoTotem.maxDelayTicks) {
                        JatsInventoryAutoTotem.maxDelayTicks = JatsInventoryAutoTotem.minDelayTicks + 1;
                    }
                }
        ));
        this.addRenderableWidget(new SliderWidget(
                centerX + 10,
                centerY + 20 ,
                160,
                20,
                "Maximum Delay In Ticks",
                JatsInventoryAutoTotem.maxDelayTicks,
                1,
                20,
                "%.0f ticks",
                value -> {
                    JatsInventoryAutoTotem.maxDelayTicks = value.intValue();
                }
        ));

        this.addRenderableWidget(new SliderWidget(
                centerX - 170,
                centerY + 50,
                160,
                20,
                "Primary Hotbar Slot",
                JatsInventoryAutoTotem.hotbarSlotPrimary,
                1,
                9,
                "%.0f",
                value -> JatsInventoryAutoTotem.hotbarSlotPrimary = (int) Math.round(value)
        ));
        this.addRenderableWidget(new SliderWidget(
                centerX + 10,
                centerY + 50,
                160,
                20,
                "Secondary Hotbar Slot",
                JatsInventoryAutoTotem.hotbarSlotSecondary,
                0,
                9,
                "%.0f",
                value -> JatsInventoryAutoTotem.hotbarSlotSecondary = (int) Math.round(value)
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
