package xueluoanping.cuisine.util;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.level.Level;

public class DebugUtils {

	public static void sendPlayerMessageSingle(Level worldIn, TextComponent textComponent) {
		if (!worldIn.isClientSide()) {
			Minecraft.getInstance().player.sendMessage(textComponent, Minecraft.getInstance().player.getUUID());
		}
	}
	public static void sendPlayerMessageSingle(Level worldIn, String s) {
		if (!worldIn.isClientSide()) {
			Minecraft.getInstance().player.sendMessage(new TextComponent(s), Minecraft.getInstance().player.getUUID());
		}
	}

	public static void sendPlayerMessageServer(Level worldIn, String s) {
		if (!worldIn.isClientSide()) {
			worldIn.getServer().sendMessage(new TextComponent(s),null);}
		else {
			Minecraft.getInstance().player.sendMessage(new TextComponent(s),
					Minecraft.getInstance().player.getUUID());
			Minecraft.getInstance().player.displayClientMessage(new TextComponent(s), true);
		}
	}

}
