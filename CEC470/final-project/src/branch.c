#include "branch.h"

void doBranchOperation(CPU_t cpu, BranchType_e type)
{
    switch (type) {
        case BRANCH_BRA:
            cpu.pc = GET_OPERAND();

            break;
        case BRANCH_BRZ:
            if (cpu.acc == 0) {
                cpu.pc = GET_OPERAND();
            }

            break;
        case BRANCH_BNE:
            if (cpu.acc != 0) {
                cpu.pc = GET_OPERAND();
            }

            break;
        case BRANCH_BLT:
            if (cpu.acc < 0) {
                cpu.pc = GET_OPERAND();
            }

            break;
        case BRANCH_BLE:
            if (cpu.acc <= 0) {
                cpu.pc = GET_OPERAND();
            }

            break;
        case BRANCH_BGT:
            if (cpu.acc > 0) {
                cpu.pc = GET_OPERAND();
            }

            break;
        case BRANCH_BGE:
            if (cpu.acc >= 0) {
                cpu.pc = GET_OPERAND();
            }

            break;
        default:
            break;
    }
}
