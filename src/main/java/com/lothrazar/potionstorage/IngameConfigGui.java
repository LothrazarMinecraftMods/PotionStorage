package com.lothrazar.potionstorage;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;

public class IngameConfigGui extends GuiConfig 
{
	//thanks to the http://jabelarminecraft.blogspot.ca/p/minecraft-modding-configuration-guis.html
	public IngameConfigGui(GuiScreen parent) 
    {
        super(parent, new ConfigElement(
        		ModPotions.config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(),
                Const.MODID, 
                false, 
                false, 
                "PotionStorage");
    }
	
	@Override
    public void initGui()
    {
        super.initGui();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(GuiButton button)
    {
        super.actionPerformed(button);
    }
}
