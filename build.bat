cd src
javac *.java
mkdir libnation
move *.class libnation
jar cfm LIBnationGame.jar manifest.txt libnation
rmdir libnation /s /q
del ..\classes\LIBnationGame.jar
move LIBnationGame.jar ..\classes
cd ..\classes
