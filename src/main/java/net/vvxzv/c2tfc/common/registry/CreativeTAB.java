package net.vvxzv.c2tfc.common.registry;

import net.dries007.tfc.common.blocks.wood.Wood;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.vvxzv.c2tfc.C2TFC;

public class CreativeTAB {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, C2TFC.MODID);
    public static final RegistryObject<CreativeModeTab> C2TFC_TAB;

    static {
        C2TFC_TAB = CREATIVE_TAB.register(C2TFC.MODID, () -> CreativeModeTab.builder()
                .title(Component.translatable("c2tfc.tab.name"))
                .icon(() -> new ItemStack(C2TFCBlocks.STRESS_CONVERTERS.get(Wood.ACACIA)))
                .displayItems((parm, output) -> {
                    C2TFCBlocks.STRESS_CONVERTERS.forEach((woodType, blockEntry) -> {
                        output.accept(blockEntry.get());
                    });
                }).build()
        );
    }
}
