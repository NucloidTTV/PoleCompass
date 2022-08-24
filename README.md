# PoleCompass
Minecraft Bukkit Plugin where you can modify your compass positions.

Features & Commands
Polecompass can do at the moment these things:

/compass <direction> - Sets your compass target to a the North, East, South or West(Those are the valid arguments for direction);
/compass pos <x> <y> <z> - Sets your compass target to a specific coordinate;
/compass pos current - Sets your compass target to your current location.
/compass bed - Sets your compass to your bed
/compass follow <player> - Sets your compass target to a player's location, and keep folowing him!
/compass about- shows the info about PoleCompass
/compass help - views help page
/setplayercompass <player> <direction> - Points the player's compass to the specified direction. Valid directions: north, east, south, west. (PoleCompass.setplayercompass)
/setplayercompass <player> pos <current> | <x> <y> <z> - Points the player's compass to the specified position, or the player's current position if you specify current instead.(PoleCompass.setplayercompass.pos)
/setplayercompass <player> follow <player to follow> - Make someones compass point and follow to a player.(PoleCompass.setplayercompass) Use /compass [reset] to reset your compass.
Tip: You can short arguments! for example, instead /compass north, you can use /compass n!

Permissions
PoleCompass.compass - /compass 
PoleCompass.compass.pos - /compass pos 
PoleCompass.compass.follow - /compass follow 
PoleCompass.compass.help - /compass help 
PoleCompass.compass.about - /compass about 
PoleCompass.setplayercompass - /setplayercompass 
PoleCompass.setplayercompass.follow - /setplayercompass follow 
PoleCompass.setplayercompass.pos - /setplayercompass pos

Other stuff

Originally developed by joppiesaus. 
https://dev.bukkit.org/projects/joppiesaus-pass

Changelog
Compiled in Java 1.8

V 2.0.5 - Initial Update. Currently updated to run 1.19.2

V 2.0.4 - hotfix

Fixed /compass bed not showing up in /compass help
V 2.0.3

Hopefully finally fixed that stupid bug(your compass will reset no matter what now)
/compass reset no longer sets your compass to your bed if you have one
Added /compass bed, it points the compass to the bed
Added /setplayercompass bed
V 2.0.2

Learned that I should, I definitely should, test more.
Fixed not being able to reset bug, the other variant
Fixed "<player> is not a valid command or direction" on /setplayercompass
V 2.0.1

Fixed some bugs & tweaked some stuff
Fixed not being able to reset bug
V 2.0 - Made for bukkit/spigot for Minecraft 1.9

Added /setplayercompass
Rewrote everything
Fixed many bugs
V 1.3 - Made for bukkit for Minecraft 1.7

Added /compass playerpos <name> [x | current] [y] [z], does the same thing as /compass pos, but then for other players.(very usefull for command blocks and stuff)
Tweaked some other stuff, including derp messages.
Realized that the Y value is actually useless
V 1.2 - compatiable with the 1.6.4 build

Added about command, /polecompass - shows the info about PoleCompass
Added permission for /compass follow (I got some protests from the hermits.)
Some tweaks & fixes
V 1.1

Added new arguments: /compass follow <player> - folows a player!(djeez. It took me a while!)
Added command, /compass help
V 1.0

Inital release
Ideas
GUI - being worked on
location information on right-click with compass - being worked on
Enyoy my plugin! Let me know if you found bugs, have ideas, problems or other stuff! Thanks for 1000 downloads by the way.