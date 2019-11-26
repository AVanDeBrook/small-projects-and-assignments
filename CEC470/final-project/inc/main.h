#ifndef __MAIN_H
#define __MAIN_H

/// ------ Includes ------
#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>

/// ------ Constants & Definitions ------
#define MEM_SIZE ((uint16_t) 65536)

/// ------ Macros ------
#define GET_OPERAND() (cpu->memory[cpu->pc++] << 8) | (cpu->memory[cpu->pc++])

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

/// ------ Prototypes ------
void fetchNextInstruction(void);
void executeInstruction(void);

#endif