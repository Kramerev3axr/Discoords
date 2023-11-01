# Discoords
### Overview
**Discoords** is a Minecraft: Java Edition mod for Forge 1.19.2 that allows you to set keybinds to put your current coordinates and dimension into a database (& optionally a specified discord channel via a bot) that can be viewed from within Minecraft. 

If the command `/coords edit` is used, you gain access to edit mode, where you can edit the names of your stored locations, as well as delete them.

This mod wasn't made for general use, and is specifically used for my needs, so it will not be maintained for newer versions and will not be ported to fabric. However, if you want to do either of these things yourself go ahead, I don't mind.

### Config
The config file is stored server-side, meaning it will be located in `/world/serverconfig/discoords-server.toml`.

The  `Channel_ID` and `Bot_Token` values should be self-explanatory for what you need, and if you don't want the mod to output your locations to a Discord channel at all, just leave one or both blank.

### Contributing
This is my first mod for Minecraft, so if you see issues with my code or know of any easier ways to accomplish certain tasks, please let me know!