package com.company.cli;

public class Instruction extends TextUtil {

    private InstructionType type;

    public Instruction(String text) {
        super(text);
        type = InstructionType.get(args.get(0));
    }

    public InstructionType getType() {
        return type;
    }
}
