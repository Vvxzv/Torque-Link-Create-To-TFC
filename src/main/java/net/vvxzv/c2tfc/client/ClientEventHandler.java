package net.vvxzv.c2tfc.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.vvxzv.c2tfc.C2TFC;
import net.vvxzv.c2tfc.compat.ponder.Ponder;

@Mod.EventBusSubscriber(modid = C2TFC.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventHandler {

    @SubscribeEvent
    public static void clientSetupEvent(FMLClientSetupEvent event) {
        Ponder.init();
    }
}
