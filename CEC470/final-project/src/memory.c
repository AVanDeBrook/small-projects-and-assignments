#include "memory.h"

void doMemoryOperation(CPU_t *cpu, MemoryCode_e opcode, MemoryReg_e reg, MemoryMethod_e method)
{
    switch (opcode) {
        case MEM_STORE:
            if (reg == MEM_REG_ACC) {
                // Store accumulator
                cpu->memory[getMemoryOperand(cpu, reg, method)] = cpu->acc;
            } else {
                // Store index register (MAR)
                cpu->memory[getMemoryOperand(cpu, reg, method)] = cpu->mar;
            }

            break;
        case MEM_LOAD:
            if (reg == MEM_REG_ACC) {
                // Load accumulator
                cpu->acc = cpu->memory[getMemoryOperand(cpu, reg, method)];
            } else {
                // Load index register (MAR)
                cpu->mar = getMemoryOperand(cpu, reg, method);//cpu->memory[getMemoryOperand(cpu, reg, method)];
            }

            break;
        default:
            break;
    }
}

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
            operand = (cpu->memory[cpu->mar] << 8) | (cpu->memory[cpu->mar + 1]);
            printf("\noperand = %04x\n", operand);
            break;
        default:
            break;
    }

    return operand;
}
