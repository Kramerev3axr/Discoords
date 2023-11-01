package com.github.kramerev3axr.discoords.networking.packet.database;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.function.Supplier;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

public class AddDatabaseC2SPacket 
{	
	private String finalCoords;
	
	public AddDatabaseC2SPacket(String finalCoords)
	{
		this.finalCoords = finalCoords;
	}
	
	public AddDatabaseC2SPacket(FriendlyByteBuf buf) // Read Data from ByteBuffer
	{
		this.finalCoords = buf.readUtf();
	}
	
	public void toBytes(FriendlyByteBuf buf) // Write Data from ByteBuffer
	{
		buf.writeUtf(finalCoords);
	}
	
	public boolean handle(Supplier<NetworkEvent.Context> supplier)
	{
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE SERVER!
        	
        	// Check if database exists and add Coords
    		File database = new File("coordsDB.txt");
    		
    		try 
    		{
    			database.createNewFile(); // if file already exists will do nothing
    			FileWriter writer = new FileWriter(database, true); // "true" for appending
    			writer.write(finalCoords + System.getProperty("line.separator"));
    			writer.close();
    		} 
    		catch (IOException e1) 
    		{
    			e1.printStackTrace();
    		} 
        });
        return true;
	}
}
