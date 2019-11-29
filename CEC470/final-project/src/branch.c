#include "branch.h"

void doBranchOperation(CPU_t *cpu, BranchType_e type)
{
    uint16_t operand = GET_OPERAND();

    switch (type) {
        case BRANCH_BRA:
            cpu->pc = operand;

            break;
        case BRANCH_BRZ:
            if (cpu->acc == 0) {
                cpu->pc = operand;
            }

            break;
        case BRANCH_BNE:
            if (cpu->acc != 0) {
                cpu->pc = operand;
            }

            break;
        case BRANCH_BLT:
            if (cpu->acc < 0) {
                cpu->pc = operand;
            }

            break;
        case BRANCH_BLE:
            if (cpu->acc <= 0) {
                cpu->pc = operand;
            }

            break;
        case BRANCH_BGT:
            if (cpu->acc > 0) {
                cpu->pc = operand;
            }

            break;
        case BRANCH_BGE:
            if (cpu->acc >= 0) {
                cpu->pc = operand;
            }

            break;
        default:
            break;
    }
}
