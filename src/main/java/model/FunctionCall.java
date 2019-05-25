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

    public String getName() {
        return name;
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

        switch (this.name) {
            case "print":
                Stdlib.print.setArguments(this.getArguments());
                return Stdlib.print.execute(scope);
            case "head":
                Stdlib.head.setArguments(this.getArguments());
                System.out.println(Stdlib.head.execute(scope));
                return Stdlib.head.execute(scope);
            case "tail":
                Stdlib.tail.setArguments(this.getArguments());
                System.out.println(Stdlib.tail.execute(scope));
                return Stdlib.tail.execute(scope);
        }

        List<Executable> evaluatedErguments = new ArrayList<>();
        Function function = scope.getFunctions().get(this.name);

        if(function.getParameters().size() != arguments.size()){
            throw new MyRunTimeException("Wrong number of arguments for function " + this.name);
        }

        for (int i = 0 ; i < arguments.size(); ++i){
            Parameter parameter = function.getParameters().get(i);
            Executable argument = arguments.get(i).execute(scope);

            if (argument instanceof Literal) {
                checkParameter(parameter,((Literal) argument).getEvaluatedType());
            } else if (argument instanceof Array) {
                checkParameter(parameter,new MyType(true,((Array) argument).getElementsType()));
            }

            evaluatedErguments.add(argument);
        }


        return null;
    }

    private void checkParameter(Parameter parameter, MyType givenType) throws MyRunTimeException {
        if(!parameter.getParameterType().equals(givenType)){
            throw new MyRunTimeException("Wrong parameter type for parameter " + parameter.getName() +". Found " +
                    givenType + " but should be " + parameter.getParameterType() +".");
        }
    }

}
