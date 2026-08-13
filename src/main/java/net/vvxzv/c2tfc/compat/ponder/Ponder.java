package net.vvxzv.c2tfc.compat.ponder;

import net.createmod.ponder.foundation.PonderIndex;
import net.minecraftforge.fml.ModList;

public class Ponder {
    public static void init() {
        if (ModList.get().isLoaded("ponder")) {
            PonderIndex.addPlugin(new PonderPlugin());
        }
    }
}
