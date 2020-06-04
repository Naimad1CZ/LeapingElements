# How to play Leaping Elements

## Launch the game
First of all, you need to extract the content of ZIP file to some folder and launch the
executable JAR file ‘LeapingElements.jar’. It requires Java 8, so download Java if you can’t open the
file:
https://www.java.com/en/download/

Note: if you are a developer and have higher version of Java installed, make sure that you use java
jdk (if using openjdk, make sure that you have installed JavaFX libraries along) 1.8.0_251 to open it (check by running `java -version`)

## Main Menu
After launching the game, you will see the Main Menu. Here you can choose to play a level
by clicking on it, load your own level from `.tmx` file, or see credits.

<p align="center">
 <img src="/screenshots/MainMenu.png" hight="auto" width="700">
</p>

## Controls
In the the levels itself, you control 2 characters – Ice hero (with arrows) and Fire hero (with
keys A, W, D). You can change camera modes – automatic (tries to show both heroes if possible),
focus Ice hero, focus Fire hero. – by pressing C. If you want to restart the level, press R. If you want to
quit level, press Escape.

<p align="center">
 <img src="/screenshots/Game.png" hight="auto" width="700">
</p>

## Goal
The goal in each level is to collect all available stars.

## Heroes specifics & game mechanics
Ice hero is more bulky, therefore moves slower and jumps lower (only around 2 blocks up),
but has 4 lives and can swim in the water. Ice hero also melts in the contact with Fire hero, so don’t
get too close!

Fire hero is more agile, moves faster, jumps higher (around 3 blocks up), but has 3 lives and
drowns in water.

Both heroes die in the contact with enemies, turret blocks and all projectiles except projectile
of their own kind (so Fire hero dies to ice and combined projectiles, Ice hero to fire and combined
projectile).

**Have fun! :)**

# How to create your own level
See `How_to_create_your_own_level.pdf` for tutorial and specifications of what should be included in `.txm` file in order to be possible to load it as a game level.
