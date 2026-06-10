package jat9119.inventory.auto.totem;

import com.mojang.blaze3d.platform.InputConstants;
import jat9119.inventory.auto.totem.gui.AutoTotemConfig;
import jat9119.inventory.auto.totem.gui.AutoTotemScreen;
import jat9119.inventory.auto.totem.util.InventoryUtils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Items;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JatsInventoryAutoTotem implements ModInitializer {
	public static final String MOD_ID = "jats-inventory-auto-totem";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	private KeyMapping toggleBind;
	private KeyMapping openGuiBind;


	public static boolean modEnabled = true;
	private int tickDelay;

	public static int minDelayTicks = 1;
	public static int maxDelayTicks = 4;

	public static int hotbarSlotPrimary = 8;
	public static int hotbarSlotSecondary = 0;


	private static final KeyMapping.Category INVENTORY_AUTO_TOTEM = KeyMapping.Category.register(Identifier.fromNamespaceAndPath(
			MOD_ID,
			"inventory_auto_totem"
	));

	@Override
	public void onInitialize() {
		AutoTotemConfig.load();

		toggleBind = KeyMappingHelper.registerKeyMapping(new KeyMapping(
				"key.jats-inventory-auto-totem.toggle_bind",
				InputConstants.Type.KEYSYM,
				GLFW.GLFW_KEY_F9,
				INVENTORY_AUTO_TOTEM
		));
		openGuiBind = KeyMappingHelper.registerKeyMapping(new KeyMapping(
				"key.jats-inventory-auto-totem.open_gui",
				InputConstants.Type.KEYSYM,
				GLFW.GLFW_KEY_RIGHT_SHIFT,
				INVENTORY_AUTO_TOTEM
		));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {

			while (toggleBind.consumeClick()) {
				modEnabled = !modEnabled;
				client.player.sendSystemMessage(Component.literal("Auto Totem " + (modEnabled ? "§aEnabled" : "§cDisabled")));
			}

			while (openGuiBind.consumeClick()) {
				client.setScreen(new AutoTotemScreen(client.screen));
			}


			if (client.player == null || client.player.gameMode() == null) {
				return;
			}
			if (!(client.screen instanceof InventoryScreen)) {
				return;
			}
			if (!modEnabled) {
				return;
			}
			client.player.getInventory().setSelectedSlot(InventoryUtils.toHotbarIdex(hotbarSlotPrimary));

			if (tickDelay > 0) {
				tickDelay--;
				return;
			}
			if (restock()) {
				tickDelay = InventoryUtils.randomDelay(minDelayTicks, maxDelayTicks);
			}
		});
	}

	public static boolean restock() {
		if (InventoryUtils.inventoryItemCount(Items.TOTEM_OF_UNDYING) <= 0) {
			return false;
		}

		int primarySlot = InventoryUtils.toHotbarIdex(hotbarSlotPrimary);
		int secondarySlot = InventoryUtils.toHotbarIdex(hotbarSlotSecondary);


		if (!(InventoryUtils.offHandHasItem(Items.TOTEM_OF_UNDYING))) {
			InventoryUtils.offHandQuickMove(Items.TOTEM_OF_UNDYING);
			return true;
		}
		if (!(InventoryUtils.hotbarSlotHasItem(Items.TOTEM_OF_UNDYING, primarySlot))) {
			InventoryUtils.hotbarQuickMove(Items.TOTEM_OF_UNDYING, primarySlot);
			return true;
		}

		if (hotbarSlotSecondary != 0 && !(InventoryUtils.ignoreQuickmoveItems(secondarySlot))
				&& InventoryUtils.offHandHasItem(Items.TOTEM_OF_UNDYING)
				&& InventoryUtils.hotbarSlotHasItem(Items.TOTEM_OF_UNDYING, primarySlot)
				&& !InventoryUtils.hotbarSlotHasItem(Items.TOTEM_OF_UNDYING, secondarySlot)) {

			InventoryUtils.hotbarQuickMove(Items.TOTEM_OF_UNDYING, secondarySlot);
			return true;
		}
        return false;
    }
}