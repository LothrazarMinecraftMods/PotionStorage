package com.lothrazar.potionstorage;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PotionButtonPacket implements IMessage , IMessageHandler<PotionButtonPacket, IMessage>
{
	public PotionButtonPacket() {}
	NBTTagCompound tags = new NBTTagCompound(); 
	
	public PotionButtonPacket(NBTTagCompound ptags)
	{
		tags = ptags;
	}

	@Override
	public void fromBytes(ByteBuf buf) 
	{
		tags = ByteBufUtils.readTag(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) 
	{
		ByteBufUtils.writeTag(buf, this.tags);
	}

	public static boolean allowMerge = true;
	@Override
	public IMessage onMessage(PotionButtonPacket message, MessageContext ctx)
	{
		EntityPlayer p = ctx.getServerHandler().playerEntity;
		
		//encode potions as csv, store in metadata for player and clear them

		InventoryPersistProperty storage = InventoryPersistProperty.get(p);
		
		if(storage.countPotionEffects() == 0)
		{
			storage.savePotionEffects();
	        p.clearActivePotions();
		}
		else
		{
			for(PotionEffect pot : storage.getSavedPotionEffects())
			{
				//make a copy
				
				//wait dont we want to merge
				
				//
				if(allowMerge)
					addOrMergePotionEffect(p, pot);
				else
					p.addPotionEffect(new PotionEffect(pot));
			}
		}
        
       // p.closeScreen();
        
		return null;
	}
	
	//copied  from my PowerPApples mod
	public static void addOrMergePotionEffect(EntityLivingBase player, PotionEffect newp)
	{
		//only merge if they are the same level
		if(player.isPotionActive(newp.getPotionID()))
		{
			//do not use built in 'combine' function, just add up duration myself
			PotionEffect p = player.getActivePotionEffect(Potion.potionTypes[newp.getPotionID()]);
			
			if(p.getAmplifier() != newp.getAmplifier())				
			{
				//for example, if you take speedII at 1:30, and speed I at 3:00
				//then take the maxtime and the min level
				

				int ampMin = Math.min(p.getAmplifier(), newp.getAmplifier());
				int timeMax = Math.max(p.getDuration(), newp.getDuration());

				player.addPotionEffect(new PotionEffect(newp.getPotionID()
						,timeMax
						,ampMin));
			}
			else
			{
				
				player.addPotionEffect(new PotionEffect(newp.getPotionID()
						,newp.getDuration() + p.getDuration()
						,p.getAmplifier()));
			}
		}
		else
		{
			player.addPotionEffect(new PotionEffect(newp));
		}
	}
}
