package model;

import semcheck.MyRunTimeException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FunctionAssignment extends Node implements Function {
    private String identifier;
    private Executable expression;
    private MyType returnType;
    private Scope scope = new Scope();
    private List<Parameter> parameters = new ArrayList<>();


    public FunctionAssignment(String identifier, Executable expression, MyType returnType) {
        this.identifier = identifier;
        this.expression = expression;
        this.returnType = returnType;
    }

    public MyType getReturnType() {
        return returnType;
    }

    public Executable getExpression() {
        return expression;
    }

    public Scope getScope() {
        return scope;
    }

    public Expression getOperand(int index){
        if(expression instanceof Expression){
            return (Expression) ((Expression) expression).getOperands().get(index);
        }
        return null;
    }

    @Override
    public List<Parameter> getParameters() {
        return new ArrayList<>();
    }

    @Override
    public Executable execute(Scope scope, Map<String, Executable> evaluatedArguments) throws MyRunTimeException {
        return expression.execute(scope);
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    public void setParentScope(Scope scope){
        this.scope.setParentScope(scope);
    }

    @Override
    public Type getType() {
        return Type.FunctionAssignment;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("def ");
        stringBuilder.append(returnType);
        stringBuilder.append(" ");
        stringBuilder.append(identifier +" ");
        stringBuilder.append(" = ");
        stringBuilder.append(expression.toString());
        return stringBuilder.toString();
    }

    @Override
    public String getName() {
        return identifier;
    }
}
