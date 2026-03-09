package net.vvxzv.c2tfc.common.block.entity;

import com.simibubi.create.content.kinetics.transmission.SplitShaftBlockEntity;
import net.dries007.tfc.common.blockentities.rotation.RotatingBlockEntity;
import net.dries007.tfc.util.rotation.NetworkAction;
import net.dries007.tfc.util.rotation.Node;
import net.dries007.tfc.util.rotation.Rotation;
import net.dries007.tfc.util.rotation.SourceNode;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.vvxzv.c2tfc.Config;

public class StressConverterEntity extends SplitShaftBlockEntity implements RotatingBlockEntity {
    private final SourceNode node;
    private boolean invalid = false;
    private final Direction facing;
    private float speed;
    protected boolean needsClientUpdate;
    protected boolean isDirty;

    public StressConverterEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.facing = state.getValue(BlockStateProperties.FACING);
        this.node = new SourceNode(pos, Node.ofAxis(this.facing.getAxis()), this.facing, 0.0F) {
            public String toString() {
                return "StressConverter[pos=%s, axis=%s]".formatted(this.pos(), StressConverterEntity.this.facing.getAxis());
            }
        };
    }

    public float getRotationSpeedModifier(Direction direction) {
        return this.hasSource() && direction != this.getSourceFacing() && this.getBlockState().getValue(BlockStateProperties.POWERED) ? 0.0F : 1.0F;
    }

    private static float stressImpact() {
        return (float) Config.stressImpact;
    }

    public float calculateStressApplied() {
        return this.getBlockState().getValue(BlockStateProperties.POWERED) ? 0.0F : stressImpact();
    }

    public void tick() {
        super.tick();
        this.serverTick();
    }

    public void serverTick() {
        this.checkForLastTickSync();
        this.clientTick();
        if (this.level != null && this.level.getGameTime() % 20L == 0L) {
            this.speed = this.getBlockState().getValue(BlockStateProperties.POWERED) ? 0.0F : radPerTick(this.getSpeed());
            this.markForSync();
        }
    }

    public void clientTick() {
        Rotation.Tickable rotation = this.node.rotation();
        rotation.tick();
        rotation.setSpeed(this.facing != Direction.SOUTH && this.facing != Direction.EAST && this.facing != Direction.DOWN ? this.speed : -this.speed);
    }

    public static float radPerTick(float rpm) {
        return (float)((double)rpm * 0.005235987755982988);
    }

    protected void onLoadAdditional() {
        this.performNetworkAction(NetworkAction.ADD_SOURCE);
    }

    protected void onUnloadAdditional() {
        this.performNetworkAction(NetworkAction.REMOVE);
    }

    public void markAsInvalidInNetwork() {
        this.invalid = true;
    }

    public boolean isInvalidInNetwork() {
        return this.invalid;
    }

    public Node getRotationNode() {
        return this.node;
    }

    public void checkForLastTickSync() {
        if (this.needsClientUpdate) {
            this.needsClientUpdate = false;
            this.tfc_be_markForSync();
        }

        if (this.isDirty) {
            this.isDirty = false;
            this.tfc_be_markDirty();
        }
    }

    public void markForSync() {
        this.needsClientUpdate = true;
    }

    public void markDirty() {
        this.isDirty = true;
    }

    public final void invalidate() {
        super.invalidate();
        this.onUnloadAdditional();
    }

    public final void onChunkUnloaded() {
        super.onChunkUnloaded();
        this.onUnloadAdditional();
    }

    public final void onLoad() {
        this.requestModelDataUpdate();
        this.onLoadAdditional();
    }

    public void markForBlockUpdate() {
        if (this.level != null) {
            BlockState state = this.level.getBlockState(this.worldPosition);
            this.level.sendBlockUpdated(this.worldPosition, state, state, 3);
            this.setChanged();
        }
    }

    public void tfc_be_markForSync() {
        this.sendVanillaUpdatePacket();
        this.setChanged();
    }

    public void tfc_be_markDirty() {
        if (this.level != null && this.level.hasChunkAt(this.worldPosition)) {
            this.level.getChunkAt(this.worldPosition).setUnsaved(true);
        }
    }

    public void sendVanillaUpdatePacket() {
        ClientboundBlockEntityDataPacket packet = this.getUpdatePacket();
        BlockPos pos = this.getBlockPos();
        if (this.level instanceof ServerLevel serverLevel) {
            serverLevel.getChunkSource().chunkMap.getPlayers(new ChunkPos(pos), false).forEach((e) -> e.connection.send(packet));
        }
    }

    protected void write(CompoundTag compound, boolean clientPacket) {
        super.write(compound, clientPacket);
        this.node.rotation().saveToTag(compound);
        compound.putBoolean("invalid", this.invalid);
    }

    protected void read(CompoundTag compound, boolean clientPacket) {
        super.read(compound, clientPacket);
        this.node.rotation().loadFromTag(compound);
        this.invalid = compound.getBoolean("invalid");
    }
}
