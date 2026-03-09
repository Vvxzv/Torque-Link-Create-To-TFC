package net.vvxzv.c2tfc.client.blockRender;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.vvxzv.c2tfc.common.block.entity.StressConverterEntity;

public class StressConverterRender extends KineticBlockEntityRenderer<StressConverterEntity> {
    public StressConverterRender(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected void renderSafe(StressConverterEntity blockEntity, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        Direction direction = blockEntity.getBlockState().getValue(BlockStateProperties.FACING);
        VertexConsumer vb = buffer.getBuffer(RenderType.cutoutMipped());

        SuperByteBuffer shaftHalf = CachedBuffers.partialFacing(AllPartialModels.SHAFT_HALF, blockEntity.getBlockState(), direction.getOpposite());

        int lightBehind = 0;
        if(blockEntity.getLevel() != null) LevelRenderer.getLightColor(blockEntity.getLevel(), blockEntity.getBlockPos().relative(direction.getOpposite()));
        standardKineticRotationTransform(shaftHalf, blockEntity, lightBehind).renderInto(ms, vb);
    }
}
