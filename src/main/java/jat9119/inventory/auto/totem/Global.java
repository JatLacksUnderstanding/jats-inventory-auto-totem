package jat9119.inventory.auto.totem;

import net.minecraft.client.Minecraft;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface Global {
    Minecraft mc = Minecraft.getInstance();

    String MOD_ID = "jats-inventory-auto-totem";
    Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
}
