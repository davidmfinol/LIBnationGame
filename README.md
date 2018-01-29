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
Java security settings need to be lowered to medium. 
Old instructions can be found here: http://www.java.com/en/download/help/jcp_security.xml
Medium security is no longer an option in Java 8 or newer, so you need to ensure you are using Java 7. 
You will also need to set appropriate permissions in your policy. 
One quick hack was to just allow everything (obviously unsafe) in java.policy:
grant {
  permission java.security.AllPermission;
};
Answer found here: https://stackoverflow.com/questions/10454037/java-security-accesscontrolexception-access-denied-java-io-filepermission
Once security settings are configured, open LIBnationGame.html with an older brower. 
Newer version of Chrome and FireFox do not allow java applets. 
Choose to Run and Allow (Do Not Block) in any security dialogues that may come up.
