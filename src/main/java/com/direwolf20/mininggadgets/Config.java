package com.direwolf20.mininggadgets;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

import java.nio.file.Path;

@Mod.EventBusSubscriber
public class Config {

    public static final String CATEGORY_GENERAL = "general";
    public static final String CATEGORY_POWER = "power";
    public static final String SUBCATEGORY_MININGGADGET = "mining_gadget";

    private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
    private static final ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();

    public static ForgeConfigSpec COMMON_CONFIG;
    public static ForgeConfigSpec CLIENT_CONFIG;

    public static ForgeConfigSpec.IntValue MININGGADGET_MAXPOWER;
    public static ForgeConfigSpec.IntValue MININGGADGET_BASECOST;

    static {

        COMMON_BUILDER.comment("General settings").push(CATEGORY_GENERAL);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Power settings").push(CATEGORY_POWER);

        setupMiningGadgetConfig();

        COMMON_BUILDER.pop();


        COMMON_CONFIG = COMMON_BUILDER.build();
        CLIENT_CONFIG = CLIENT_BUILDER.build();
    }

    private static void setupMiningGadgetConfig() {
        COMMON_BUILDER.comment("Mining Gadget Settings").push(SUBCATEGORY_MININGGADGET);

        MININGGADGET_MAXPOWER = COMMON_BUILDER.comment("Maximum power for the Mining Gadget")
                .defineInRange("maxPower", 1000000, 0, Integer.MAX_VALUE);
        MININGGADGET_BASECOST = COMMON_BUILDER.comment("Base cost per block broken")
                .defineInRange("baseCost", 200, 0, Integer.MAX_VALUE);
        /*FIRSTBLOCK_GENERATE = COMMON_BUILDER.comment("Power generation per diamond")
                .defineInRange("generate", 1000, 0, Integer.MAX_VALUE);
        FIRSTBLOCK_SEND = COMMON_BUILDER.comment("Power generation to send per tick")
                .defineInRange("send", 100, 0, Integer.MAX_VALUE);
        FIRSTBLOCK_TICKS = COMMON_BUILDER.comment("Ticks per diamond")
                .defineInRange("ticks", 20, 0, Integer.MAX_VALUE);*/

        COMMON_BUILDER.pop();
    }

    public static void loadConfig(ForgeConfigSpec spec, Path path) {

        final CommentedFileConfig configData = CommentedFileConfig.builder(path)
                .sync()
                .autosave()
                .writingMode(WritingMode.REPLACE)
                .build();

        configData.load();
        spec.setConfig(configData);
    }

    @SubscribeEvent
    public static void onLoad(final ModConfig.Loading configEvent) {

    }

    @SubscribeEvent
    public static void onReload(final ModConfig.ConfigReloading configEvent) {
    }


}

