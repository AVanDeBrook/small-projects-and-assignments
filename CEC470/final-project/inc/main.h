#ifndef __MAIN_H
#define __MAIN_H

/// ------ Includes ------
#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>

/// ------ Constants & Definitions ------
#define MEM_SIZE ((uint16_t) 65536)

/// ------ Macros ------
#define GET_OPERAND() (cpu.memory[cpu.pc++] << 8) | (cpu.memory[cpu.pc++])

/// ------ Typedefs & Enums ------
typedef struct _CPU_t {
    uint16_t pc;
    uint16_t mar;
    uint8_t ir;
    uint8_t acc;
    uint8_t memory[MEM_SIZE];
} CPU_t;

typedef enum _SpecialOpCodes_e {
    NOP_CODE = 0x18,
    HALT_CODE = 0x19
} SpeiclaOpCodes_e;

typedef enum _BranchOpCodes_e {
    BRANCH_BRA = 0b000,
    BRANCH_BRZ = 0b001,
    BRANCH_BNE = 0b010,
    BRANCH_BLT = 0b011,
    BRANCH_BLE = 0b100,
    BRANCH_BGT = 0b101,
    BRANCH_BGE = 0b110,
} BranchOpCodes_e;

/// ------ Prototypes ------
void fetchNextInstruction(void);
void executeInstruction(void);

#endif