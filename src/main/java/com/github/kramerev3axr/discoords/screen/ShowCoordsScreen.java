package com.github.kramerev3axr.discoords.screen;

import java.util.List;

import com.github.kramerev3axr.discoords.Discoords;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.GameNarrator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.PlainTextButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = Discoords.MODID, value = Dist.CLIENT, bus = Bus.MOD)
public class ShowCoordsScreen extends Screen
{
	private static int currentPage = 0;
	
	List<String> database = null;
	
	public ShowCoordsScreen(List<String> database) 
	{
		super(GameNarrator.NO_TITLE);
		this.database = database;
	}
	
	@Override
	protected void init() 
	{
	    super.init();

		renderCoords();
	    renderArrows();
	}
	
	@SuppressWarnings("resource")
	@Override
	public void render(PoseStack pose, int mouseX, int mouseY, float partialTick) 
	{
	    // Background is typically rendered first
	    this.renderBackground(pose);

	    // Render things here before widgets (background textures)
	    RenderSystem.setShaderTexture(0, new ResourceLocation("discoords", "images/discoordsdbguileft.png"));
	    blit(pose, this.width / 3 - (386 / 3), this.height / 2 - (256 / 2), 0, 0, 256, 256); // Left
	    
	    RenderSystem.setShaderTexture(0, new ResourceLocation("discoords", "images/discoordsdbguiright.png"));
	    blit(pose, this.width / 3 - (386 / 3) + 208, this.height / 2 - (256 / 2), 0, 0, 256, 256); // Right
	    
	    // Then the widgets if this is a direct child of the Screen
	    Minecraft.getInstance().font.draw(pose, "Coordinates Database", this.width / 2 - (256 / 4) + 8, this.height / 2 - (256 / 2) + 8, 0x000000);
	    
	    super.render(pose, mouseX, mouseY, partialTick);
	    
	    // Render things after widgets (tooltips)
	}
	
	@Override
	public void onClose() 
	{
	    // Stop any handlers here

	    // Call last in case it interferes with the override
	    super.onClose();
	}

	@Override
	public void removed() 
	{
	    // Reset initial states here

	    // Call last in case it interferes with the override
	    super.removed();
	}
	
	private static void onAccept(Button button)
	{
		
	}
	
	public void renderCoords()
	{
	    int[] pageNums = getPageNums(database.size());
	    
	    for (int i = 0; i < pageNums.length; i++) 
	    {	    	
	    	if (pageNums[i] == currentPage)
	    	{
		    	this.addRenderableOnly(new PlainTextButton(
			    		this.width / 3 - (386 / 3) + 8, // X
			    		this.height / 2 - (256 / 2) + ((i % 15) * 15) + 25, // Y
			    		database.get(i).length() * 5, // Width
			    		10, // Height
			    		Component.literal(database.get(i)), // Text
			    		ShowCoordsScreen::onAccept, // When Clicked 
			    		this.font)); // Font
	    	}
	    	System.out.println(database.get(i) + " | Page #" + pageNums[i]);
	    }
	}
	
	public void renderArrows()
	{
	    this.addRenderableWidget(new Button( // Left Arrow
	    		this.width / 3 - (386 / 3) + 414, // X
	    		this.height / 2 - (256 / 2) - 8, // Y 
	    		20, // Width
	    		20, // Height
	    		Component.literal("<-"), // Text
	    		(p_212984_1_) -> 
	    		{
	    			if (currentPage != 0) // Ceiling floor page #'s
	    			{
	    				currentPage--;
	    			    this.clearWidgets();
	    			    renderCoords();
	    			    renderArrows();
	    			}
	    		})); // When Clicked
	    
	    this.addRenderableWidget(new Button( // Right Arrow
	    		this.width / 3 - (386 / 3) + 439, // X
	    		this.height / 2 - (256 / 2) - 8, // Y 
	    		20, // Width
	    		20, // Height
	    		Component.literal("->"), // Text
	    		(p_212984_1_) -> 
	    		{
	    			if (currentPage  < getPageNums(database.size())[database.size() - 1]) // Ceiling for page #'s
	    			{
	    				currentPage++;
	    			    System.out.println(currentPage);
	    			    this.clearWidgets();
	    			    renderCoords();
	    			    renderArrows();
	    			}
	    		})); // When Clicked
	}
	
	private int[] getPageNums(int dbSize)
	{
		// i do NOT understand how this works just DONT TOUCH IT
		int[] pageAssign = new int[dbSize];
		int pageNum = 0;
		int lastInterval = 0;
		
		for (int i = 0; i < pageAssign.length; i++)
		{
			if (i % 15 == 0 && i != 0)
			{
				for (int k = i; k >= i - 15; k--)
				{
					pageAssign[k] = pageNum;
				}
				pageNum++;
			}
			
			if (i % 15 == 0)
			{
				lastInterval = i;
			}
		}
		
		if (pageAssign.length % 15 != 0) // leftovers
		{
			for (int i = pageAssign.length - 1; i >= lastInterval; i--)
			{
				pageAssign[i] = pageNum;
			}
		}
		
		if (dbSize % 15 == 0) // no leftovers
		{
			for (int k = dbSize - 1; k >= dbSize - 15; k--)
			{
				pageAssign[k] = pageNum;
			}
		}
		
		System.out.println("dbSize: " + dbSize);
		System.out.println("pageAssign.length: " + pageAssign.length);
		return pageAssign;
	}
}
