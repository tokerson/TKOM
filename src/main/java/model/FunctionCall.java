package model;

import semcheck.MyRunTimeException;

import java.util.ArrayList;
import java.util.List;

public class FunctionCall extends Node implements Executable{

    private String name;
    private List<Executable> arguments;

    public FunctionCall(String name) {
        this.name = name;
        this.arguments = new ArrayList<>();
    }

    public void addArgument(Executable argument) {
        this.arguments.add(argument);
    }

    public void setArguments(List<Executable> arguments){
        this.arguments = arguments;
    }

    public List<Executable> getArguments() {
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
    public Executable execute(Scope scope) throws MyRunTimeException {
        if(this.name.equals("print")){
            Stdlib.print.setArguments(this.getArguments());
            return Stdlib.print.execute(scope);
        } else if (this.name.equals("head")){
            Stdlib.head.setArguments(this.getArguments());
            System.out.println(Stdlib.head.execute(scope));
            return Stdlib.head.execute(scope);
        } else if (this.name.equals("tail")){
            Stdlib.tail.setArguments(this.getArguments());
            System.out.println(Stdlib.tail.execute(scope));
            return Stdlib.tail.execute(scope);
        }

        return null;
    }
}
