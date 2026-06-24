package jat9119.inventory.auto.totem;

import jat9119.inventory.auto.totem.client.AutoTotemController;
import net.fabricmc.api.ModInitializer;

public class JatsInventoryAutoTotem implements ModInitializer {

    private final AutoTotemController AutoTotem = new AutoTotemController();

    @Override
    public void onInitialize() {
        AutoTotem.initialize();
    }
}
