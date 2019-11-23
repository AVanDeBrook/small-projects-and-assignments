#include "arithmetic.h"

void doArithOperation(CPU_t cpu, ArithmeticCode_e opcode, ArithmeticDest_e dest, ArithmeticSrc_e src)
{
    switch (opcode) {
        case ARITH_AND:
            if (dest == ARITH_DEST_INDIR) {
                cpu.mar = dest;
                cpu.memory[cpu.mar] &= getArithOperand(cpu, src);
            } else if (dest == ARITH_DEST_ACC) {
                cpu.acc &= getArithOperand(cpu, src);
            } else if (dest == ARITH_DEST_ADDR) {
                cpu.mar &= getArithOperand(cpu, src);
            } else {
                cpu.memory[cpu.mar] &= getArithOperand(cpu, src);
            }

            break;
        case ARITH_OR:
            if (dest == ARITH_DEST_INDIR) {
                cpu.mar = dest;
                cpu.memory[cpu.mar] |= getArithOperand(cpu, src);
            } else if (dest == ARITH_DEST_ACC) {
                cpu.acc |= getArithOperand(cpu, src);
            } else if (dest == ARITH_DEST_ADDR) {
                cpu.mar |= getArithOperand(cpu, src);
            } else {
                cpu.memory[cpu.mar] |= getArithOperand(cpu, src);
            }

            break;
        case ARITH_XOR:
            if (dest == ARITH_DEST_INDIR) {
                cpu.mar = dest;
                cpu.memory[cpu.mar] ^= getArithOperand(cpu, src);
            } else if (dest == ARITH_DEST_ACC) {
                cpu.acc ^= getArithOperand(cpu, src);
            } else if (dest == ARITH_DEST_ADDR) {
                cpu.mar ^= getArithOperand(cpu, src);
            } else {
                cpu.memory[cpu.mar] ^= getArithOperand(cpu, src);
            }

            break;
        case ARITH_ADD:
        case ARITH_SUB:
        case ARITH_INC:
        case ARITH_DEC:
        case ARITH_NOT:
        default:
            break;
    }
}

uint16_t getArithOperand(CPU_t cpu, ArithmeticSrc_e src)
{
    uint16_t operand;

    switch (src) {
        case ARITH_SRC_INDIR:
            cpu.mar = GET_OPERAND();
            operand = cpu.memory[cpu.mar];
            break;
        case ARITH_SRC_ACC:
            operand = cpu.acc;
            break;
        case ARITH_SRC_CONST:
            operand = GET_OPERAND();
            break;
        case ARITH_SRC_MEM:
            operand = cpu.memory[GET_OPERAND()];
            break;
        default:
            break;
    }

    return operand;
}
