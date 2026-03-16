package net.vvxzv.c2tfc.common.block;

import com.simibubi.create.content.kinetics.base.DirectionalKineticBlock;
import com.simibubi.create.foundation.block.IBE;
import net.dries007.tfc.common.blockentities.rotation.RotatingBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.vvxzv.c2tfc.common.block.entity.StressConverterEntity;
import net.vvxzv.c2tfc.common.registry.C2TFCBlockEntity;

public class StressConverter extends DirectionalKineticBlock implements IBE<StressConverterEntity> {
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    public StressConverter(Properties properties) {
        super(properties);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(POWERED);
        super.createBlockStateDefinition(builder);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getNearestLookingDirection().getOpposite()).setValue(POWERED, false);
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        boolean hasSignal = level.hasNeighborSignal(pos);
        if (hasSignal != state.getValue(POWERED)) {
            level.setBlock(pos, state.setValue(POWERED, hasSignal), 3);
        }

    }

    @Override
    public Direction.Axis getRotationAxis(BlockState blockState) {
        return blockState.getValue(FACING).getAxis();
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        return face == state.getValue(FACING);
    }

    @Override
    public Class<StressConverterEntity> getBlockEntityClass() {
        return StressConverterEntity.class;
    }

    @Override
    public BlockEntityType<? extends StressConverterEntity> getBlockEntityType() {
        return C2TFCBlockEntity.STRESS_CONVERTER.get();
    }

    @Override
    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        BlockEntity var6 = pLevel.getBlockEntity(pPos);
        if (var6 instanceof RotatingBlockEntity entity) {
            entity.destroyIfInvalid(pLevel, pPos);
        }

        super.tick(pState, pLevel, pPos, pRandom);
    }
}
