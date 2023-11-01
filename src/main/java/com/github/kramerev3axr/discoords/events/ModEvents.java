package com.github.kramerev3axr.discoords.events;

import com.github.kramerev3axr.discoords.Discoords;
import com.github.kramerev3axr.discoords.commands.EditCoordsCommand;

import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;

@Mod.EventBusSubscriber(modid = Discoords.MODID)
public class ModEvents {

    @SubscribeEvent
    public static void onCommandsRegister(RegisterCommandsEvent event) 
    {
        new EditCoordsCommand(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());
    }
}