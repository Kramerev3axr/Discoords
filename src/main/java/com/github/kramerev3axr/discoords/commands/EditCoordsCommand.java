package com.github.kramerev3axr.discoords.commands;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.github.kramerev3axr.discoords.networking.DiscoordsPacketHandler;
import com.github.kramerev3axr.discoords.networking.packet.screens.OpenEditScreenS2CPacket;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;

public class EditCoordsCommand 
{
    public EditCoordsCommand(CommandDispatcher<CommandSourceStack> commandDispatcher) 
    {
        commandDispatcher.register(Commands.literal("coords").then(Commands.literal("edit").executes((command) -> 
        {
            return editCoords(command.getSource());
        })));
        
    }
    
    private int editCoords(CommandSourceStack commandSourceStack) throws CommandSyntaxException
    {
    	ServerPlayer player = commandSourceStack.getPlayer();
    	
    	DiscoordsPacketHandler.sendToPlayer(new OpenEditScreenS2CPacket(getDatabaseList()), player);
//    	player.sendSystemMessage(Component.literal("Command executed successfully!"));
    	return 1;
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
