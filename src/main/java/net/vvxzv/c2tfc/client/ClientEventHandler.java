package net.vvxzv.c2tfc.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.vvxzv.c2tfc.C2TFC;
import net.vvxzv.c2tfc.compat.ponder.Ponder;

@SuppressWarnings("removal")
@EventBusSubscriber(modid = C2TFC.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventHandler {

    public ClientEventHandler(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    public static void clientSetupEvent(FMLClientSetupEvent event) {
        Ponder.init();
    }
}
