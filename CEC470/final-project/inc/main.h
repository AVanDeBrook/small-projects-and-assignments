#ifndef __MAIN_H
#define __MAIN_H

/// ------ Includes ------
#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>

/// ------ Constants & Definitions ------
#define MEM_SIZE ((uint16_t) 65536)

/// ------ Typedefs & Enums ------
typedef struct _CPU_t {
    uint16_t pc;
    uint16_t mar;
    uint8_t ir;
    uint8_t acc;
    uint8_t memory[MEM_SIZE];
} CPU_t;

typedef enum _OpCodes_e {
    HALT_CODE = 0x19,
} OpCodes_e;

/// ------ Prototypes ------
void fetchNextInstruction(void);
void executeInstruction(void);

#endif