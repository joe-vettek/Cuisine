package xueluoanping.cuisine.data.lang;

import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraftforge.common.data.LanguageProvider;
import xueluoanping.cuisine.Cuisine;
import xueluoanping.cuisine.register.BlockEntityRegister;
import xueluoanping.cuisine.register.BlockRegister;
import xueluoanping.cuisine.register.FluidRegister;
import xueluoanping.cuisine.register.ModContents;

import java.util.function.Supplier;

public class CuisineLang_ZH extends LanguageProvider {
    public CuisineLang_ZH(DataGenerator gen) {
        super(gen, Cuisine.MODID, "zh_cn");
    }

    private void addItemGroup(CreativeModeTab group, String name) {
        add(group.getDisplayName().getString(), name);
    }

    private void addHint(Supplier<? extends Block> key, String hint)
    {
        add(key.get().getDescriptionId()+".hint",hint);
    }

    private void addFluid(Supplier<? extends FlowingFluid> key, String name)
    {
        String path=key.get().getRegistryName().getPath();
        String standard =Cuisine.MODID+"."+path;
        add("fluid."+standard,name);
        add("fluid."+standard+"_block",name);
        add("item."+standard+"_bucket",name+"桶");
    }
    @Override
    protected void addTranslations() {
        add(Cuisine.MODID, "料理工艺•追忆（烟火之忆）");
        addItemGroup(Cuisine.CREATIVE_TAB, "料理工艺•追忆");

        addBlock(BlockRegister.bamboo_root,"竹根");
        addBlock(BlockRegister.bamboo_plant,"淡竹笋");
        addBlock(BlockRegister.bamboo,"淡竹");
        addBlock(BlockEntityRegister.fire_pit,"火塘");
        addBlock(BlockRegister.ditch,"灌溉渠");
        add(BlockEntityRegister.fire_pit.get().getDescriptionId().toString()+"_with_wok", "火塘和铁锅");
        add(BlockEntityRegister.fire_pit.get().getDescriptionId().toString()+"_with_sticks", "烧烤架");
        add(BlockEntityRegister.fire_pit.get().getDescriptionId().toString()+"_with_frying_pan", "火塘和煎锅");
        addBlock(BlockEntityRegister.mill,"磨");
        addBlock(()->ModContents.basin,"盆");
        addHint(()->ModContents.basin,"§4用途：\n§3所有的盆都可以用来压碎蔬果或者浸泡物品，非木质盆可以在火坑上加热获取粗盐");
        addFluid(FluidRegister.juice,"丢失味道的果汁");
        add("fluid."+Cuisine.MODID+".cuisine_juice_with_material","汁");
    }


}
