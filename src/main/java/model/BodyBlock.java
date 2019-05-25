package model;

import semcheck.MyRunTimeException;

import java.util.ArrayList;
import java.util.List;

public class BodyBlock extends Node implements Executable{

    private List<Node> instructions = new ArrayList<>();
    private Scope scope = new Scope();

    public void addInstruction(Node instruction){
        this.instructions.add(instruction);
    }

    public Scope getScope() {
        return scope;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
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

    @Override
    public Executable execute(Scope scope) throws MyRunTimeException {
        return null;
    }
}
