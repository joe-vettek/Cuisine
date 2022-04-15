package xueluoanping.cuisine.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import xueluoanping.cuisine.register.BlockEntityRegister;

public class MillBlockEntity extends SyncBlockEntity {


    private float progress;
    private int power;


    public MillBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityRegister.mill_entity_type.get(), pos, state);
        this.progress = 360;
        this.power = 0;
    }


    @Override
    public void load(CompoundTag tag) {
        this.progress = tag.contains("progress") ? tag.getInt("progress") : 360;
        this.power = tag.contains("power") ? tag.getInt("power") : 0;
        super.load(tag);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        tag.putFloat("progress", progress);
        tag.putInt("power", power);
        super.saveAdditional(tag);
    }

    public float getProgress() {
        return this.progress;
    }

    public boolean addProgress(float period) {
        float addAmount = Math.min(period, 360);
        this.progress -= addAmount;
        if (this.progress <= 0) {
            this.progress += 360;
        }
        inventoryChanged();
        if (this.progress < 0) return false;
        return true;
    }

    public int getPower() {
        return this.power;
    }

    public void addPower(int provide) {
        if (provide < 100 && this.power < 720) {
            this.power += provide;
            inventoryChanged();
        }
    }

    public void subPower(int loss) {
        this.power = Math.max(this.power - loss, 0);
        inventoryChanged();
    }


    //write here,server side
    public static void tickEntity(Level worldIn, BlockPos pos, BlockState blockState, MillBlockEntity millBlockEntity) {
        if (millBlockEntity.getPower() > 0) {
            if (millBlockEntity.getPower() > 360) {
                millBlockEntity.subPower(2);
                millBlockEntity.addProgress(1+(millBlockEntity.getPower()-360)/180);
            } else {
                millBlockEntity.subPower(1);
                millBlockEntity.addProgress(1);
            }
        }
    }
}
