package model;

import java.util.ArrayList;
import java.util.List;

public class FunctionCall extends Node {

    private String name;
    private List<Node> arguments;

    public FunctionCall(String name) {
        this.name = name;
        this.arguments = new ArrayList<>();
    }

    public void addArgument(Node argument) {
        this.arguments.add(argument);
    }

    public void setArguments(List<Node> arguments){
        this.arguments = arguments;
    }

    @Override
    public Type getType() {
        return Type.FunctionCall;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(name);
        if (arguments.size() > 0) {
            stringBuilder.append("( ");
            for (int i = 0; i < arguments.size(); i++) {
                stringBuilder.append(arguments.get(i));
                if (i != arguments.size() - 1) stringBuilder.append(", ");
            }
            stringBuilder.append(" )");
        }
        return stringBuilder.toString();
    }
}
