package com.flier268.autoharvest.Plugin;

import com.flier268.autoharvest.AutoHarvest;
import com.flier268.autoharvest.Configuration;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ClothConfig {
    public static Screen openConfigScreen(Screen parentScreen) {
        ConfigBuilder builder = ConfigBuilder.create()

                .setTitle(Text.translatable(AutoHarvest.MOD_NAME + " config screen")).setSavingRunnable(ClothConfig::saveConfig);

        ConfigCategory scrolling = builder.getOrCreateCategory(Text.translatable(AutoHarvest.MOD_NAME));
        ConfigEntryBuilder entryBuilder = ConfigEntryBuilder.create();

        Configuration c = AutoHarvest.instance.configure.load();

        scrolling.addEntry(entryBuilder.startBooleanToggle(Text.translatable("config.flower_is_seed"), c.flowerIsSeed.value).setDefaultValue(false).setSaveConsumer(b -> c.flowerIsSeed.value = b).build());

        scrolling.addEntry(entryBuilder.startIntSlider(Text.translatable("config.effect_radius"), c.effectRadius.value, Configuration.EffectRadius.Min, Configuration.EffectRadius.Max).setDefaultValue((new Configuration.EffectRadius()).value).setSaveConsumer(b -> c.effectRadius.value = b).build());
        scrolling.addEntry(entryBuilder.startIntSlider(Text.translatable("config.tick_skip"), c.tickSkip.value, Configuration.TickSkip.Min, Configuration.TickSkip.Max).setTooltip(Text.translatable("config.tick_skip_tooltip")).setDefaultValue(new Configuration.TickSkip().value).setSaveConsumer(b -> c.tickSkip.value = b).build());
        scrolling.addEntry(entryBuilder.startBooleanToggle(Text.translatable("config.keep_fishing_rod_alive"), c.keepFishingRodAlive.value).setDefaultValue(new Configuration.KeepFishingRodAlive().value).setSaveConsumer(b -> c.keepFishingRodAlive.value = b).build());
        return builder.setParentScreen(parentScreen).build();
    }

    private static void saveConfig() {
        AutoHarvest.instance.configure.save();
    }
}
