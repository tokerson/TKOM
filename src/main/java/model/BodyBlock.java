package model;

import java.util.ArrayList;
import java.util.List;

public class BodyBlock extends Node {

    public List<Node> instructions = new ArrayList<>();

    public void addInstruction(Node instruction){
        this.instructions.add(instruction);
    }

    @Override
    public Type getType() {
        return Type.BodyBlock;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("{\n");

        for (Node instruction: instructions) {
            stringBuilder.append("  ");
            stringBuilder.append(instruction);
            stringBuilder.append(";\n");
        }

        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}
