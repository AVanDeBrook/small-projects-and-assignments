#ifndef __BRANCH_H
#define __BRANCH_H

/// ------ Includes ------
#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>

#include "main.h"

/// ------ Typedefs & Enums ------
typedef enum _BranchType_e {
    BRANCH_BRA = 0b000,
    BRANCH_BRZ = 0b001,
    BRANCH_BNE = 0b010,
    BRANCH_BLT = 0b011,
    BRANCH_BLE = 0b100,
    BRANCH_BGT = 0b101,
    BRANCH_BGE = 0b110,
} BranchType_e;

/// ------ Function Prototypes ------
void doBranchOperation(CPU_t *cpu, BranchType_e type);

#endif