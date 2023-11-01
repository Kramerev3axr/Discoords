package com.github.kramerev3axr.discoords.events;

import com.github.kramerev3axr.discoords.Discoords;
import com.github.kramerev3axr.discoords.client.CoordsHudOverlay;
import com.github.kramerev3axr.discoords.networking.DiscoordsPacketHandler;
import com.github.kramerev3axr.discoords.networking.packet.database.GetDatabaseC2SPacket;
import com.github.kramerev3axr.discoords.screen.AddCoordsScreen;
import com.github.kramerev3axr.discoords.util.Keybinding;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ClientEvents 
{
	@Mod.EventBusSubscriber(modid = Discoords.MODID, value = Dist.CLIENT)
	public static class ClientForgeEvents
	{
		@SubscribeEvent
		public static void onKeyInput(InputEvent.Key event)
		{
			if (Keybinding.ADD_COORD_KEY.consumeClick())
			{
				
				Minecraft.getInstance().setScreen(new AddCoordsScreen());
			}
			
			if (Keybinding.SHOW_COORD_KEY.consumeClick())
			{
				DiscoordsPacketHandler.sendToServer(new GetDatabaseC2SPacket("coords"));
//				Minecraft.getInstance().setScreen(new ShowCoordsScreen());
			}
		}
	}
	
	@Mod.EventBusSubscriber(modid = Discoords.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
	public static class ClientModBusEvents
	{
		@SubscribeEvent
		public static void onKeyRegister(RegisterKeyMappingsEvent event)
		{
			event.register(Keybinding.ADD_COORD_KEY);
			event.register(Keybinding.SHOW_COORD_KEY);
		}
		
		@SubscribeEvent
		public static void registerGuiOverlays(RegisterGuiOverlaysEvent event)
		{
			event.registerAboveAll("coords", CoordsHudOverlay.HUD_COORDS);
		}
	}
}
