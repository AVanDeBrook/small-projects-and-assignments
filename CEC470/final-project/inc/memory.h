#ifndef __MEMORY_H
#define __MEMORY_H

/// ------ Includes ------
#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>

#include "main.h"

/// ------ Typedefs & Enums ------
typedef enum _MemoryCodes_e {
    MEM_STORE = 0b0,
    MEM_LOAD = 0b1
} MemoryCode_e;

typedef enum _MemoryReg_e {
    MEM_REG_ACC = 0b0,
    MEM_REG_INDEX = 0b1
} MemoryReg_e;

typedef enum _MemoryMethod_e {
    MEM_METHOD_ADDR = 0b00,
    MEM_METHOD_CONST = 0b01,
    MEM_METHOD_INDIR = 0b10
} MemoryMethod_e;

/// ------ Function Prototypes ------
void doMemoryOperation(CPU_t cpu, MemoryCode_e opcode, MemoryReg_e reg, MemoryMethod_e method);
uint16_t getMemoryOperand(CPU_t cpu, MemoryMethod_e method);

#endif