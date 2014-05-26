LIBnationGame
=============
The Original Fighting Game.
Made in my freshman year of high school, this game was the first step in my game development career.
Mossflower (represented by the mouse) is my online username, and Nick (represented by Mega-Man) was another member of the now defunct LIBnation community.


Build
-------------
Windows: 
Running the build.bat should work if a) you have your java classpath set and b) you have an alias in your default java keystore with the name DavidFinolWaterford.

Otherwise: 
In the src/ folder, run "javac *.java" to compile the java files.
Then, move all the *.class files into a folder called "libnation" and run "jar LIBnationGame.jar manifest.txt libnation" to create the jar archive. 
Next, delete the "libnation" folder from the src/ folder and move "LIBnationGame.jar" to the classes/ folder.
Finally, use jarsigner to sign LIBnationGame.jar.


Run
-------------
Open LIBnationGame.html with any web browser. Choose to Run and Allow (Do Not Block) in any security dialogues that may come up.
NOTE: Running it locally instead of through a webserver will require lowering java security settings to medium.
