package xueluoanping.cuisine.api.util;

import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import snownee.kiwi.recipe.FullBlockIngredient;
import snownee.kiwi.util.NBTHelper;

import java.util.ArrayList;

import static xueluoanping.cuisine.CoreModule.Basin;

public class NBTUtils {
	public static Logger logger = LogManager.getLogger();

	//用于读取本模组中带NBT的可变贴图物品所用纹理的BLock注册名
	public static String getNameFromRetxtureStack(ItemStack stack) {
		try {
			return ((CompoundTag) ((CompoundTag) ((CompoundTag) ((CompoundTag)
					stack.getOrCreateTag().get("BlockEntityTag"))
					.get("Overrides"))
					.get("particle"))
					.get("Block"))
					.getString("Name");
		} catch (Exception ex) {
			return "null";
		}
	}

	//用于读取本模组中带NBT的可变贴图物品所用纹理的BLock注册名
	public static String getNameFromRetxtureTag(CompoundTag tag) {
		try {
			return ((CompoundTag) ((CompoundTag) tag
					.get("particle"))
					.get("Block"))
					.getString("Name");
		} catch (Exception ex) {
			return "null";
		}
	}

	//给物品添加纹理TAG
	public static ItemStack createTagFromTxtureProvider(ItemStack originStack, Block txtureBlock) {
//		var stack = Basin.asItem().getDefaultInstance();

		NBTHelper data = NBTHelper.create();
		data.setString("Name", txtureBlock.getRegistryName().toString());
		try {
			if (txtureBlock.asItem().getDefaultInstance().getTag() != null) {
				data.setString("Properties", txtureBlock.defaultBlockState().toString());
			}
		} catch (Exception exception) {
			logger.info("Can't get the properties:" + exception.getMessage() + txtureBlock.getRegistryName().toString() + "//" + txtureBlock.delegate.get().defaultBlockState().getProperties());
		}

		NBTHelper blockNBT = NBTHelper.create();
		blockNBT.setTag("Block", data.get());
		blockNBT.setString("Type", "Block");

		NBTHelper particleNBT = NBTHelper.create();
		particleNBT.setTag("particle", blockNBT.get());

		NBTHelper overridesNBT = NBTHelper.create();
		overridesNBT.setTag("Overrides", particleNBT.get());
//		logger.info(overridesNBT.get().getAsString());
		originStack.getOrCreateTag().put("BlockEntityTag", overridesNBT.get());
		return originStack;
	}

	//输入命名空间和路径，在注册的方块中检索物品（未处理是否存在，建议catch异常）
	public static Block getBlockFromRegister(String[] space) {
		try {
			if (space.length == 1)
				return Registry.BLOCK.get(new ResourceLocation(space[0]));
			else if (space.length == 2)
				return Registry.BLOCK.get(new ResourceLocation(space[0], space[1]));
		} catch (Exception e) {
//			logger.info(space);
		}
		return Blocks.AIR;
	}

	//获取注册物品中所有符合要求的方块
	public static ArrayList<Block> getBlockListFromRegister() {
		ArrayList<Block> blockArrayList = new ArrayList<>();
		ForgeRegistries.BLOCKS.getValues().stream()
				.forEach(block -> {
					if (FullBlockIngredient.isFullBlock(block.asItem().getDefaultInstance()))
						blockArrayList.add(block);
				});
		return blockArrayList;
	}

}
