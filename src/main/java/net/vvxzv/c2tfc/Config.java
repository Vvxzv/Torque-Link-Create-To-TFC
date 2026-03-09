package net.vvxzv.c2tfc;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = C2TFC.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.DoubleValue STRESS_IMPACT = BUILDER.comment(" ").comment("Stress impact of stress converter. (default 64)").comment("应力转换器的应力影响").defineInRange("stressImpact", 64, 0, Double.MAX_VALUE);

    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static double stressImpact;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        stressImpact = STRESS_IMPACT.get();
    }
}
