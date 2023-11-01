package com.github.kramerev3axr.discoords.networking.packet.database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import com.github.kramerev3axr.discoords.networking.DiscoordsPacketHandler;
import com.github.kramerev3axr.discoords.networking.packet.screens.OpenCoordsScreenS2CPacket;
import com.github.kramerev3axr.discoords.networking.packet.screens.OpenEditScreenS2CPacket;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

public class GetDatabaseC2SPacket 
{	
	private String screen;
	
	public GetDatabaseC2SPacket(String screen)
	{
		this.screen = screen;
	}
	
	public GetDatabaseC2SPacket(FriendlyByteBuf buf) // Read Data from ByteBuffer
	{
		this.screen = buf.readUtf();
	}
	
	public void toBytes(FriendlyByteBuf buf) // Write Data from ByteBuffer
	{
		buf.writeUtf(screen);
	}
	
	public void handle(Supplier<NetworkEvent.Context> supplier)
	{
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> 
        {
            // HERE WE ARE ON THE SERVER!
        	if (screen.equals("coords")) 
        	{
           		DiscoordsPacketHandler.sendToPlayer(new OpenCoordsScreenS2CPacket(getDatabaseList()), context.getSender());
        	}
        	
        	if (screen.equals("edit")) 
        	{
        		DiscoordsPacketHandler.sendToPlayer(new OpenEditScreenS2CPacket(getDatabaseList()), context.getSender());
        	}
        });
        context.setPacketHandled(true);
	}

	private List<String> getDatabaseList() 
	{
		List<String> data = new ArrayList<>();
    	// Check if database exists & Grab Database Entries
		File database = new File("coordsDB.txt");
		
		System.out.println("Accessed database at: " + database.getAbsolutePath());
		
		try 
		{
			database.createNewFile(); // If file already exists does nothing
			BufferedReader reader = new BufferedReader(new FileReader(database));
			
			String line = reader.readLine();
			while (line != null)
			{
				data.add(line);
				line = reader.readLine(); // Read next line
			}
			reader.close();
		} 
		catch (IOException e1) 
		{
			e1.printStackTrace();
		} 
    	
    	// Put Database entries into ClientDatabase.java
		System.out.println(data);
		
		return data;
	}
}
