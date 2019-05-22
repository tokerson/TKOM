package model;

import semcheck.MyRunTimeException;

import java.util.ArrayList;
import java.util.List;

public class FunctionCall extends Node implements Executable{

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

    public List<Node> getArguments() {
        return arguments;
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

    @Override
    public Node execute(Scope scope) throws MyRunTimeException {
        if(this.name.equals("print")){
            Stdlib.print.setArguments(this.getArguments());
            return Stdlib.print.execute(scope);
        } else if (this.name.equals("head")){
            Stdlib.head.setArguments(this.getArguments());
            return Stdlib.head.execute(scope);
        } else if (this.name.equals("tail")){
            Stdlib.tail.setArguments(this.getArguments());
            System.out.println(Stdlib.tail.execute(scope));
            return Stdlib.tail.execute(scope);
        }

        return null;
    }
}
