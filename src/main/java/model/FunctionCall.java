package model;

import program.MyRunTimeException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FunctionCall extends Node implements Executable {

    private String name;
    private List<Executable> arguments = new ArrayList<>();

    public FunctionCall(String name) {
        this.name = name;
    }

    public void setArguments(List<Executable> arguments) {
        this.arguments = arguments;
    }

    List<Executable> getArguments() {
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

        if (Stdlib.FUNCTIONS.contains(this.name)) {
            return handleStdlibFunction(this.name, scope);
        }

        Map<String, Executable> evaluatedArguments = new HashMap<>();
        Function function = getFunctionFromScope(this.name, scope);

        if (function.getParameters().size() != arguments.size()) {
            throw new MyRunTimeException("Wrong number of arguments for function " + this.name + ". Given - "
                    + arguments.size() + " and should be - " + function.getParameters().size() + ".");
        }

        evaluatedArguments = evaluateArguments(function, scope);

        System.out.println("Executing function " + function.getName());
        return function.execute(scope, evaluatedArguments);
    }

    private Function getFunctionFromScope(String name, Scope scope) throws MyRunTimeException {
        Function function = scope.getFunctions().get(this.name);
        Scope tempScope = scope.getParentScope();

        while (function == null) {
            function = tempScope.getFunctions().get(this.name);
            tempScope = tempScope.getParentScope();
            if (tempScope == null) break;
        }

        if (function == null) {
            throw new MyRunTimeException("Function " + this.name + " wasn't declared in this scope");
        }
        return function;
    }

    private Executable handleStdlibFunction(String name, Scope scope) throws MyRunTimeException {
        switch (this.name) {
            case "print":
                Stdlib.print.setArguments(this.getArguments());
                return Stdlib.print.execute(scope);
            case "head":
                Stdlib.head.setArguments(this.getArguments());
                return Stdlib.head.execute(scope);
            case "tail":
                Stdlib.tail.setArguments(this.getArguments());
                return Stdlib.tail.execute(scope);
            case "length":
                Stdlib.length.setArguments(this.getArguments());
                return Stdlib.length.execute(scope);
            default:
                throw new MyRunTimeException("No function called " + name + " found in Stdlib.");
        }
    }

    private Map<String, Executable> evaluateArguments(Function function, Scope scope) throws MyRunTimeException {
        Map<String, Executable> evaluatedArguments = new HashMap<>();
        for (int i = 0; i < arguments.size(); ++i) {
            Parameter parameter = function.getParameters().get(i);
            Executable argument = arguments.get(i);

            if (argument instanceof Literal) {
                checkParameter(parameter, ((Literal) argument).getEvaluatedType());
            } else if (argument instanceof Array) {
                checkParameter(parameter, new MyType(true, ((Array) argument).getElementsType()));
            }

            argument = argument.execute(scope); //this repair recursiveness but language is not lazy then.
            evaluatedArguments.put(parameter.getName(), argument);
        }

        return evaluatedArguments;
    }

    private void checkParameter(Parameter parameter, MyType givenType) throws MyRunTimeException {
        if (!parameter.getParameterType().equals(givenType)) {
            throw new MyRunTimeException("Wrong parameter type for parameter " + parameter.getName() + ". Found " +
                    givenType + " but should be " + parameter.getParameterType() + ".");
        }
    }

}
