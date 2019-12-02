#include "main.h"
#include "arithmetic.h"
#include "memory.h"
#include "branch.h"

CPU_t cpu = {
    .pc = 0,
    .mar = 0,
    .ir = 0,
    .acc = 0
};

int main(int argc, char *argv[])
{
    if (argc != 3) {
        fprintf(stderr, "Please provide an input file, like so: .\%s [mem_in] [mem_out]\n", argv[0]);
        return -1;
    }

    FILE *mem_out, *mem_in = fopen(argv[1], "r");
    uint8_t *mem_ptr = cpu.memory;

    printf("Reading memory from %s...\n", argv[1]);
    while (!feof(mem_in)) {
        fscanf(mem_in, "%x", mem_ptr++);
    }

    fclose(mem_in);

    while (cpu.memory[cpu.pc] != HALT_CODE) {
        fetchNextInstruction();
        executeInstruction();
        printf("cpu.memory[0] = %02x\n", cpu.memory[0]);
        printf("ACC:\t%02x\n", cpu.acc);
        printf("MAR:\t%04x\n\n", cpu.mar);
    }


    printf("\nWriting memory to %s...\n", argv[2]);
    mem_out = fopen(argv[2], "w");

    for (int i = 0; i < MEM_SIZE; i++) {
        fprintf(mem_out, "%02x ", cpu.memory[i]);

        if ((i + 1) % 16 == 0) {
            fprintf(mem_out, "\n");
        }
    }

    fclose(mem_out);

    return 0;
}

/**
 * Stores the next instruction in the IR and increments the PC.
 */
void fetchNextInstruction(void)
{
    cpu.ir = cpu.memory[cpu.pc++];

    if (cpu.ir == NOP_CODE) {
        cpu.ir = cpu.memory[cpu.pc++];
    }

    //printf("PC:\t%04x\n", cpu.pc);
    printf("IR:\t%02x\n", cpu.ir);
}

void executeInstruction(void)
{
    // Math & Logic operations
    uint8_t opcode, dest, src;
    // Memory operations
    uint8_t reg, method;
    // Branch type
    uint8_t type;

    if (((cpu.ir & 0x80) >> 7) == 1) {
        // Arithmetic/Logical opcode
        opcode = (cpu.ir & 0xF0) >> 4;
        dest = (cpu.ir & 0x0C) >> 2;
        src = cpu.ir & 0x03;

        doArithOperation(&cpu, (ArithmeticCode_e) opcode, (ArithmeticDest_e) dest, (ArithmeticSrc_e) src);
    } else if (((cpu.ir & 0x10) >> 4) == 1) {
        // Branch/Jump opcode
        type = cpu.ir & 0x07;

        doBranchOperation(&cpu, (BranchType_e) type);
    } else {
        // Memory opcode
        opcode = (cpu.ir & 0x08) >> 3;
        reg = (cpu.ir & 0x04) >> 2;
        method = cpu.ir & 0x03;

        doMemoryOperation(&cpu, (MemoryCode_e) opcode, (MemoryReg_e) reg, (MemoryMethod_e) method);
    }
}
