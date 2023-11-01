package com.github.kramerev3axr.discoords.networking.packet.screens;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import com.github.kramerev3axr.discoords.client.ClientScreenManager;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

public class OpenEditScreenS2CPacket
{
	private List<String> data = new ArrayList<>();
	
	public OpenEditScreenS2CPacket(List<String> data)
	{
		this.data = data;
	}
	
	public OpenEditScreenS2CPacket(FriendlyByteBuf buf) // Read Data from ByteBuffer
	{	
		this.data = buf.readList(FriendlyByteBuf::readUtf);
	}
	
	public void toBytes(FriendlyByteBuf buf) // Write Data from ByteBuffer
	{
		buf.writeCollection(data, FriendlyByteBuf::writeUtf);
	}
	
	public void handle(Supplier<NetworkEvent.Context> supplier)
	{
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> 
        {
            // HERE WE ARE ON THE CLIENT!
        	DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientScreenManager.openEditScreen(data));
        	
        });
        context.setPacketHandled(true);
	}
}
