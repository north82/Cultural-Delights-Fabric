package dev.sterner.culturaldelights.common.block;

import com.nhoryzon.mc.farmersdelight.block.FeastBlock;
import dev.sterner.culturaldelights.common.registry.CDObjects;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ExoticRollMedleyBlock extends FeastBlock {
    public static final IntegerProperty ROLL_SERVINGS = IntegerProperty.create("servings", 0, 8);
    protected static final VoxelShape PLATE_SHAPE = Block.box(1.0, 0.0, 1.0, 15.0, 2.0, 15.0);
    protected static final VoxelShape FOOD_SHAPE;
    public final List<Item> riceRollServings;

    public ExoticRollMedleyBlock() {
        super(Properties.copy(Blocks.CAKE), CDObjects.TROPICAL_ROLL, true);
        this.riceRollServings = Arrays.asList(
                CDObjects.PUFFERFISH_ROLL,
                CDObjects.PUFFERFISH_ROLL,
                CDObjects.TROPICAL_ROLL,
                CDObjects.TROPICAL_ROLL,
                CDObjects.TROPICAL_ROLL,
                CDObjects.CHICKEN_ROLL_SLICE,
                CDObjects.CHICKEN_ROLL_SLICE,
                CDObjects.CHICKEN_ROLL_SLICE);
    }

    @Override
    public IntegerProperty getServingsProperty() {
        return ROLL_SERVINGS;
    }

    @Override
    public int getMaxServings() {
        return 8;
    }

    @Override
    public ItemStack getServingStack(BlockState state) {
        return new ItemStack(riceRollServings.get(state.getValue(getServingsProperty()) - 1));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return state.getValue(getServingsProperty()) == 0 ? PLATE_SHAPE : FOOD_SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, ROLL_SERVINGS);
    }


    static {
        FOOD_SHAPE = Shapes.joinUnoptimized(PLATE_SHAPE, Block.box(2.0, 2.0, 2.0, 14.0, 4.0, 14.0), BooleanOp.OR);
    }
}
