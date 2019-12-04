CC = gcc
ODIR = ..\out
INCLUDE = ..\inc
FLAGS = -g -I$(INCLUDE)

main.exe: main.o arithmetic.o memory.o branch.o
	$(CC) $(FLAGS) $(ODIR)\main.o $(ODIR)\arithmetic.o $(ODIR)\memory.o $(ODIR)\branch.o -o $@

%.o: %.c
	$(CC) $(FLAGS) -c $^ -o $(ODIR)\$@

.PHONY: clean

clean:
	del $(ODIR)\*.o
	del *.exe