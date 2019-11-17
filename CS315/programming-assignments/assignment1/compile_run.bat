@echo off
cd src
javac *.java
java -Xss4m Manager
pause
del *.class
cd ..