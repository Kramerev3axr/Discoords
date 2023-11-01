package com.github.kramerev3axr.discoords.util;

import org.lwjgl.glfw.GLFW;

import com.mojang.blaze3d.platform.InputConstants;

import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;

public class Keybinding 
{
	public static final String KEY_CATEGORY_DISCOORDS = "key.category.discoords.coords";
	public static final String KEY_ADD_COORD = "key.discoords.add_coords";
	public static final String KEY_SHOW_COORD = "key.discoords.show_coords"; 
	
	public static final KeyMapping ADD_COORD_KEY = new KeyMapping(KEY_ADD_COORD, 
			KeyConflictContext.IN_GAME, 
			InputConstants.Type.KEYSYM, 
			GLFW.GLFW_KEY_UNKNOWN, // Unbound 
			KEY_CATEGORY_DISCOORDS);
	
	public static final KeyMapping SHOW_COORD_KEY = new KeyMapping(KEY_SHOW_COORD, 
			KeyConflictContext.IN_GAME, 
			InputConstants.Type.KEYSYM, 
			GLFW.GLFW_KEY_UNKNOWN, // Unbound 
			KEY_CATEGORY_DISCOORDS);
}
