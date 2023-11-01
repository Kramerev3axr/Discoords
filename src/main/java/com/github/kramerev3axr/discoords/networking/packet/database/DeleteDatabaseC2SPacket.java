package com.github.kramerev3axr.discoords.networking.packet.database;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Supplier;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

public class DeleteDatabaseC2SPacket 
{	
	private int line;
	
	public DeleteDatabaseC2SPacket(int line)
	{
		this.line = line;
	}
	
	public DeleteDatabaseC2SPacket(FriendlyByteBuf buf) // Read Data from ByteBuffer
	{
		this.line = buf.readInt();
	}
	
	public void toBytes(FriendlyByteBuf buf) // Write Data from ByteBuffer
	{
		buf.writeInt(line);
	}
	
	public boolean handle(Supplier<NetworkEvent.Context> supplier)
	{
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // HERE WE ARE ON THE SERVER!
            try 
            {	
                Path path = Paths.get("coordsDB.txt");
                List<String> database = Files.readAllLines(path, StandardCharsets.UTF_8);
                database.remove(line); // Deletes specified index
				Files.write(path, database, StandardCharsets.UTF_8);
			} 
            catch (IOException e)
            {
				e.printStackTrace();
			}
        });
        return true;
	}
}
