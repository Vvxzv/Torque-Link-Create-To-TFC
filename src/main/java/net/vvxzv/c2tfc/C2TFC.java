package net.vvxzv.c2tfc;

import com.simibubi.create.foundation.data.CreateRegistrate;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.vvxzv.c2tfc.common.registry.C2TFCBlockEntity;
import net.vvxzv.c2tfc.common.registry.C2TFCBlocks;
import net.vvxzv.c2tfc.common.registry.CreativeTAB;

@Mod(C2TFC.MODID)
public class C2TFC {
    public static final String MODID = "c2tfc";
    private static final StackWalker STACK_WALKER = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);
    private static final CreateRegistrate REGISTRATE = CreateRegistrate.create(MODID).defaultCreativeTab((ResourceKey<CreativeModeTab>) null);

    @SuppressWarnings("removal")
    public C2TFC() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        REGISTRATE.registerEventListeners(modEventBus);
        C2TFCBlocks.init();
        C2TFCBlockEntity.init();
        CreativeTAB.CREATIVE_TAB.register(modEventBus);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    public static CreateRegistrate registrate() {
        if (!STACK_WALKER.getCallerClass().getPackageName().startsWith("net.vvxzv.c2tfc")){
            throw new UnsupportedOperationException("Other mods are not permitted to use c2tfc's registrate instance.");
        }

        return REGISTRATE;
    }
}
