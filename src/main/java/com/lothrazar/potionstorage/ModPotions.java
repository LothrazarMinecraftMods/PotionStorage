package com.lothrazar.potionstorage;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = Const.MODID, useMetadata=true, canBeDeactivated=false		
,  guiFactory ="com.lothrazar."+Const.MODID+".IngameConfigHandler")
public class ModPotions
{
	@Instance(Const.MODID)
	public static ModPotions instance;
	
	@SidedProxy(clientSide = "com.lothrazar.potionstorage.ClientProxy", serverSide = "com.lothrazar.potionstorage.CommonProxy")
	public static CommonProxy proxy;
	public SimpleNetworkWrapper network ;
    public static Configuration config;
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	network = NetworkRegistry.INSTANCE.newSimpleChannel(Const.MODID);
    	 
    	loadConfig(new Configuration(event.getSuggestedConfigurationFile()));

    	int packetID = 0;
    
    	network.registerMessage(PotionButtonPacket.class,PotionButtonPacket.class,  packetID++, Side.SERVER);
 
    	proxy.registerHandlers();
    }
    
	public static boolean persistOnDeath;

	public static boolean showInventoryButton;

	private void loadConfig(Configuration c)
	{
		config = c;
		config.load();
		syncConfig();
	}
	
	public static void syncConfig()
	{
		String category = Configuration.CATEGORY_GENERAL;
				
		PotionButtonPacket.allowMerge = config.getBoolean("allow_merge",category,true,"Allow similar potion effects to merge.  For example, if you have 1 minute of speed in storage, drink another speed potion, the time will get added together if this is true.");
		persistOnDeath = config.getBoolean("persist_death",category,true,"Allow your saved potions to stick with you through death.");
		showInventoryButton = config.getBoolean("show_button",category,true,"Show or hide the inventory button for potions, (you can still use the keybinding).");
		
		if(config.hasChanged()){config.save();}
	}
}
