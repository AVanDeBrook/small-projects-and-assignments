CC = gcc
ODIR = ..\out
INCLUDE = ..\inc
FLAGS = -g -I$(INCLUDE)

main.exe: main.o arithmetic.o memory.o branch.o
	$(CC) $(FLAGS) $(ODIR)\main.o $(ODIR)\arithmetic.o $(ODIR)\memory.o $(ODIR)\branch.o -o $(ODIR)\$@

main.o: main.c
	$(CC) $(FLAGS) -c $^ -o $(ODIR)\$@

arithmetic.o: arithmetic.c
	$(CC) $(FLAGS) -c $^ -o $(ODIR)\$@

memory.o: memory.c
	$(CC) $(FLAGS) -c $^ -o $(ODIR)\$@

branch.o: branch.c
	$(CC) $(FLAGS) -c $^ -o $(ODIR)\$@

.PHONY: clean

clean:
	del $(ODIR)\*.exe
	del $(ODIR)\*.o