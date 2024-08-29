package xueluoanping.cuisine.plugin.jade;

import mcp.mobius.waila.api.IWailaClientRegistration;
import mcp.mobius.waila.api.IWailaCommonRegistration;
import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.WailaPlugin;
import xueluoanping.cuisine.register.BlockEntityRegister;

@WailaPlugin
public class JadeCompact implements IWailaPlugin {

	@Override
	public void register(IWailaCommonRegistration registration) {
		//TODO register data providers and config options here
	}

	@Override
	public void registerClient(IWailaClientRegistration registration) {
		//TODO register component providers and icon providers here
//		JadeCompact.client = registration;
		registration.usePickedResult(BlockEntityRegister.basin.get());
	}

}
