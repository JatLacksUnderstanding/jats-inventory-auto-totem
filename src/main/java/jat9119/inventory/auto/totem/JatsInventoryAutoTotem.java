package jat9119.inventory.auto.totem;

import com.mojang.blaze3d.platform.InputConstants;
import jat9119.inventory.auto.totem.config.AutoTotemConfig;
import jat9119.inventory.auto.totem.gui.AutoTotemScreen;
import jat9119.inventory.auto.totem.util.InvUtils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Items;
import org.lwjgl.glfw.GLFW;

import java.util.concurrent.ThreadLocalRandom;

public class JatsInventoryAutoTotem implements ModInitializer, Global {

    private KeyMapping toggleBind;
    private KeyMapping openGuiBind;

    public static boolean modEnabled = true;
    public static boolean debugMessagesEnabled = false;
    public static boolean mixedDistributionDelayEnabled = true;

    private int tickDelay;
    public static int minDelayTicks = 1;
    public static int maxDelayTicks = 4;

    public static int hotbarSlotPrimary = 8;
    public static int hotbarSlotSecondary = 0;

    public static boolean openInventory = false;


    private static final KeyMapping.Category INVENTORY_AUTO_TOTEM = KeyMapping.Category.register(Identifier.fromNamespaceAndPath(
            MOD_ID,
            "inventory_auto_totem"
    ));

    @Override
    public void onInitialize() {
        AutoTotemConfig.load();

        toggleBind = KeyMappingHelper.registerKeyMapping(new KeyMapping("key.jats-inventory-auto-totem.toggle_bind", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_F9, INVENTORY_AUTO_TOTEM));
        openGuiBind = KeyMappingHelper.registerKeyMapping(new KeyMapping("key.jats-inventory-auto-totem.open_gui", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_SHIFT, INVENTORY_AUTO_TOTEM));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {

            while (openGuiBind.consumeClick()) {
                client.setScreen(new AutoTotemScreen(client.screen));
            }

            while (toggleBind.consumeClick()) {
                modEnabled = !modEnabled;
                client.player.sendSystemMessage(Component.literal("Auto Totem " + (modEnabled ? "§aEnabled" : "§cDisabled")));
            }


            if (client.player == null || client.player.gameMode() == null || !modEnabled) {
                return;
            }

            if (openInventory) {
                client.setScreen(new InventoryScreen(client.player));
                openInventory = false;
                return;
            }

            if (!(client.screen instanceof InventoryScreen)) {
                return;
            }


            if (tickDelay > 0) {
                tickDelay--;
                return;
            }

            if (restock()) {
                if (mixedDistributionDelayEnabled) {
                    tickDelay = InvUtils.mixedDistributionDelay(minDelayTicks, maxDelayTicks);
                } else {
                    if (maxDelayTicks > 1) {
                        tickDelay = ThreadLocalRandom.current().nextInt(minDelayTicks, maxDelayTicks);
                    } else {
                        tickDelay = 1;
                    }
                }

                if (debugMessagesEnabled) {
                    client.player.sendSystemMessage(Component.literal(
                            "§6§l[Auto Totem] §r"
                                    + (JatsInventoryAutoTotem.mixedDistributionDelayEnabled ? "§bWeighted Distribution" : "§aUniform Distribution")
                                    + "§7. Random Delay: §e" + tickDelay
                                    + "§7 Ticks. Min: §e" + minDelayTicks
                                    + "§7 Max: §e" + maxDelayTicks
                                    + "§7 Slots: §d" + hotbarSlotPrimary
                                    + "§7 & §d" + hotbarSlotSecondary
                    ));
                }
            }
        });
    }

    public static boolean restock() {
        if (InvUtils.inventoryItemCount(Items.TOTEM_OF_UNDYING) <= 0
                && InvUtils.hotbarItemCount(Items.TOTEM_OF_UNDYING) <= 0) {
            return false;
        }

        int primarySlot = InvUtils.toHotbarIndex(hotbarSlotPrimary);
        int secondarySlot = InvUtils.toHotbarIndex(hotbarSlotSecondary);


        if (!(InvUtils.offHandHasItem(Items.TOTEM_OF_UNDYING))) {
            InvUtils.offHandQuickMove(Items.TOTEM_OF_UNDYING);
            return true;
        }
        if (!(InvUtils.hotbarSlotHasItem(Items.TOTEM_OF_UNDYING, primarySlot))) {
            InvUtils.hotbarQuickMove(Items.TOTEM_OF_UNDYING, primarySlot);
            return true;
        }

        if (hotbarSlotSecondary != 0 && !(InvUtils.ignoreQuickmoveItems(secondarySlot))
                && InvUtils.offHandHasItem(Items.TOTEM_OF_UNDYING)
                && InvUtils.hotbarSlotHasItem(Items.TOTEM_OF_UNDYING, primarySlot)
                && !InvUtils.hotbarSlotHasItem(Items.TOTEM_OF_UNDYING, secondarySlot)) {

            InvUtils.hotbarQuickMove(Items.TOTEM_OF_UNDYING, secondarySlot);
            return true;
        }
        return false;
    }
}