#include "memory.h"

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
