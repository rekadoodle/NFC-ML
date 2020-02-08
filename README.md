# NFC-ML
A WIP rewrite of Zowja's mod [New Frontier Craft](http://newfrontiercraft.net/) for ModLoader. It's not ready for survival yet.

## Features
- [x] Ores
- [ ] Ore Generation
- [x] Stone Blocks
- [ ] Crafting/Smelting Recipes
- [x] Ingots
- [ ] Metal Blocks
- [x] Tools & Armor
- [ ] Vanilla tools re-balance
- [ ] Textures
- [x] Stone Blocks
- [x] Windows
- [x] Slabs/Stairs
- [x] Brick Oven
- [ ] Brick Oven HMI support
- [ ] Telescope
- [ ] Wrench
- [ ] Music Discs
- [ ] Oil
- [ ] Writable Books
- [ ] Multiplayer Support

#### Changes from NFC
- Block ids are completely scrambled, many blocks share ids
- Stone block names have been made more specific
- Ingots and basic items all share a single id
- Slab/Stair placing is a bit different (WIP)

## Client Installation
Follow this if you just want to play the mod. There are no releases yet, but you can play the live development version.
1. Install [ModLoader](https://mcarchive.net/mods/modloader?gvsn=b1.7.3) 
2. Install [Forge](https://mcarchive.net/mods/minecraftforge?gvsn=b1.7.3)
3. Download the [master branch](https://github.com/rekadoodle/NFC-ML/archive/master.zip) and extract it into your .minecraft/mods folder

## Development Setup
1. Setup an MCP workspace with Forge
   1. Download v4.3 of the [Mod Coder Pack](https://minecraft.gamepedia.com/Programs_and_editors/Mod_Coder_Pack#Downloads)
   2. Open /runtime/decompile.py with a text editor of your choice and remove line 15 `commands.checkupdates()`
   3. Follow the steps within /docs/README-MCP.TXT
   4. Download [Forge Source](https://mcarchive.net/mods/minecraftforge?gvsn=b1.7.3) and follow the steps in the README.TXT
   5. Download [mine_diver's Fixed Forge patch](http://www.mediafire.com/file/kmf47og8hf1c7ib) and extract it into the root of your MCP folder.
2. Download the [master branch](https://github.com/rekadoodle/NFC-ML/archive/master.zip) and extract the src folder into the root of your MCP folder.
3. If using eclipse, open the workspace with the eclipse folder and go to the src properties. Under Resource > Resource Filters, remove the filter 'Name matches net'.
4. OPTIONAL: To get textures working, put the nfc folder into /jars/bin/minecraft.jar

## License

TBD I know nothing about licenses
