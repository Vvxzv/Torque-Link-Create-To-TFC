package net.vvxzv.c2tfc.compat.ponder;

import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.vvxzv.c2tfc.C2TFC;
import net.vvxzv.c2tfc.common.registry.C2TFCBlocks;
import net.vvxzv.c2tfc.compat.ponder.scene.StressConverterScene;
import org.jetbrains.annotations.NotNull;

public class PonderPlugin implements net.createmod.ponder.api.registration.PonderPlugin {

    @Override
    public @NotNull String getModId() {
        return C2TFC.MODID;
    }

    @Override
    public void registerScenes(@NotNull PonderSceneRegistrationHelper<ResourceLocation> helper) {
        C2TFCBlocks.STRESS_CONVERTERS.forEach((wood, stressConverterBlockEntry) -> {
            helper.forComponents(BuiltInRegistries.BLOCK.getKey(stressConverterBlockEntry.get()))
                    .addStoryBoard("stress_converter", StressConverterScene::introduction);
        });
    }
}
