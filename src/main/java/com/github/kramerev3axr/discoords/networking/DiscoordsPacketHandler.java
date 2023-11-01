package com.github.kramerev3axr.discoords.networking;

import com.github.kramerev3axr.discoords.Discoords;
import com.github.kramerev3axr.discoords.networking.packet.database.*;
import com.github.kramerev3axr.discoords.networking.packet.screens.*;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class DiscoordsPacketHandler 
{
	private static SimpleChannel INSTANCE = null;
	
	private static int packetID = 0;
	private static int id() 
	{
		return packetID++; // Assigns an ID to every packet for us
	}
	
	public static void register()
	{
		SimpleChannel net = NetworkRegistry.ChannelBuilder
				.named(new ResourceLocation(Discoords.MODID, "messages"))
				.networkProtocolVersion(() -> "1.0")
				.clientAcceptedVersions(s -> true)
				.serverAcceptedVersions(s -> true)
				.simpleChannel();
		
		INSTANCE = net;
		
		// Register Packets
		net.messageBuilder(GetDatabaseC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
		.decoder(GetDatabaseC2SPacket::new)
		.encoder(GetDatabaseC2SPacket::toBytes)
		.consumerMainThread(GetDatabaseC2SPacket::handle)
		.add();
		
		net.messageBuilder(AddDatabaseC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
		.decoder(AddDatabaseC2SPacket::new)
		.encoder(AddDatabaseC2SPacket::toBytes)
		.consumerMainThread(AddDatabaseC2SPacket::handle)
		.add();
		
		net.messageBuilder(EditDatabaseC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
		.decoder(EditDatabaseC2SPacket::new)
		.encoder(EditDatabaseC2SPacket::toBytes)
		.consumerMainThread(EditDatabaseC2SPacket::handle)
		.add();
		
		net.messageBuilder(DeleteDatabaseC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
		.decoder(DeleteDatabaseC2SPacket::new)
		.encoder(DeleteDatabaseC2SPacket::toBytes)
		.consumerMainThread(DeleteDatabaseC2SPacket::handle)
		.add();
		
        net.messageBuilder(OpenCoordsScreenS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
        .decoder(OpenCoordsScreenS2CPacket::new)
        .encoder(OpenCoordsScreenS2CPacket::toBytes)
        .consumerMainThread(OpenCoordsScreenS2CPacket::handle)
        .add();
        
        net.messageBuilder(OpenEditScreenS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
        .decoder(OpenEditScreenS2CPacket::new)
        .encoder(OpenEditScreenS2CPacket::toBytes)
        .consumerMainThread(OpenEditScreenS2CPacket::handle)
        .add();
	}
	
	public static <MSG> void sendToServer(MSG message)
	{
		INSTANCE.sendToServer(message);
	}
	
	public static <MSG> void sendToPlayer(MSG message, ServerPlayer player)
	{
		INSTANCE.send(PacketDistributor.PLAYER.with(()-> player), message);
	}
}
