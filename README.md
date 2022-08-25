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