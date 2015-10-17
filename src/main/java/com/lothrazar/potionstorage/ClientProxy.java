package com.lothrazar.potionstorage;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import org.lwjgl.input.Keyboard; 
 
public class ClientProxy extends CommonProxy
{
	public static KeyBinding keyPotions;   
 
	public static final String keyCategory = "key.categories.inventory";
 
	@Override
	public void registerHandlers()
	{
		super.registerHandlers();
		
		keyPotions = new KeyBinding("key.potions", Keyboard.KEY_Y, keyCategory); 
        ClientRegistry.registerKeyBinding(ClientProxy.keyPotions);
         
	}
}
