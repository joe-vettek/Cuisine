package xueluoanping.cuisine.data.model;

import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import xueluoanping.cuisine.Cuisine;
import xueluoanping.cuisine.block.BlockBasin;
import xueluoanping.cuisine.block.firepit.BlockFirePit;
import xueluoanping.cuisine.block.baseblock.SimpleHorizontalEntityBlock;
import xueluoanping.cuisine.block.nature.BlockCuisineCrops;
import xueluoanping.cuisine.register.BlockEntityRegister;
import xueluoanping.cuisine.register.BlockRegister;
import xueluoanping.cuisine.register.CropRegister;

import javax.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BlockStatesDataProvider extends BlockStateProvider {


	public BlockStatesDataProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {

		super(gen, Cuisine.MODID, exFileHelper);
	}

	@Override
	protected void registerStatesAndModels() {
		simpleBlock(BlockRegister.bamboo_root.get());


		// this.customStageBlock(CropRegister.tomato.get(), resourceBlock("cross_crop"), "crop", BlockCuisineCrops.AGE, Arrays.asList(0,0, 1,1, 2,2,2, 3 ));

		ArrayList<RegistryObject<Block>> cropBlockList = new ArrayList<>();
		cropBlockList.addAll(CropRegister.DRBlocks.getEntries());
		cropBlockList.remove(CropRegister.rice);
		cropBlockList.remove(CropRegister.corn);
		cropBlockList.remove(CropRegister.cucumber);
		cropBlockList.forEach(crop -> {
			this.customStageBlock(crop.get(), resourceBlock("cross_crop"), "crop", BlockCuisineCrops.AGE, Arrays.asList(0, 0, 1, 1, 2, 2, 2, 3));
		});

		if (BlockEntityRegister.basinColored.size() == 0)
			throw new RuntimeException("空");
		// BlockEntityRegister.basinColored.forEach();
		BlockEntityRegister.basinColored.forEach((dyeColor, blockRegistryObject) -> {
			getVariantBuilder(blockRegistryObject.get()).forAllStatesExcept(state -> {
				return ConfiguredModel.builder()
						.modelFile(models().singleTexture(blockName(blockRegistryObject.get()), resourceBlock("basin_base"), "particle", resourceVanillaBlock(BlockEntityRegister.colorBlockMap.get(dyeColor).getRegistryName().getPath())))
						.build();
			}, BlockBasin.FACING, BlockBasin.LIGHT);
			// BlockEntityRegister.colorBlockMap.get(dyeColor).getRegistryName()
		});


		getVariantBuilder(BlockEntityRegister.fire_pit.get()).forAllStatesExcept(state -> {
			return ConfiguredModel.builder()
					.modelFile(models().withExistingParent(blockName(BlockEntityRegister.fire_pit.get()), resourceBlock("fire_pit_obj"))).rotationY(SimpleHorizontalEntityBlock.getRotateYByFacing(state))
					.build();
		},BlockFirePit.LIGHT_LEVEL);

		getVariantBuilder(BlockEntityRegister.barbecue_rack.get()).forAllStatesExcept(state -> {
			return ConfiguredModel.builder()
					.modelFile(models().withExistingParent(blockName(BlockEntityRegister.barbecue_rack.get()), resourceBlock("fire_pit_with_sticks_obj"))).rotationY(SimpleHorizontalEntityBlock.getRotateYByFacing(state))
					.build();
		});
		getVariantBuilder(BlockEntityRegister.wok_on_fire_pit.get()).forAllStatesExcept(state -> {
			return ConfiguredModel.builder()
					.modelFile(models().withExistingParent(blockName(BlockEntityRegister.wok_on_fire_pit.get()), resourceBlock("fire_pit_with_wok_obj"))).rotationY(SimpleHorizontalEntityBlock.getRotateYByFacing(state))
					.build();
		});
	}


	// Thanks vectorwing，great work
	// I am not proud of this method... But hey, it's runData. Only I shall have to deal with it.
	public void customStageBlock(Block block, @Nullable ResourceLocation parent, String textureKey, IntegerProperty ageProperty, List<Integer> suffixes, Property<?>... ignored) {
		getVariantBuilder(block)
				.forAllStatesExcept(state -> {
					int ageSuffix = state.getValue(ageProperty);
					String stageName = blockName(block) + "_stage_";
					stageName += suffixes.isEmpty() ? ageSuffix : suffixes.get(Math.min(suffixes.size() - 1, ageSuffix));
					// Cuisine.logger(stageName);
					if (parent == null) {
						return ConfiguredModel.builder()
								.modelFile(models().cross(stageName, resourceBlock(stageName))).build();
					}
					return ConfiguredModel.builder()
							.modelFile(models().singleTexture(stageName, parent, textureKey, resourceBlock(stageName))).build();
				}, ignored);
	}

	private String blockName(Block block) {
		return block.getRegistryName().getPath();
	}

	public static ResourceLocation resourceBlock(String path) {
		return new ResourceLocation(Cuisine.MODID, "block/" + path);
	}

	public ResourceLocation resourceVanillaBlock(String path) {
		return new ResourceLocation("block/" + path);
	}

}
