package model;

import model.Token.TokenType;

import java.util.List;

public class FunctionDeclaration extends Node {
    private String identifier;
    private MyType returnType;
    private List<Parameter> parameters;
    private BodyBlock bodyBlock;

    public FunctionDeclaration(String identifier, MyType returnType, List<Parameter> parameters, BodyBlock bodyBlock) {
        this.identifier = identifier;
        this.returnType = returnType;
        this.parameters = parameters;
        this.bodyBlock = bodyBlock;
    }

    public TokenType getReturnType() {
        return returnType.getType();
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public BodyBlock getBodyBlock() {
        return bodyBlock;
    }

    @Override
    public Type getType() {
        return Type.FunctionDeclaration;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("def ");
        stringBuilder.append(returnType);
        stringBuilder.append(" ");
        stringBuilder.append(identifier);
        stringBuilder.append("( ");
        for(int i = 0; i < parameters.size(); i++) {
            stringBuilder.append(parameters.get(i).toString());
            if( i != parameters.size() - 1 ) stringBuilder.append(", ");
        }
        stringBuilder.append(")");
        stringBuilder.append(bodyBlock) ;

        return stringBuilder.toString();
    }
}
