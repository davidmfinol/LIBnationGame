cd src
javac *.java
move *.class ../classes
cd ../classes
jar cfm LIBnationGame.jar manifest.txt audio images LIBnationGame$Controls.class LIBnationGame.class Player.class
move LIBnationGame.jar ..
cd ..
jarsigner LIBnationGame.jar DavidFinolWaterford
