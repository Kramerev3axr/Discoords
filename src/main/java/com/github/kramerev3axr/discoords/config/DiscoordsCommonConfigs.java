package com.github.kramerev3axr.discoords.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class DiscoordsCommonConfigs 
{
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<String> DISCORD_CHANNEL_ID;
    public static final ForgeConfigSpec.ConfigValue<String> DISCORD_BOT_TOKEN;

    static {
        BUILDER.push("Configs for Discoords");

        DISCORD_CHANNEL_ID = BUILDER.comment("Discord Channel ID Mod will output Coordinates to")
                .define("Channel_ID", "");
        DISCORD_BOT_TOKEN = BUILDER.comment("Discord Bot Token")
                .define("Bot_Token", "");

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
