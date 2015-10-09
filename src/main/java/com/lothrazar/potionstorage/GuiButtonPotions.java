package com.lothrazar.potionstorage;
 
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GuiButtonPotions extends GuiButton 
{
    public GuiButtonPotions(int buttonId, int x, int y, int w,int h)
    {
    	super(buttonId, x, y, w,h, StatCollector.translateToLocal("button.potion"));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY)
    {
    	boolean pressed = super.mousePressed(mc, mouseX, mouseY);
    	
    	if(pressed)
    	{ 	
    		ModPotions.instance.network.sendToServer(new PotionButtonPacket(new NBTTagCompound()));
    	}
    	
    	return pressed;
    }
}

