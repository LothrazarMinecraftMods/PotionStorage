package com.lothrazar.potionstorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import net.minecraftforge.common.util.Constants;

public class InventoryPersistProperty implements IExtendedEntityProperties
{
	public static final String ID = "POTIONSTORAGE";
	
	EntityPlayer player;

	private ArrayList<NBTTagCompound> potions;

	public static void register(EntityPlayer player)
	{
		player.registerExtendedProperties(ID, new InventoryPersistProperty(player));
	}
	
	public static InventoryPersistProperty get(EntityPlayer player)
	{
		IExtendedEntityProperties property = player.getExtendedProperties(ID);
		
		if(property != null && property instanceof InventoryPersistProperty)
		{
			return (InventoryPersistProperty)property;
		} else
		{
			return null;
		}
	}
	
	public InventoryPersistProperty(EntityPlayer player)
	{
		this.player = player;
	}

	
	@Override
	public void loadNBTData(NBTTagCompound compound)
	{

		
		NBTTagList taglist = compound.getTagList("potions",Constants.NBT.TAG_COMPOUND);
		potions = new ArrayList<NBTTagCompound>();
		for (int i = 0; i < taglist.tagCount(); ++i)
		{
			NBTTagCompound tags = taglist.getCompoundTagAt(i);//tagAt
		
			potions.add(tags);
		}
	}
	
	@Override
	public void init(Entity entity, World world)
	{
		if(entity instanceof EntityPlayer)
		{
			this.player = (EntityPlayer)entity;
		}
	}

	@Override
	public void saveNBTData(NBTTagCompound properties) 
	{
	
		NBTTagList nbttaglist = new NBTTagList();
		
		int i = 0;
		for(NBTTagCompound pot : potions)
		{
			nbttaglist.appendTag(pot);
			i++;
		}
		properties.setTag("potions", nbttaglist);
	}
	

	public int countPotionEffects()
	{
		return potions == null ? 0 : potions.size();
	}

	public ArrayList<PotionEffect> getSavedPotionEffects()
	{
		PotionEffect pot;
		ArrayList<PotionEffect> pots = new ArrayList<PotionEffect>();
		for(NBTTagCompound tag : potions)
		{
			
			pot = PotionEffect.readCustomPotionEffectFromNBT(tag);
			
			if(pot != null)
			{
				pots.add(pot);
			}
			//else System.out.println("ERROR null pot from tag");
			
		}
		
		potions = new ArrayList<NBTTagCompound>();
		return pots;
	}
	
	
	public void savePotionEffects()
	{
		// Collection collection = p.getActivePotionEffects();
		//ArrayList<PotionEffect> active = (ArrayList<PotionEffect>)p.getActivePotionEffects();
        PotionEffect potioneffect;
        Iterator iterator = this.player.getActivePotionEffects().iterator();
        
       // ArrayList<String> encodedPotions = new ArrayList<String> ();
        String s;
        
        potions = new ArrayList<NBTTagCompound>();
        NBTTagCompound tags;
        
        while (iterator.hasNext())
        {
            potioneffect = (PotionEffect)iterator.next();
            
            s = potioneffect.getPotionID() +","+potioneffect.getAmplifier()+","+potioneffect.getDuration();

            tags = new NBTTagCompound();
            
            if(potioneffect.getDuration()*20 > 10)
            	potioneffect.writeCustomPotionEffectToNBT(tags);
            //else it is shorter than 10 seconds. might be from a beacon
            //do not save

            potions.add(tags);
        }
	}
	
}
