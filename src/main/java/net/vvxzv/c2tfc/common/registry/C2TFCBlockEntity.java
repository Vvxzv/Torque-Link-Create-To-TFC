package net.vvxzv.c2tfc.common.registry;

import com.simibubi.create.content.kinetics.transmission.SplitShaftVisual;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.vvxzv.c2tfc.C2TFC;
import net.vvxzv.c2tfc.client.blockRender.StressConverterRender;
import net.vvxzv.c2tfc.common.block.entity.StressConverterEntity;

public class C2TFCBlockEntity {
    public static final CreateRegistrate REGISTRATE;
    public static final BlockEntityEntry<StressConverterEntity> STRESS_CONVERTER;

    public static void register(IEventBus modEventBus) {
        REGISTRATE.registerEventListeners(modEventBus);
    }

    static {
        REGISTRATE = CreateRegistrate.create(C2TFC.MODID);

        NonNullSupplier<? extends Block>[] STRESS_CONVERTER_BLOCKS = C2TFCBlocks.STRESS_CONVERTERS.values().stream()
                .map(blockEntry -> (NonNullSupplier<Block>) blockEntry::get)
                .toArray(NonNullSupplier[]::new);

        STRESS_CONVERTER = REGISTRATE
                .blockEntity("stress_converter", StressConverterEntity::new)
                .visual(() -> SplitShaftVisual::new, false)
                .validBlocks(STRESS_CONVERTER_BLOCKS)
                .renderer(() -> StressConverterRender::new)
                .register();
    }
}
