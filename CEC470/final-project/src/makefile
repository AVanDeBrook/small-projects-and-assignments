CC = gcc
INCLUDE = ..\inc
FLAGS = -g -I$(INCLUDE)
ODIR = ..\out

main.exe: main.o arithmetic.o memory.o
	$(CC) $(FLAGS) $(ODIR)\main.o $(ODIR)\arithmetic.o $(ODIR)\memory.o -o $(ODIR)\$@

main.o: main.c
	$(CC) $(FLAGS) -c $^ -o $(ODIR)\$@

arithmetic.o: arithmetic.c
	$(CC) $(FLAGS) -c $^ -o $(ODIR)\$@

memory.o: memory.c
	$(CC) $(FLAGS) -c $^ -o $(ODIR)\$@

.PHONY: clean

clean:
	del $(ODIR)\*.exe
	del $(ODIR)\*.o