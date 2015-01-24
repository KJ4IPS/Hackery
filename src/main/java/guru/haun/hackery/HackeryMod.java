package guru.haun.hackery;

import guru.haun.hackery.api.exploits.ExploitUtils;
import guru.haun.hackery.blocks.*;
import guru.haun.hackery.blocks.gui.GuiHandler;
import guru.haun.hackery.enchant.*;
import guru.haun.hackery.exploits.*;
import guru.haun.hackery.items.ExploitItem;
import guru.haun.hackery.items.GlitchHarvester;
import guru.haun.hackery.items.GlitchPowder;
import guru.haun.hackery.items.GlitchShard;
import guru.haun.hackery.material.GlitchMaterial;
import guru.haun.hackery.potion.HackPotions;
import guru.haun.hackery.worldgen.GlitchPlacer;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.FMLEmbeddedChannel;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.Logger;

import java.util.EnumMap;


//@Mod(modid="hackery", name="Hackers", version="modjam")
public class HackeryMod {
	
	@Instance("hackery")
	public static HackeryMod instance;
	
	@SidedProxy(clientSide = "guru.haun.hackery.client.HackeryClientProxy", serverSide = "guru.haun.hackery.HackeryCommonProxy")
	public static HackeryCommonProxy proxy;
	
	public static Logger logger;
	
	public static EnumMap<Side,FMLEmbeddedChannel> channel;
	
	//Glitch Blocks
	public static GlitchMaterial		matGlitch			= new GlitchMaterial(MapColor.purpleColor);
	public static GlitchBlock 			blockGlitch			= new GlitchBlock(matGlitch);
	public static GlitchCoreBlock		blockGlitchCore		= new GlitchCoreBlock(matGlitch);
	
	public static TraceAnalyzerBlock	blockTraceAnalyzer	= new TraceAnalyzerBlock(Material.iron);
	public static GuildedRedstoneBlock	blockGuildedRedstone = new GuildedRedstoneBlock();
	public static EmeraldGlassBlock		blockEmeraldGlass	= new EmeraldGlassBlock();
	
	//Items
	public static GlitchHarvester		glitchHarvester		= new GlitchHarvester();
	public static ExploitItem			itemExploit			= new ExploitItem();
	public static GlitchShard			shardGlitch			= new GlitchShard();
	public static GlitchPowder			powderGlitch;
    public static InfectedEnchant       enchantVirus;
    public static ContagionEnchant      enchantSpread;
    public static QuestEnchantment      enchantQuest;

    public static HackeryConfig         config;

		
	
	public static CreativeTabs creativetab = new CreativeTabs("tabHackery") {
		@Override
		public Item getTabIconItem() {
			return Item.getItemFromBlock(HackeryMod.blockGlitchCore);
		}
	};
	

	@EventHandler
	public void HackeryPreInit(FMLPreInitializationEvent e){
		
		logger = e.getModLog();

        config = new HackeryConfig(e.getSuggestedConfigurationFile());

		proxy.registerRenderThings();
		proxy.registerKeyBinds();
		
		channel = NetworkRegistry.INSTANCE.newChannel("hackery", new HacketHandler());

		enchantVirus = new InfectedEnchant(config.idEnchInfect,new ResourceLocation("infected"),0,EnumEnchantmentType.ALL);
        enchantSpread = new ContagionEnchant(config.idEnchSpread,new ResourceLocation("contagous"),0,EnumEnchantmentType.ALL);
        enchantQuest = new QuestEnchantment(config.idEnchQuest,new ResourceLocation("questing"),0, EnumEnchantmentType.ALL);
        MinecraftForge.EVENT_BUS.register(new InfectedEnchantHandler());
        MinecraftForge.EVENT_BUS.register(new ContagionEnchantHandler());
        MinecraftForge.EVENT_BUS.register(new QuestEnchantHandler());
        proxy.registerServerCommands();

		
		GameRegistry	.registerBlock(blockGlitchCore, "blockGlitchCore");
		GameRegistry	.registerBlock(blockGlitch, "blockGlitch");

		blockGlitchCore	.setCreativeTab(creativetab);
		blockGlitch		.setCreativeTab(creativetab);

		GameRegistry	.registerBlock(blockGuildedRedstone, "guildedRedstone");
		blockGuildedRedstone.registerRecipie();
		blockGuildedRedstone.setCreativeTab(creativetab);
		GameRegistry	.registerBlock(blockEmeraldGlass, "emeraldGlass");
		blockEmeraldGlass.registerRecipie();
		blockEmeraldGlass.setCreativeTab(creativetab);

		GameRegistry	.registerBlock(blockTraceAnalyzer,"traceAnalyzer");
		blockTraceAnalyzer.setCreativeTab(creativetab);
		blockTraceAnalyzer.registerRecipie();
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());

		GameRegistry	.registerTileEntity(TraceAnalyzerTE.class, "tileEntitiyTraceAnalyzer");
		
		GameRegistry	.registerWorldGenerator(new GlitchPlacer(), 0);

		GameRegistry.registerItem(glitchHarvester, "glitchHarvester");
			glitchHarvester.registerRecipie();
		glitchHarvester	.setCreativeTab(creativetab);
		
		ExploitUtils.addExploit(new ExploitRandomTp(0));
		ExploitUtils.addExploit(new ExploitCrash(1));
        ExploitUtils.addExploit(new ExploitHealthAdjust(2));
        ExploitUtils.addExploit(new ExploitMailBomb(3));
        ExploitUtils.addExploit(new ExploitInfection(4));
        ExploitUtils.addExploit(new ExploitContagion(5));
        ExploitUtils.addExploit(new ExploitQuest(6));
		GameRegistry	.registerItem(itemExploit, "itemExploit");
		itemExploit.setCreativeTab(creativetab);
		
		GameRegistry.registerItem(shardGlitch, "glitchShard");
		shardGlitch.setCreativeTab(creativetab);
		
		powderGlitch = new GlitchPowder(true);
		

		HackPotions.register();
		
	}
	
	
	@EventHandler
	public void HackeryInit(FMLInitializationEvent e){
	}
	
	
	@EventHandler
	public void HackeryPostInit(FMLPostInitializationEvent e){
		
	}
	
	
}
