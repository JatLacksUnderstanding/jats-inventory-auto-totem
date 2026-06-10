package jat9119.inventory.auto.totem.gui;

import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.network.chat.Component;

import java.util.function.Consumer;

public class SliderWidget extends AbstractSliderButton {
    private final String label;
    private final double min;
    private final double max;
    private final String format;
    private final Consumer<Double> onChange;

    public SliderWidget(int x, int y, int width, int height, String label, double initial, int min, int max, String format, Consumer<Double> onChange) {
        super(x, y, width, height, Component.empty(), normalize(initial, min, max));
        this.label = label;
        this.min = min;
        this.max = max;
        this.format = format;
        this.onChange = onChange;
        this.updateMessage();
    }

    private static double normalize(double v, double min, double max) {
        return Math.max(0.0, Math.min(1.0, (v - min) / (max - min)));
    }

    public double getRealValue() {
        return this.min + this.value * (this.max - this.min);
    }

    protected void updateMessage() {
        if(this.label.equals("Secondary Hotbar Slot") && value == 0) {
            this.setMessage(Component.literal(this.label + " §cDisabled"));
        } else {
            this.setMessage(Component.literal(this.label + ": " + String.format(this.format, this.getRealValue())));
        }
    }

    protected void applyValue() {
        this.onChange.accept(this.getRealValue());
    }
}
