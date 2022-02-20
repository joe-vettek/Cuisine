package xueluoanping.cuisine.datagen;

import snownee.kiwi.data.provider.KiwiBlockLoot;
import snownee.kiwi.util.Util;

public class CommonBlockLoot extends KiwiBlockLoot {

	public CommonBlockLoot() {
		// 指定从该模块中获取所有方块
		super(Util.RL("cuisine:core"));
	}

	@Override
	protected void _addTables() {

	}


}
