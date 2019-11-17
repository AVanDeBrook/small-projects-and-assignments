@echo off
javac Manager.java
java Manager ..\example\fsm.txt ..\example\strings.txt
pause
del *.class
@echo on