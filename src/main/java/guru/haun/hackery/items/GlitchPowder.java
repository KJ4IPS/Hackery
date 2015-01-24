package guru.haun.hackery.items;

import guru.haun.hackery.HackeryMod;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;

public class GlitchPowder extends Item{
	
	public GlitchPowder(boolean register) {
		setUnlocalizedName("powderGlitch");
		if(register){
			GameRegistry.registerItem(this, "glitchPowder");
			GameRegistry.addShapelessRecipe(new ItemStack(this, 1,0), new ItemStack(HackeryMod.shardGlitch,1,0), new ItemStack(HackeryMod.shardGlitch,1,0), new ItemStack(HackeryMod.shardGlitch,1,0), new ItemStack(HackeryMod.shardGlitch,1,0));
			registerPotion();
			this.setCreativeTab(HackeryMod.creativetab);
		}
	}
	
	@Override
	public boolean isPotionIngredient(ItemStack is){
		return true;
	}
	
	@Override
    public String getPotionEffect(ItemStack is){
		return HackeryMod.config.gpPotionEff;
	}
	
	public void registerPotion(){
		try{
			Class potHepler = PotionHelper.class;
			Field potFields[] = potHepler.getDeclaredFields();
			Field mods;
			mods = Field.class.getDeclaredField("modifiers");
			mods.setAccessible(true);
			for(Field f : potFields){
				if(f.getName() == "potionRequirements" || f.getName() == "field_77927_l"){
					mods.setInt(f, f.getModifiers() & ~Modifier.FINAL);
					f.setAccessible(true);
					final HashMap<Integer,String> newReq = (HashMap<Integer,String>)((HashMap<Integer,String>)f.get(null)).clone();
					newReq.put(HackeryMod.config.idEffectGlitch, HackeryMod.config.gfPotionReq);
					f.set(null, newReq);
				}else if(false && (f.getName() == "potionAmplifiers" || f.getName() == "field_77928_m")){
					mods.setInt(f, f.getModifiers()& ~Modifier.FINAL);
					f.setAccessible(true);
					final HashMap<Integer,String> newAmp = (HashMap<Integer, String>)((HashMap<Integer,String>)f.get(null)).clone();
					newAmp.put(HackeryMod.config.idEffectGlitch, "5");
					f.set(null, newAmp);
				}
			}
		}catch (Exception e){
			HackeryMod.logger.error("Something went wrong while registering potions!");
			e.printStackTrace();
		}
		
	}

    @Override
    public void getSubItems(Item i, CreativeTabs tab, List l) {
        l.add(new ItemStack(this,1,0));
        l.add(new ItemStack(Items.potionitem,1,9243));
        l.add(new ItemStack(Items.potionitem,1,9307));
        l.add(new ItemStack(Items.potionitem,1,17435));
        l.add(new ItemStack(Items.potionitem,1,17499));
    }


}
