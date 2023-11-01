package com.github.kramerev3axr.discoords.screen;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import com.github.kramerev3axr.discoords.config.DiscoordsCommonConfigs;
import com.github.kramerev3axr.discoords.networking.DiscoordsPacketHandler;
import com.github.kramerev3axr.discoords.networking.packet.database.AddDatabaseC2SPacket;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.GameNarrator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class AddCoordsScreen extends Screen
{
	private static EditBox namebox;

	public AddCoordsScreen() 
	{
		super(GameNarrator.NO_TITLE);
	}

	@Override
	protected void init() 
	{
	    super.init();

	    // Add widgets and precomputed values
	    namebox = new EditBox(this.font, this.width / 2 - (176 / 2) + 13, this.height / 2 - (78 / 2) + 29, 150, 20, Component.literal("")); // X Y W H STRING
	    this.addRenderableWidget(namebox);
	    
	    this.addRenderableWidget(new Button(
	    		this.width / 2 - (176 / 2) + 66, 
	    		this.height / 2 - (78 / 2) + 52, 
	    		50, 
	    		20, 
	    		Component.literal("Add"), 
	    		(p_212984_1_) -> 
	    		{
	    			if (!namebox.getValue().equals(""))
	    			{
	    				onAccept();
	    			}
	    		})); // When Clicked // X Y W H STRING
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
	    Minecraft.getInstance().font.draw(pose, "Enter Location Name", this.width / 2 - (176 / 2) + 13, this.height / 2 - (78 / 2) + 16, 0x000000);
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
	
	private static void onAccept()
	{
		@SuppressWarnings("resource")
		Player player = Minecraft.getInstance().player;
		
		BlockPos playerPos = player.blockPosition();
		String pos = playerPos.getX() + ", " + playerPos.getY() + ", " + playerPos.getZ();
		
		ResourceKey<Level> dimKey = player.level.dimension(); // [minecraft:dimension / minecraft:overworld]
		String dim = dimKey.location().getPath(); // extract "overworld"
		
		if (dim.substring(0,4).equals("the_")) // Remove "the_"
		{
			dim = dim.substring(4);
		}	
		
		dim = dim.substring(0,1).toUpperCase()  + dim.substring(1); // Capitalize First Letter
		dim = "(" + dim + ")"; // Add "()"
		
		String locName = namebox.getValue();
		
		String finalCoords = pos + ": " + locName + " " + dim;
		
		// Client -> Database.txt
		// Send finalCoords to addDatabaseC2SPacket
		DiscoordsPacketHandler.sendToServer(new AddDatabaseC2SPacket(finalCoords));
		
		// Client -> Discord
		String channelID = DiscoordsCommonConfigs.DISCORD_CHANNEL_ID.get();
		String botToken = DiscoordsCommonConfigs.DISCORD_BOT_TOKEN.get();
		
		URL apiURL;
		try 
		{
			apiURL = new URL("https://discordapp.com/api/channels/" + channelID + "/messages");
			
			HttpURLConnection connection = (HttpURLConnection) apiURL.openConnection(); // API POST Request
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Authorization", "Bot " + botToken);
			
			String payload = "{\"content\":\"" + finalCoords + "\"}";
			
	        byte[] out = payload.getBytes(StandardCharsets.UTF_8);
	        OutputStream stream = connection.getOutputStream();
	        stream.write(out);
	        System.out.println(connection.getResponseCode() + " " + connection.getResponseMessage()); // This is optional
	        connection.disconnect();
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Minecraft.getInstance().setScreen(null); // Close Screen
	}
}
