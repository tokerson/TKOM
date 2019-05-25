package model;

import model.Token.TokenType;

import java.util.ArrayList;
import java.util.List;

public class FunctionAssignment extends Node implements Function {
    private String identifier;
    private Expression expression;
    private MyType returnType;
    private Scope scope = new Scope();

    public FunctionAssignment(String identifier, Expression expression, MyType returnType) {
        this.identifier = identifier;
        this.expression = expression;
        this.returnType = returnType;
    }

    public MyType getReturnType() {
        return returnType;
    }

    public Expression getExpression() {
        return expression;
    }

    public Scope getScope() {
        return scope;
    }

    @Override
    public List<Parameter> getParameters() {
        return new ArrayList<>();
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
