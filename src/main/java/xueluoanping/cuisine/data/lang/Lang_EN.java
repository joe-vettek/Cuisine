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

public class Lang_EN extends LanguageProvider {
    public Lang_EN(DataGenerator gen) {
        super(gen, Cuisine.MODID, "en_us");
        Cuisine.logger("hello");
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
        add("item."+standard+"_bucket",name+" Bucket");
    }
    @Override
    protected void addTranslations() {
        add(Cuisine.MODID, "Cuisine: the memory of food");
        addItemGroup(Cuisine.CREATIVE_TAB, "Cuisine");

        addBlock(BlockRegister.bamboo_root,"Bamboo Root");
        addBlock(BlockRegister.bamboo_plant,"Henon bamboo");
        addBlock(BlockRegister.bamboo,"Henon Bamboo Shoot");
        addBlock(BlockEntityRegister.fire_pit,"Fire Pit");
        addBlock(BlockRegister.ditch,"Irrigation Canal");
        add(BlockEntityRegister.fire_pit.get().getDescriptionId().toString()+"_with_wok", "Fire Pit With Wok");
        add(BlockEntityRegister.fire_pit.get().getDescriptionId().toString()+"_with_sticks", "Fire Pit With Sticks");
        add(BlockEntityRegister.fire_pit.get().getDescriptionId().toString()+"_with_frying_pan", "Fire Pit With Frying Pan");
        addBlock(BlockEntityRegister.mill,"Mill");
        addBlock(()->ModContents.basin,"Basin");
        addHint(()->ModContents.basin,"§4used for：\n§3All pots can be used to crush fruits and vegetables or soak things. Non wooden pots can be heated in a fire pit to obtain coarse salt");
        addFluid(FluidRegister.juice,"Strange Juice");
        add("fluid."+Cuisine.MODID+".cuisine_juice_with_material","Juice");
    }
}
