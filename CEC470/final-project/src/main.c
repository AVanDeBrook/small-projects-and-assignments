#include "main.h"

CPU_t cpu = {
    .pc = 0,
    .mar = 0,
    .ir = 0,
    .acc = 0
};

int main(int argc, char *argv[])
{
    if (argc != 2) {
        fprintf(stderr, "Please provide an input file, like so: .\%s [mem_in]\n", argv[0]);
        return -1;
    }
    FILE *mem_in = fopen(argv[1], "r");

    uint8_t *mem_ptr = cpu.memory;
    while (!feof(mem_in)) {
        fscanf(mem_in, "%x", mem_ptr++);
    }

    while (cpu.memory[cpu.pc] != HALT_CODE) {
        printf("%02x ", cpu.memory[cpu.pc++]);
        if (cpu.pc % 10 == 0) {
            printf("\n");
        }
    }
    return 0;
}
