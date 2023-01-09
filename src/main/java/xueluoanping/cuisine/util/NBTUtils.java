package xueluoanping.cuisine.util;

import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

		CompoundTag data = new CompoundTag();
		data.putString("Name", txtureBlock.getRegistryName().toString());
		try {
			if (txtureBlock.asItem().getDefaultInstance().getTag() != null) {
				data.putString("Properties", txtureBlock.defaultBlockState().toString());
			}
		} catch (Exception exception) {
			logger.info("Can't get the properties:" + exception.getMessage() + txtureBlock.getRegistryName().toString() + "//" + txtureBlock.delegate.get().defaultBlockState().getProperties());
		}

		CompoundTag blockNBT = new CompoundTag();
		blockNBT.put("Block", data);
		blockNBT.putString("Type", "Block");

		CompoundTag particleNBT = new CompoundTag();
		particleNBT.put("particle", blockNBT);

		CompoundTag overridesNBT = new CompoundTag();
		overridesNBT.put("Overrides", particleNBT);
//		logger.info(overridesNBT.get().getAsString());
		originStack.getOrCreateTag().put("BlockEntityTag", overridesNBT);
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

//	//获取注册物品中所有符合要求的方块
//	public static ArrayList<Block> getBlockListFromRegister() {
//		ArrayList<Block> blockArrayList = new ArrayList<>();
//		ForgeRegistries.BLOCKS.getValues().stream()
//				.forEach(block -> {
//					if (FullBlockIngredient.isFullBlock(block.asItem().getDefaultInstance()))
//						blockArrayList.add(block);
//				});
//		return blockArrayList;
//	}

	//获取Block对应实例
	public static Block getBlockFromRegisterByBlock(ResourceLocation resourceLocation) {
		return ForgeRegistries.BLOCKS.getValue(resourceLocation);
	}

	public static ResourceLocation de(String rl) {
		String[] s=rl.split(":");
		if (s.length==1)
		return new ResourceLocation(rl);
		if (s.length==2)
			return new ResourceLocation(s[0],s[1]);
		else throw new ClassCastException("Cuisine: ResourceLocation can only have one colon");
	}

}
