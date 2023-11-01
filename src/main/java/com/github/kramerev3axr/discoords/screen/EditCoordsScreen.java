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
public class EditCoordsScreen extends Screen
{
	private static int currentPage = 0;
	
	List<String> database = null;
	
	public EditCoordsScreen(List<String> database) 
	{
		super(GameNarrator.NO_TITLE);
		this.database = database;
	}
	
	@Override
	protected void init() 
	{
	    super.init();

	    int[] pageNums = getPageNums(database.size());
	    
	    for (int i = 0; i < pageNums.length; i++) 
	    {	    	
	    	if (pageNums[i] == currentPage)
	    	{
	    		
		    	this.addRenderableWidget(new PlainTextButton(
			    		this.width / 3 - (386 / 3) + 8, // X
			    		this.height / 2 - (256 / 2) + ((i % 15) * 15) + 25, // Y
			    		database.get(i).length() * 5, // Width
			    		10, // Height
			    		Component.literal(database.get(i)), // Text
			    		(p_212984_1_) ->  // When Clicked  
			    		{
			    			onAccept(p_212984_1_);
			    		}, 
			    		this.font)); // Font
	    	}
//	    	System.out.println(database.get(i) + " | Page #" + pageNums[i]);
	    }
	    
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
	    			    init();
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
	    			    this.clearWidgets();
	    			    init();
	    			}
	    		})); // When Clicked
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
	    Minecraft.getInstance().font.draw(pose, "Edit Coordinates Database", this.width / 2 - (256 / 4) + 4, this.height / 2 - (256 / 2) + 8, 0x000000);
	    
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
	
	private void onAccept(Button button)
	{
		String message = button.getMessage().toString();
		message = message.substring(8, message.length() - 1);
		
		int buttonInd = 0;
		for (int i = 0; i < database.size(); i++)
		{
			if (database.get(i).equals(message))
			{
				buttonInd = i;
			}
		}
		
		// Seperate POS DIM and Name
		int posEnd = 0;
		int dimBeg = 0;
		
		for (int i = 0; i < message.length(); i++)
		{
			if (message.charAt(i) == ':')
			{
				posEnd = i;
			}
			
			if (message.charAt(i) == '(')
			{
				dimBeg = i;
			}
		}
		
		String pos = message.substring(0, posEnd);
		String dim = message.substring(dimBeg);
		String name = message.substring(posEnd + 2, dimBeg - 1 ); // +2 and -1 are to get rid of the spaces
		
		// Send all this info to the editing gui
		Minecraft.getInstance().setScreen(new EditCoordsMiniScreen(name, pos, dim, buttonInd));
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
		return pageAssign;
	}
}
