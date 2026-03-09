package net.vvxzv.c2tfc.common.registry;

import com.simibubi.create.foundation.data.*;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.TooltipModifier;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.createmod.catnip.lang.FontHelper;
import net.dries007.tfc.common.blocks.wood.Wood;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.vvxzv.c2tfc.C2TFC;
import net.vvxzv.c2tfc.common.block.StressConverter;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("removal")
public class C2TFCBlocks {
    public static final CreateRegistrate REGISTRATE;
    public static final Map<Wood, BlockEntry<StressConverter>> STRESS_CONVERTERS = new HashMap<>();

    public static void register(IEventBus modEventBus) {
        REGISTRATE.registerEventListeners(modEventBus);
    }

    static {
        REGISTRATE = CreateRegistrate.create(C2TFC.MODID);

        for (Wood woodType : Wood.VALUES) {
            String blockId = "stress_converter/" + woodType.getSerializedName();
            String translationKey = "block.c2tfc.stress_converter." + woodType.getSerializedName();

            STRESS_CONVERTERS.put(woodType,
                    REGISTRATE.block(blockId, StressConverter::new)
                            .initialProperties(SharedProperties::stone)
                            .properties(p -> p.noOcclusion().mapColor(MapColor.PODZOL))
                            .transform(TagGen.axeOrPickaxe())
                            .blockstate(BlockStateGen.directionalBlockProvider(true))
                            .addLayer(() -> RenderType::cutoutMipped)
                            .item()
                            .transform(ModelGen.customItemModel())
                            .onRegisterAfter(Registries.ITEM, item -> {
                                ItemDescription.useKey(item, translationKey);
                                TooltipModifier.REGISTRY.register(item.asItem(),
                                        new ItemDescription.Modifier(item.asItem(), FontHelper.Palette.STANDARD_CREATE));
                            })
                            .register()
            );
        }
    }
}
