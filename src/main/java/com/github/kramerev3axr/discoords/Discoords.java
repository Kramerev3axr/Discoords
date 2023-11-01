package com.github.kramerev3axr.discoords;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.kramerev3axr.discoords.config.DiscoordsCommonConfigs;
import com.github.kramerev3axr.discoords.networking.DiscoordsPacketHandler;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Discoords.MODID)
public final class Discoords 
{
	public static final String MODID = "discoords";
	
	private static final Logger LOGGER = LogManager.getLogger();

	public Discoords() 
	{
		LOGGER.debug("Mod up");
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		
		modEventBus.addListener(this::commonSetup);
		
		ModLoadingContext.get().registerConfig(Type.SERVER, DiscoordsCommonConfigs.SPEC, "discoords-server.toml");
		
        MinecraftForge.EVENT_BUS.register(this);
	}
	
	private void commonSetup(final FMLCommonSetupEvent event)
	{
        event.enqueueWork(() -> {});
        DiscoordsPacketHandler.register();
	}
	
	 @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
	 public static class ClientModEvents 
	 {
	     @SubscribeEvent
	     public static void onClientSetup(FMLClientSetupEvent event) 
	     {
	     
	     }
	 }
}
