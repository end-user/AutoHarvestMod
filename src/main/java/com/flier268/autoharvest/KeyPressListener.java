package com.flier268.autoharvest;

import com.flier268.autoharvest.Plugin.ClothConfig;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.TranslatableText;
import org.lwjgl.glfw.GLFW;

public class KeyPressListener {

    private KeyBinding key_Switch, key_ModeChange, key_Config;
    private KeyBinding key_HARVEST, key_PLANT, key_Farmer, key_SEED, key_FEED, key_FISHING, Key_BONEMEALING;

    public KeyPressListener() {
        String categoryGeneral = new TranslatableText("key.category.general").getString();
        String categorySwitchTo = new TranslatableText("key.category.switchTo").getString();
        key_ModeChange = new KeyBinding("key.general.modechange",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_H,
                categoryGeneral
        );
        key_Switch = new KeyBinding("key.general.switch",
                GLFW.GLFW_KEY_J,
                categoryGeneral
        );
        key_Config = new KeyBinding("key.general.config",
                GLFW.GLFW_KEY_K,
                categoryGeneral
        );
        key_HARVEST = new KeyBinding("harvest",
                GLFW.GLFW_KEY_UNKNOWN,
                categorySwitchTo
        );
        key_PLANT = new KeyBinding("plant",
                GLFW.GLFW_KEY_UNKNOWN,
                categorySwitchTo
        );
        key_Farmer = new KeyBinding("farmer",
                GLFW.GLFW_KEY_UNKNOWN,
                categorySwitchTo
        );
        key_SEED = new KeyBinding("seed",
                GLFW.GLFW_KEY_UNKNOWN,
                categorySwitchTo
        );
        key_FEED = new KeyBinding("feed",
                GLFW.GLFW_KEY_UNKNOWN,
                categorySwitchTo
        );
        key_FISHING = new KeyBinding("fishing",
                GLFW.GLFW_KEY_UNKNOWN,
                categorySwitchTo
        );
        Key_BONEMEALING = new KeyBinding("bonemealing",
                GLFW.GLFW_KEY_UNKNOWN,
                categorySwitchTo
        );
        KeyBindingHelper.registerKeyBinding(key_ModeChange);
        KeyBindingHelper.registerKeyBinding(key_Switch);
        KeyBindingHelper.registerKeyBinding(key_Config);
        KeyBindingHelper.registerKeyBinding(key_HARVEST);
        KeyBindingHelper.registerKeyBinding(key_PLANT);
        KeyBindingHelper.registerKeyBinding(key_Farmer);
        KeyBindingHelper.registerKeyBinding(key_SEED);
        KeyBindingHelper.registerKeyBinding(key_FEED);
        KeyBindingHelper.registerKeyBinding(key_FISHING);
        KeyBindingHelper.registerKeyBinding(Key_BONEMEALING);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            onProcessKey();
        });
    }

    public void onProcessKey() {
        if (key_Switch.wasPressed()) {
            AutoHarvest.instance.Switch = !AutoHarvest.instance.Switch;
            AutoHarvest.msg("notify.turn." + (AutoHarvest.instance.Switch ? "on" : "off"));
        } else if (key_Config.wasPressed()) {
            MinecraftClient.getInstance().openScreen(ClothConfig.openConfigScreen(MinecraftClient.getInstance().currentScreen));
        } else {
            String modeName = null;
            if (key_ModeChange.wasPressed()) {
                if (AutoHarvest.instance.overlayRemainingTick == 0) {
                    AutoHarvest.instance.overlayRemainingTick = 60;
                    modeName = AutoHarvest.instance.mode.toString().toLowerCase();
                } else {
                    AutoHarvest.instance.overlayRemainingTick = 60;
                    modeName = AutoHarvest.instance.toNextMode().toString().toLowerCase();
                }
            } else if (key_HARVEST.wasPressed()) {
                modeName = AutoHarvest.instance.toSpecifiedMode(AutoHarvest.HarvestMode.HARVEST).toString().toLowerCase();
            } else if (key_PLANT.wasPressed()) {
                modeName = AutoHarvest.instance.toSpecifiedMode(AutoHarvest.HarvestMode.PLANT).toString().toLowerCase();
            } else if (key_Farmer.wasPressed()) {
                modeName = AutoHarvest.instance.toSpecifiedMode(AutoHarvest.HarvestMode.Farmer).toString().toLowerCase();
            } else if (key_SEED.wasPressed()) {
                modeName = AutoHarvest.instance.toSpecifiedMode(AutoHarvest.HarvestMode.SEED).toString().toLowerCase();
            } else if (key_FEED.wasPressed()) {
                modeName = AutoHarvest.instance.toSpecifiedMode(AutoHarvest.HarvestMode.FEED).toString().toLowerCase();
            } else if (key_FISHING.wasPressed()) {
                modeName = AutoHarvest.instance.toSpecifiedMode(AutoHarvest.HarvestMode.FISHING).toString().toLowerCase();
            } else if (Key_BONEMEALING.wasPressed()) {
                modeName = AutoHarvest.instance.toSpecifiedMode(AutoHarvest.HarvestMode.BONEMEALING).toString().toLowerCase();
            }
            if (modeName != null)
                AutoHarvest.msg("notify.switch_to", new TranslatableText(modeName).getString());
        }
    }
}
