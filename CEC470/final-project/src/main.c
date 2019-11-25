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
        fetchNextInstruction();
        executeInstruction();
    }

    return 0;
}

/**
 * Stores the next instruction in the IR and increments the PC.
 */
void fetchNextInstruction(void)
{
    cpu.ir = cpu.memory[cpu.pc++];
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

        doArithOperation(cpu, (ArithmeticCode_e) opcode, (ArithmeticDest_e) dest, (ArithmeticSrc_e) src);
    } else if (((cpu.ir & 0x10) >> 4) == 1) {
        // Branch/Jump opcode
        type = cpu.ir & 0x07;

        doBranchOperation(cpu, (BranchType_e) type);
    } else {
        // Memory opcode
        opcode = (cpu.ir & 0x08) >> 3;
        reg = (cpu.ir & 0x04) >> 2;
        method = (cpu.ir & 0x03);

        doMemoryOperation(cpu, (MemoryCode_e) opcode, (MemoryReg_e) reg, (MemoryMethod_e) method);
    }
}
