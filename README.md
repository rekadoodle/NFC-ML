# NFC-ML
A WIP rewrite of Zowja's mod [New Frontier Craft](http://newfrontiercraft.net/) for ModLoader. It's not ready for survival yet.

## Features
- [x] Ores
- [x] Ore Generation
- [x] Stone Blocks
- [x] Ingots
- [ ] Metal Blocks
- [x] Tools & Armor
- [x] Vanilla tools re-balance
- [ ] Textures
- [x] Stone Blocks
- [x] Windows
- [x] Slabs/Stairs
- [x] Brick Oven
- [x] Brick Oven HMI support
- [x] Telescope
- [x] Wrench
- [x] Custom Sounds
- [x] Music Discs
- [ ] Oil
- [ ] Cave Generation
- [ ] Structure Generation
- [ ] Writable Books
- [ ] Configurable IDs
- [ ] Multiplayer Support
- [ ] Slab Placement UI
- [ ] Old World Block ID Converter

#### Changes from NFC
- Block ids are completely scrambled, many blocks share ids
- Stone block names have been made more specific
- Ingots and basic items all share a single id
- Slab/Stair placing is a bit different (WIP)

## Client Installation
Follow this if you just want to play the mod. There are no releases yet, but you can play the live development version.
1. Install [ModLoader](https://mcarchive.net/mods/modloader?gvsn=b1.7.3) 
2. Install [Forge](https://mcarchive.net/mods/minecraftforge?gvsn=b1.7.3)
3. ~~Download the [master branch](https://github.com/rekadoodle/NFC-ML/archive/master.zip) and extract it into your .minecraft/mods folder~~ Binaries coming soon.

## Development Setup
1. Setup an MCP workspace with Forge
   1. Download v4.3 of the [Mod Coder Pack](https://minecraft.gamepedia.com/Programs_and_editors/Mod_Coder_Pack#Downloads)
   2. Open /runtime/decompile.py with a text editor of your choice and remove line 15 `commands.checkupdates()`
   3. Follow the steps within /docs/README-MCP.TXT
   4. Download [Forge Source](https://mcarchive.net/mods/minecraftforge?gvsn=b1.7.3) and follow the steps in the README.TXT
   5. Download [mine_diver's Fixed Forge patch](http://www.mediafire.com/file/kmf47og8hf1c7ib) and extract it into the root of your MCP folder.
2. Download the [master branch](https://github.com/rekadoodle/NFC-ML/archive/master.zip) and extract the contents of the repo folder into the root of your MCP folder.
3. Delete any packages within the nfc.references package that you do not have installed to remove any errors.

## License

TBD I know nothing about licenses
