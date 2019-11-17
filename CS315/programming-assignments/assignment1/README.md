# CS 315 Programming Assignment 1
## Compiling and Running
To compile and run the assignment you can run the [`compile_run.bat`](./compile_run.bat) in the top directory of the git repo.
For full transparency, it contains the following code:
```
@echo off
cd src
javac *.java
java -Xss4m Manager
pause
del *.class
cd ..
```
The `-Xss4m` when running the program is to change the stack size to 4 MB. This was the minimum size for Quick Sort to work on a data set with a size of 50,000, otherwise the program will run into a stack overflow.
## Sorting Algorithms Used
I used the following algorithms for this assignment:
* Insertion Sort
* Quick Sort
I tested the algorithms on an array of size 10 to ensure I implemented the algorithms properly before scaling them.
The program is set to use an array of size 50 for the small data sets, and size 50,000 for the large data sets.
This can be changed by modifiying the following two lines at the top of [`Manager.java`](./src/Manager.java):
```
private static final int smallDataLength = 50;
private static final int largeDataLength = 50000;
```
