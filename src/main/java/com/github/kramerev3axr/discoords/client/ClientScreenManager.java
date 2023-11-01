package com.github.kramerev3axr.discoords.client;

import java.util.List;

import com.github.kramerev3axr.discoords.screen.EditCoordsScreen;
import com.github.kramerev3axr.discoords.screen.ShowCoordsScreen;

import net.minecraft.client.Minecraft;

public class ClientScreenManager 
{
	public static void openCoordsScreen(List<String> data) 
	{
		Minecraft.getInstance().setScreen(new ShowCoordsScreen(data));
	}
	
	public static void openEditScreen(List<String> data) 
	{
		Minecraft.getInstance().setScreen(new EditCoordsScreen(data));
	}
}
