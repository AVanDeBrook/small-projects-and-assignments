
/** Includes **/
#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>

/** Defines & Constants **/
#define MEM_SIZE 65536

/** Macros **/
#define GET_OPERAND() (cpu->memory[cpu->pc++] << 8) | (cpu->memory[cpu->pc++])

/** Data Structures **/
typedef struct _CPU_t {
    uint16_t pc;
    uint16_t mar;
    uint8_t ir;
    uint8_t acc;
    uint8_t memory[MEM_SIZE];
} CPU_t;

/** Enumerations **/

/** Special Instruction Codes **/
typedef enum _SpecialOpCodes_e {
    NOP_CODE = 0x18,
    HALT_CODE = 0x19
} SpeiclaOpCodes_e;

/** Mathmatical and Logical Instruction Codes **/
typedef enum _ArithmeticCode_e {
    ARITH_AND = 0b1000,
    ARITH_OR = 0b1001,
    ARITH_XOR = 0b1010,
    ARITH_ADD = 0b1011,
    ARITH_SUB = 0b1100,
    ARITH_INC = 0b1101,
    ARITH_DEC = 0b1110,
    ARITH_NOT = 0b1111,
} ArithmeticCode_e;

/** Mathematical and Logical Destination Codes **/
typedef enum _ArithmeticDest_e {
    ARITH_DEST_INDIR = 0b00,
    ARITH_DEST_ACC = 0b01,
    ARITH_DEST_ADDR = 0b10,
    ARITH_DEST_MEM = 0b11
} ArithmeticDest_e;

/** Mathematical and Logical Source Codes **/
typedef enum _ArithmeticSrcs_e {
    ARITH_SRC_INDIR = 0b00,
    ARITH_SRC_ACC = 0b01,
    ARITH_SRC_CONST = 0b10,
    ARITH_SRC_MEM = 0b11
} ArithmeticSrc_e;

/** Branch Instruction Codes **/
typedef enum _BranchType_e {
    BRANCH_BRA = 0b000,
    BRANCH_BRZ = 0b001,
    BRANCH_BNE = 0b010,
    BRANCH_BLT = 0b011,
    BRANCH_BLE = 0b100,
    BRANCH_BGT = 0b101,
    BRANCH_BGE = 0b110,
} BranchType_e;

/** Memory Instruction Codes **/
typedef enum _MemoryCodes_e {
    MEM_STORE = 0b0,
    MEM_LOAD = 0b1
} MemoryCode_e;

/** Memory Register Codes **/
typedef enum _MemoryReg_e {
    MEM_REG_ACC = 0b0,
    MEM_REG_INDEX = 0b1
} MemoryReg_e;

/** Memory Method Codes **/
typedef enum _MemoryMethod_e {
    MEM_METHOD_ADDR = 0b00,
    MEM_METHOD_CONST = 0b01,
    MEM_METHOD_INDIR = 0b10
} MemoryMethod_e;

/** Prototypes **/
void fetchNextInstruction(void);
void executeInstruction(void);

/// Arithmetic Prototypes
void doArithOperation(CPU_t *cpu, ArithmeticCode_e opcode, ArithmeticDest_e dest, ArithmeticSrc_e src);
uint16_t getArithOperand(CPU_t *cpu, ArithmeticDest_e dest, ArithmeticSrc_e src);

/// Branching Prototypes
void doBranchOperation(CPU_t *cpu, BranchType_e type);

/// Memory Prototypes
void doMemoryOperation(CPU_t *cpu, MemoryCode_e opcode, MemoryReg_e reg, MemoryMethod_e method);
uint16_t getMemoryOperand(CPU_t *cpu, MemoryReg_e reg, MemoryMethod_e method);

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

    // Read memory contents in from the memory file
    printf("Reading memory from %s...\n", argv[1]);
    while (!feof(mem_in)) {
        fscanf(mem_in, "%x", mem_ptr++);
    }

    fclose(mem_in);

    // Execute instructions in memory until halt code is reached
    while (cpu.memory[cpu.pc] != HALT_CODE) {
        fetchNextInstruction();
        executeInstruction();
    }

    // Write memory to output file
    printf("Writing memory to %s...\n", argv[2]);
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
}

/**
 * Determines the type of operation in the IR and executes.
 */
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

/**
 * Determines the type of math and/or logical operation and performs it.
 */
void doArithOperation(CPU_t *cpu, ArithmeticCode_e opcode, ArithmeticDest_e dest, ArithmeticSrc_e src)
{
    switch (opcode) {
        case ARITH_AND:
            if (dest == ARITH_DEST_INDIR) {
                cpu->mar = dest;
                cpu->memory[cpu->mar] &= getArithOperand(cpu, dest, src);
            } else if (dest == ARITH_DEST_ACC) {
                cpu->acc &= getArithOperand(cpu, dest, src);
            } else if (dest == ARITH_DEST_ADDR) {
                cpu->mar &= cpu->memory[getArithOperand(cpu, dest, src)];
            } else {
                cpu->memory[cpu->mar] &= getArithOperand(cpu, dest, src);
            }

            break;
        case ARITH_OR:
            if (dest == ARITH_DEST_INDIR) {
                cpu->mar = dest;
                cpu->memory[cpu->mar] |= getArithOperand(cpu, dest, src);
            } else if (dest == ARITH_DEST_ACC) {
                cpu->acc |= getArithOperand(cpu, dest, src);
            } else if (dest == ARITH_DEST_ADDR) {
                cpu->mar |= cpu->memory[getArithOperand(cpu, dest, src)];
            } else {
                cpu->memory[cpu->mar] |= getArithOperand(cpu, dest, src);
            }

            break;
        case ARITH_XOR:
            if (dest == ARITH_DEST_INDIR) {
                cpu->mar = dest;
                cpu->memory[cpu->mar] ^= getArithOperand(cpu, dest, src);
            } else if (dest == ARITH_DEST_ACC) {
                cpu->acc ^= getArithOperand(cpu, dest, src);
            } else if (dest == ARITH_DEST_ADDR) {
                cpu->mar ^= cpu->memory[getArithOperand(cpu, dest, src)];
            } else {
                cpu->memory[cpu->mar] ^= getArithOperand(cpu, dest, src);
            }

            break;
        case ARITH_ADD:
            if (dest == ARITH_DEST_INDIR) {
                cpu->mar = dest;
                cpu->memory[cpu->mar] += getArithOperand(cpu, dest, src);
            } else if (dest == ARITH_DEST_ACC) {
                cpu->acc += getArithOperand(cpu, dest, src);
            } else if (dest == ARITH_DEST_ADDR) {
                cpu->mar += cpu->memory[getArithOperand(cpu, dest, src)];
            } else {
                cpu->memory[cpu->mar] += getArithOperand(cpu, dest, src);
            }

            break;
        case ARITH_SUB:
            if (dest == ARITH_DEST_INDIR) {
                cpu->mar = dest;
                cpu->memory[cpu->mar] -= getArithOperand(cpu, dest, src);
            } else if (dest == ARITH_DEST_ACC) {
                cpu->acc -= getArithOperand(cpu, dest, src);
            } else if (dest == ARITH_DEST_ADDR) {
                cpu->mar -= cpu->memory[getArithOperand(cpu, dest, src)];
            } else {
                cpu->memory[cpu->mar] -= getArithOperand(cpu, dest, src);
            }

            break;
        case ARITH_INC:
            if (dest == ARITH_DEST_INDIR) {
                cpu->mar = dest;
                cpu->memory[cpu->mar] += 1;
            } else if (dest == ARITH_DEST_ACC) {
                cpu->acc += 1;
            } else if (dest == ARITH_DEST_ADDR) {
                cpu->mar += 1;
            } else {
                cpu->memory[cpu->mar] += 1;
            }

            break;
        case ARITH_DEC:
            if (dest == ARITH_DEST_INDIR) {
                cpu->mar = dest;
                cpu->memory[cpu->mar] -= 1;
            } else if (dest == ARITH_DEST_ACC) {
                cpu->acc -= 1;
            } else if (dest == ARITH_DEST_ADDR) {
                cpu->mar -= 1;
            } else {
                cpu->memory[cpu->mar] -= 1;
            }

            break;
        case ARITH_NOT:
            if (dest == ARITH_DEST_INDIR) {
                cpu->mar = dest;
                cpu->memory[cpu->mar] = ~cpu->memory[cpu->mar];
            } else if (dest == ARITH_DEST_ACC) {
                cpu->acc = ~cpu->acc;
            } else if (dest == ARITH_DEST_ADDR) {
                cpu->mar = ~cpu->mar;
            } else {
                cpu->memory[cpu->mar] = ~cpu->memory[cpu->mar];
            }

            break;
        default:
            break;
    }
}

/**
 * Determines the operand from memory and how to interpret it.
 */
uint16_t getArithOperand(CPU_t *cpu, ArithmeticDest_e dest, ArithmeticSrc_e src)
{
    uint16_t operand;

    switch (src) {
        case ARITH_SRC_INDIR:
            cpu->mar = cpu->memory[GET_OPERAND()];
            operand = cpu->memory[cpu->mar];
            break;
        case ARITH_SRC_ACC:
            operand = cpu->acc;
            break;
        case ARITH_SRC_CONST:
            operand = cpu->memory[cpu->pc++];
            break;
        case ARITH_SRC_MEM:
            operand = cpu->memory[GET_OPERAND()];
            break;
        default:
            break;
    }

    return operand;
}

/**
 * Determines a branch operation and updates the PC based on where to branch.
 */
void doBranchOperation(CPU_t *cpu, BranchType_e type)
{
    uint16_t operand = GET_OPERAND();

    switch (type) {
        case BRANCH_BRA:
            cpu->pc = operand;

            break;
        case BRANCH_BRZ:
            if (cpu->acc == 0) {
                cpu->pc = operand;
            }

            break;
        case BRANCH_BNE:
            if (cpu->acc != 0) {
                cpu->pc = operand;
            }

            break;
        case BRANCH_BLT:
            if (cpu->acc < 0) {
                cpu->pc = operand;
            }

            break;
        case BRANCH_BLE:
            if (cpu->acc <= 0) {
                cpu->pc = operand;
            }

            break;
        case BRANCH_BGT:
            if (cpu->acc > 0) {
                cpu->pc = operand;
            }

            break;
        case BRANCH_BGE:
            if (cpu->acc >= 0) {
                cpu->pc = operand;
            }

            break;
        default:
            break;
    }
}

/**
 * Determines whether to perform a load or store operation and which register
 * to store in memory or load data from memory.
 */
void doMemoryOperation(CPU_t *cpu, MemoryCode_e opcode, MemoryReg_e reg, MemoryMethod_e method)
{
    uint16_t mem_operand, top_half, lower_half;
    switch (opcode) {
        case MEM_STORE:
            if (reg == MEM_REG_ACC) {
                // Store accumulator
                cpu->memory[getMemoryOperand(cpu, reg, method)] = cpu->acc;
            } else {
                // Store index register (MAR)
                mem_operand = getMemoryOperand(cpu, reg, method);
                top_half = (cpu->mar & 0xFF00) >> 8;
                lower_half = cpu->mar & 0xFF;

                cpu->memory[mem_operand] = (uint8_t) top_half;
                cpu->memory[mem_operand + 1] = (uint8_t) lower_half;
            }

            break;
        case MEM_LOAD:
            if (reg == MEM_REG_ACC) {
                // Load accumulator
                if (method == MEM_METHOD_ADDR) {
                    cpu->acc = cpu->memory[getMemoryOperand(cpu, reg, method)];
                } else {
                    cpu->acc = getMemoryOperand(cpu, reg, method);
                }
            } else {
                // Load index register (MAR)
                if (method == MEM_METHOD_ADDR) {
                    mem_operand = getMemoryOperand(cpu, reg, method);
                    cpu->mar = (cpu->memory[mem_operand] << 8) | (cpu->memory[mem_operand + 1]);
                } else {
                    mem_operand = getMemoryOperand(cpu, reg, method);
                    cpu->mar = mem_operand;//(mem_operand << 8) | (mem_operand + 1);
                }
            }

            break;
        default:
            break;
    }
}

/**
 * Gets an operand from memory based on which register and method are used.
 */
uint16_t getMemoryOperand(CPU_t *cpu, MemoryReg_e reg, MemoryMethod_e method)
{
    uint16_t operand;

    switch (method) {
        case MEM_METHOD_ADDR:
            operand = GET_OPERAND();
            break;
        case MEM_METHOD_CONST:
            if (reg == MEM_REG_ACC) {
                operand = cpu->memory[cpu->pc++];
            } else {
                operand = GET_OPERAND();
            }
            break;
        case MEM_METHOD_INDIR:
            operand = cpu->mar;
            break;
        default:
            break;
    }

    return operand;
}
