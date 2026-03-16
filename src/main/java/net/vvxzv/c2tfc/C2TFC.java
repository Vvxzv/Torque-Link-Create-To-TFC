package net.vvxzv.c2tfc;

import com.simibubi.create.foundation.data.CreateRegistrate;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.vvxzv.c2tfc.common.registry.C2TFCBlockEntity;
import net.vvxzv.c2tfc.common.registry.C2TFCBlocks;
import net.vvxzv.c2tfc.common.registry.CreativeTAB;

@Mod(C2TFC.MODID)
public class C2TFC {
    public static final String MODID = "c2tfc";
    private static final StackWalker STACK_WALKER = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);
    private static final CreateRegistrate REGISTRATE = CreateRegistrate.create(MODID).defaultCreativeTab((ResourceKey<CreativeModeTab>) null);

    public C2TFC(IEventBus modEventBus, ModContainer modContainer) {
        REGISTRATE.registerEventListeners(modEventBus);
        C2TFCBlocks.init();
        C2TFCBlockEntity.init();
        CreativeTAB.CREATIVE_TAB.register(modEventBus);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    public static CreateRegistrate registrate() {
        if (!STACK_WALKER.getCallerClass().getPackageName().startsWith("net.vvxzv.c2tfc")){
            throw new UnsupportedOperationException("Other mods are not permitted to use c2tfc's registrate instance.");
        }

        return REGISTRATE;
    }
}
