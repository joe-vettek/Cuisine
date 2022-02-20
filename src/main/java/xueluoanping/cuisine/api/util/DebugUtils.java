package xueluoanping.cuisine.api.util;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.level.Level;

public class DebugUtils {

	public static void sendPlayerMessageSingle(Level worldIn, TextComponent textComponent) {
		if (!worldIn.isClientSide) {
			Minecraft.getInstance().player.sendMessage(textComponent, Minecraft.getInstance().player.getUUID());
		}
	}

}
