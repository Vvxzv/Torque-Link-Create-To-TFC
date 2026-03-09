package net.vvxzv.c2tfc;

import com.simibubi.create.foundation.data.CreateRegistrate;
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
    public static final CreateRegistrate REGISTRATE  = CreateRegistrate.create(C2TFC.MODID);

    @SuppressWarnings("removal")
    public C2TFC() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        C2TFCBlocks.init();
        C2TFCBlockEntity.init();
        REGISTRATE.registerEventListeners(modEventBus);
        CreativeTAB.CREATIVE_TAB.register(modEventBus);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }
}
