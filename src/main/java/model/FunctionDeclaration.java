package model;

import model.Token.TokenType;

import java.util.ArrayList;
import java.util.List;

public class FunctionDeclaration extends Node {
    String identifier;
    TokenType returnType;
    List<Parameter> parameters;
    BodyBlock bodyBlock;

    public FunctionDeclaration(String identifier, List parameters, BodyBlock bodyBlock) {
        this.identifier = identifier;
        this.parameters = parameters;
        this.bodyBlock = bodyBlock;
    }

    public void addParameter(Parameter parameter){
        this.parameters.add(parameter);
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public Type getType() {
        return Type.FunctionDeclaration;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(identifier);
        stringBuilder.append("( ");
        for(int i = 0; i < parameters.size(); i++) {
            stringBuilder.append(parameters.get(i).getType());
            stringBuilder.append(": ");
            stringBuilder.append(parameters.get(i).getName());
            if( i != parameters.size() - 1 ) stringBuilder.append(", ");
        }
        stringBuilder.append(")");
        stringBuilder.append(bodyBlock);

        return stringBuilder.toString();
    }
}
