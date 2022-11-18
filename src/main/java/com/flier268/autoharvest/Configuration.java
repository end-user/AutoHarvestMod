package com.flier268.autoharvest;

import com.google.gson.*;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Files;

public class Configuration {
    private final File configFile;

    public FlowerIsSeed flowerIsSeed = new FlowerIsSeed();

    public static class FlowerIsSeed {
        public boolean value = false;
        private final String name = "flowerIsSeed";
    }

    public EffectRadius effectRadius = new EffectRadius();

    public static class EffectRadius {
        public int value = 3;
        private final String name = "effect_radius";
        public static final int Max = 3;
        public static final int Min = 0;
    }

    public TickSkip tickSkip = new TickSkip();

    public static class TickSkip {
        public int value = 0;
        private final String name = "tick_skip";
        public static final int Max = 100;
        public static final int Min = 0;
    }

    public KeepFishingRodAlive keepFishingRodAlive = new KeepFishingRodAlive();

    public static class KeepFishingRodAlive {
        public boolean value = true;
        String name = "keepFishingRodAlive";
    }


    public Configuration() {
        this.configFile = FabricLoader
                .getInstance()
                .getConfigDir()
                .resolve("AutoHarvest.json")
                .toFile();
        flowerIsSeed.value = false;
    }

    public Configuration load() {
        try {
            if (!Files.exists(this.configFile.toPath()))
                return this;
            String jsonStr = new String(Files.readAllBytes(this.configFile.toPath()));
            if (!jsonStr.equals("")) {
                JsonObject jsonObject = JsonParser.parseString(jsonStr).getAsJsonObject();

                if (jsonObject.has(flowerIsSeed.name)) {
                    try {
                        this.flowerIsSeed.value = jsonObject.getAsJsonPrimitive(flowerIsSeed.name).getAsBoolean();
                    } catch (Exception e) {
                    }
                }
                if (jsonObject.has(effectRadius.name)) {
                    try {
                        this.effectRadius.value = jsonObject.getAsJsonPrimitive(effectRadius.name).getAsInt();
                        if (effectRadius.value < EffectRadius.Min || effectRadius.value > EffectRadius.Max)
                            effectRadius.value = EffectRadius.Max;
                    } catch (Exception e) {
                    }
                }
                if (jsonObject.has(tickSkip.name)) {
                    try {
                        this.tickSkip.value = jsonObject.getAsJsonPrimitive(tickSkip.name).getAsInt();
                        if (tickSkip.value < TickSkip.Min || tickSkip.value > TickSkip.Max)
                            tickSkip.value = TickSkip.Min;
                    } catch (Exception e) {
                    }
                }
                if (jsonObject.has(keepFishingRodAlive.name)) {
                    try {
                        this.keepFishingRodAlive.value = jsonObject.getAsJsonPrimitive(keepFishingRodAlive.name).getAsBoolean();
                    } catch (Exception e) {
                    }
                }
                return this;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public Configuration save() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(flowerIsSeed.name, this.flowerIsSeed.value);
        jsonObject.addProperty(effectRadius.name, this.effectRadius.value);
        jsonObject.addProperty(tickSkip.name, this.tickSkip.value);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement el = JsonParser.parseString(jsonObject.toString());
        try (PrintWriter out = new PrintWriter(configFile)) {
            out.println(gson.toJson(el));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return this;
    }
}
