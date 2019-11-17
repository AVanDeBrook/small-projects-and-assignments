@echo off
cd src
javac *.java
echo Testing Bus:
java Bus
echo Testing EvacuationQueue:
java EvacuationQueue
echo Testing Simulation:
java Simulation
pause
del *.class
cd ..
@echo on