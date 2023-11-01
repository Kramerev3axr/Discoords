package com.github.kramerev3axr.discoords.screen;

import com.github.kramerev3axr.discoords.networking.DiscoordsPacketHandler;
import com.github.kramerev3axr.discoords.networking.packet.database.DeleteDatabaseC2SPacket;
import com.github.kramerev3axr.discoords.networking.packet.database.EditDatabaseC2SPacket;
import com.github.kramerev3axr.discoords.networking.packet.database.GetDatabaseC2SPacket;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.GameNarrator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class EditCoordsMiniScreen extends Screen
{
	private static EditBox namebox;
	private static String origName;
	private static String pos;
	private static String dim;
	private static int buttonInd;

	public EditCoordsMiniScreen(String origName, String pos, String dim, int buttonInd) 
	{
		super(GameNarrator.NO_TITLE);
		EditCoordsMiniScreen.origName = origName;
		EditCoordsMiniScreen.pos = pos;
		EditCoordsMiniScreen.dim = dim;
		EditCoordsMiniScreen.buttonInd = buttonInd;
	}

	@Override
	protected void init() 
	{
	    super.init();

	    // Add widgets and precomputed values
	    namebox = new EditBox(this.font, this.width / 2 - (176 / 2) + 13, this.height / 2 - (78 / 2) + 29, 150, 20, Component.literal(origName)); // X Y W H STRING
	    namebox.setValue(origName);
	    
	    this.addRenderableWidget(namebox);
	    
	    this.addRenderableWidget(new Button(
	    		this.width / 2 - (176 / 2) + 66, 
	    		this.height / 2 - (78 / 2) + 52, 
	    		50, 
	    		20, 
	    		Component.literal("Apply"), 
	    		(p_212984_1_) -> 
	    		{
	    			if (!namebox.getValue().equals(""))
	    			{
	    				// Make final edits to coords
	    				String editedCoords = pos + ": " + namebox.getValue() + " " + dim;
	    				
	    				// Edit Database
	    				DiscoordsPacketHandler.sendToServer(new EditDatabaseC2SPacket(editedCoords, buttonInd));
	    				
	    				// Reset GUI
	    				DiscoordsPacketHandler.sendToServer(new GetDatabaseC2SPacket("edit"));
	    			}
	    		})); // When Clicked // X Y W H STRING
	    
	      this.addRenderableWidget(new ImageButton(
	    		  this.width / 2 - (176 / 2) + 144, // X
	    		  this.height / 2 - (78 / 2) + 52, // Y
	    		  20,  // Texture H
	    		  20, // Texture W
	    		  0, 
	    		  0, 
	    		  20, 
	    		  new ResourceLocation("discoords", "images/deletebutton.png"), // Texture 
	    		  64, 
	    		  64,
	    		  (p_96784_) -> // Click
	    		  {
	    				// Delete element Database
	    				DiscoordsPacketHandler.sendToServer(new DeleteDatabaseC2SPacket(buttonInd));
	    				
	    				// Reset GUI
	    				DiscoordsPacketHandler.sendToServer(new GetDatabaseC2SPacket("edit"));
	    		  }));
	}
	
	@SuppressWarnings("resource")
	@Override
	public void render(PoseStack pose, int mouseX, int mouseY, float partialTick) 
	{
	    // Background is typically rendered first
	    this.renderBackground(pose);

	    // Render things here before widgets (background textures)
	    RenderSystem.setShaderTexture(0, new ResourceLocation("discoords", "images/discoordgui.png"));
	    blit(pose, this.width / 2 - (176 / 2), this.height / 2 - (78 / 2), 0, 0, 176, 78);
	    
	    // Then the widgets if this is a direct child of the Screen
	    Minecraft.getInstance().font.draw(pose, "Enter New Location Name", this.width / 2 - (176 / 2) + 13, this.height / 2 - (78 / 2) + 16, 0x000000);
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
}
