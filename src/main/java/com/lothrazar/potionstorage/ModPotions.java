package com.lothrazar.potionstorage;

import org.apache.logging.log4j.Logger;
 
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = Const.MODID, useMetadata=true, canBeDeactivated=false		)
public class ModPotions
{
	@Instance(Const.MODID)
	public static ModPotions instance;
	
	@SidedProxy(clientSide = "com.lothrazar.potionstorage.ClientProxy", serverSide = "com.lothrazar.potionstorage.CommonProxy")
	public static CommonProxy proxy;
	public SimpleNetworkWrapper network ;
	public static Logger logger;
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	logger = event.getModLog();
    	network = NetworkRegistry.INSTANCE.newSimpleChannel(Const.MODID);
    	 
    	//ModConfig.loadConfig(new Configuration(event.getSuggestedConfigurationFile()));

    	int packetID = 0;
    
    	network.registerMessage(PotionButtonPacket.class,PotionButtonPacket.class,  packetID++, Side.SERVER);
    	
    	proxy.registerHandlers();
    }
}
