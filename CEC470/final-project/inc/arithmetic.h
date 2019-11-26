#ifndef __ARITHMETIC_H
#define __ARITHMETIC_H

/// ------ Includes ------
#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>

#include "main.h"

/// ------ Typedefs & Enums ------
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

typedef enum _ArithmeticDest_e {
    ARITH_DEST_INDIR = 0b00,
    ARITH_DEST_ACC = 0b01,
    ARITH_DEST_ADDR = 0b10,
    ARITH_DEST_MEM = 0b11
} ArithmeticDest_e;

typedef enum _ArithmeticSrcs_e {
    ARITH_SRC_INDIR = 0b00,
    ARITH_SRC_ACC = 0b01,
    ARITH_SRC_CONST = 0b10,
    ARITH_SRC_MEM = 0b11
} ArithmeticSrc_e;

/// ------ Function Prototypes ------
void doArithOperation(CPU_t *cpu, ArithmeticCode_e opcode, ArithmeticDest_e dest, ArithmeticSrc_e src);
uint16_t getArithOperand(CPU_t *cpu, ArithmeticSrc_e src);

#endif