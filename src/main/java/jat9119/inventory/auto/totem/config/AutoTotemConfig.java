package jat9119.inventory.auto.totem.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jat9119.inventory.auto.totem.Global;
import jat9119.inventory.auto.totem.JatsInventoryAutoTotem;
import jat9119.inventory.auto.totem.client.Settings;
import net.fabricmc.loader.api.FabricLoader;

import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

public class AutoTotemConfig implements Global {
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

            Settings.modEnabled = config.modEnabled;
            Settings.debugMessagesEnabled = config.debugMessagesEnabled;
            Settings.mixedDistributionDelayEnabled = config.mixedDistributionDelayEnabled;
            Settings.minDelayTicks = config.minDelayTicks;
            Settings.maxDelayTicks = config.maxDelayTicks;
            Settings.hotbarSlotPrimary = config.hotbarSlotPrimary;
            Settings.hotbarSlotSecondary = config.hotbarSlotSecondary;
        } catch (Exception exception) {
            LOGGER.warn(String.valueOf(exception));
        }
    }

    public static void save() {
        try {
            AutoTotemConfig config = new AutoTotemConfig();

            config.modEnabled = Settings.modEnabled;
            config.debugMessagesEnabled = Settings.debugMessagesEnabled;
            config.mixedDistributionDelayEnabled = Settings.mixedDistributionDelayEnabled;
            config.minDelayTicks = Settings.minDelayTicks;
            config.maxDelayTicks = Settings.maxDelayTicks;
            config.hotbarSlotPrimary = Settings.hotbarSlotPrimary;
            config.hotbarSlotSecondary = Settings.hotbarSlotSecondary;

            Files.createDirectories(CONFIG_PATH.getParent());

            try (Writer writer = Files.newBufferedWriter(CONFIG_PATH)) {
                GSON.toJson(config, writer);
            }
        } catch (Exception exception) {
            LOGGER.warn(String.valueOf(exception));
        }
    }
}
