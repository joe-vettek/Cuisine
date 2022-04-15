package xueluoanping.cuisine.data.model;

import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import xueluoanping.cuisine.Cuisine;
import xueluoanping.cuisine.register.BlockRegister;

import java.util.function.Supplier;

public class CuisineItemModel extends ItemModelProvider {
    public CuisineItemModel(DataGenerator generator,  ExistingFileHelper existingFileHelper) {
        super(generator, Cuisine.MODID, existingFileHelper);
    }
    private String blockName(Supplier<? extends Block> block) {
        return block.get().getRegistryName().getPath();
    }
    @Override
    protected void registerModels() {
        withExistingParent(blockName(BlockRegister.bamboo_root),modLoc("block/" + blockName(BlockRegister.bamboo_root)));
    }
}
