package jat9119.inventory.auto.totem.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jat9119.inventory.auto.totem.JatsInventoryAutoTotem;
import net.fabricmc.loader.api.FabricLoader;

import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

public class AutoTotemConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("jats-inventory-auto-totem.json");

    public boolean modEnabled = true;
    public boolean debugMessagesEnabled = false;
    public boolean mixedDistributionDelayEnabled = true;
    public int minDelayTicks = 1;
    public int maxDelayTicks = 4;
    public int hotbarSlotPrimary = 9;
    public int hotbarSlotSecondary = 0;

    public static void load() {
        if (!Files.exists(CONFIG_PATH)) {
            save();
            return;
        }

        try (Reader reader = Files.newBufferedReader(CONFIG_PATH)) {
            AutoTotemConfig config = GSON.fromJson(reader, AutoTotemConfig.class);

            JatsInventoryAutoTotem.modEnabled = config.modEnabled;
            JatsInventoryAutoTotem.debugMessagesEnabled = config.debugMessagesEnabled;
            JatsInventoryAutoTotem.mixedDistributionDelayEnabled = config.mixedDistributionDelayEnabled;
            JatsInventoryAutoTotem.minDelayTicks = config.minDelayTicks;
            JatsInventoryAutoTotem.maxDelayTicks = config.maxDelayTicks;
            JatsInventoryAutoTotem.hotbarSlotPrimary = config.hotbarSlotPrimary;
            JatsInventoryAutoTotem.hotbarSlotSecondary = config.hotbarSlotSecondary;
        } catch (Exception ignored) {
        }
    }

    public static void save() {
        try {
            AutoTotemConfig config = new AutoTotemConfig();

            config.modEnabled = JatsInventoryAutoTotem.modEnabled;
            config.debugMessagesEnabled = JatsInventoryAutoTotem.debugMessagesEnabled;
            config.mixedDistributionDelayEnabled = JatsInventoryAutoTotem.mixedDistributionDelayEnabled;
            config.minDelayTicks = JatsInventoryAutoTotem.minDelayTicks;
            config.maxDelayTicks = JatsInventoryAutoTotem.maxDelayTicks;
            config.hotbarSlotPrimary = JatsInventoryAutoTotem.hotbarSlotPrimary;
            config.hotbarSlotSecondary = JatsInventoryAutoTotem.hotbarSlotSecondary;

            Files.createDirectories(CONFIG_PATH.getParent());

            try (Writer writer = Files.newBufferedWriter(CONFIG_PATH)) {
                GSON.toJson(config, writer);
            }
        } catch (Exception ignored) {
        }
    }
}
