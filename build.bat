cd src/libnation
javac *.java
move *.class ../../classes/libnation
cd ../../classes
jar cfm LIBnationGame.jar manifest.txt libnation -C audio . -C images . 
move LIBnationGame.jar ..
jarsigner LIBnationGame.jar DavidFinolWaterford
