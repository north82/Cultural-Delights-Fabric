package dev.sterner.culturaldelights.common.block;

import dev.sterner.culturaldelights.common.registry.CDObjects;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CornUpperBlock extends CropBlock {
    public static final IntegerProperty CORN_AGE;
    private static final VoxelShape[] SHAPE_BY_AGE;

    public CornUpperBlock(Properties settings) {
        super(settings);
    }

    public IntegerProperty getAgeProperty() {
        return CORN_AGE;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE_BY_AGE[state.getValue(this.getAgeProperty())];
    }

    public int getMaxAge() {
        return 3;
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return CDObjects.CORN_KERNELS;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(CORN_AGE);
    }

    @Override
    protected boolean mayPlaceOn(BlockState floor, BlockGetter world, BlockPos pos) {
        return floor.getBlock() == CDObjects.CORN_CROP;
    }


    protected int getBonemealAgeIncrease(Level worldIn) {
        return super.getBonemealAgeIncrease(worldIn) / 3;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        return (world.getRawBrightness(pos, 0) >= 8 || world.canSeeSky(pos)) && world.getBlockState(pos.below()).getBlock() == CDObjects.CORN_CROP;
    }

    static {
        CORN_AGE = BlockStateProperties.AGE_3;
        SHAPE_BY_AGE = new VoxelShape[]{Block.box(3.0D, 0.0D, 3.0D, 13.0D, 8.0D, 13.0D),
                Block.box(3.0D, 0.0D, 3.0D, 13.0D, 10.0D, 13.0D),
                Block.box(2.0D, 0.0D, 2.0D, 14.0D, 12.0D, 14.0D),
                Block.box(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D)};
    }
}
