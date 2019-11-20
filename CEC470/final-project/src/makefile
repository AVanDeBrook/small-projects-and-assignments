CC = gcc
INCLUDE = ..\inc
FLAGS = -g -I$(INCLUDE)

main.exe: main.c
	$(CC) $(FLAGS) $^ -o $@

.PHONY: clean

clean:
	del *.exe