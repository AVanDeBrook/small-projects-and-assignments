#include "arithmetic.h"

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
