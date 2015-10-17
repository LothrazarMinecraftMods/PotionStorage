package com.lothrazar.potionstorage;
  
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EventHandler
{
	@SubscribeEvent
	public void onEntityConstruct(EntityConstructing event) // More reliable than on entity join
	{
		if(event.entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)event.entity;
			
			if(InventoryPersistProperty.get(player) == null)
			{
				InventoryPersistProperty.register(player);
			}
		}
	}

	@SubscribeEvent
	public void onConfigChanged(OnConfigChangedEvent event) 
	{
		if (event.modID.equals(Const.MODID)) ModPotions.syncConfig();
	}
	
	@SubscribeEvent
	public void onClonePlayer(PlayerEvent.Clone event) 
	{ 
		if(event.wasDeath)//false means switched dimensions
		{
			if(ModPotions.persistOnDeath)
			{
				InventoryPersistProperty odata = InventoryPersistProperty.get(event.original);
				
				InventoryPersistProperty.get(event.entityPlayer).setPotionNBT(odata.getPotionNBT());
			}
		}
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onGuiPostInit(InitGuiEvent.Post event)
	{
		if(event.gui == null){return;}//probably doesnt ever happen
		if(ModPotions.showInventoryButton == false){return;}//is hidden
		int button_id = 256;

		int padding = 10;
		
		int x,y = padding,w = 80,h = 20;

		if(event.gui instanceof net.minecraft.client.gui.inventory.GuiInventory)
		{
			//x = Minecraft.getMinecraft().displayWidth/2 - w - padding;//align to right side
			//TODO: config to swap
			
			//this is left side
			x = padding;
			
			event.buttonList.add(new GuiButtonPotions(button_id++, x,y,w,h));
		}
	}
	
	@SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) 
    {   
        if(ClientProxy.keyPotions.isPressed() )
        { 	     
        	ModPotions.instance.network.sendToServer( new PotionButtonPacket(new NBTTagCompound()));   
        }  
    }
}
