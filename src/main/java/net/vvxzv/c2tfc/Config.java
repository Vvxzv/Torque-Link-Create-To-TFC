package net.vvxzv.c2tfc;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@SuppressWarnings("removal")
@EventBusSubscriber(modid = C2TFC.MODID, bus = EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.DoubleValue STRESS_IMPACT = BUILDER.comment(" ").comment("Stress impact of stress converter. (default 64)").comment("应力转换器的应力影响").defineInRange("stressImpact", 64, 0, Double.MAX_VALUE);

    static final ModConfigSpec SPEC = BUILDER.build();

    public static double stressImpact;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        stressImpact = STRESS_IMPACT.get();
    }
}
