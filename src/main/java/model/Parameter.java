package model;

import model.Token.TokenType;

public class Parameter{
    private MyType type;
    private String name;

    public Parameter(MyType type, String name) {
        this.type = type;
        this.name = name;
    }

    public boolean isArray() {
        return type.isArray();
    }

    public TokenType getType() {
        return type.getType();
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        if(type.isArray()){
            stringBuilder.append("[]");
        }
        stringBuilder.append(type);
        stringBuilder.append(": ");
        stringBuilder.append(name);

        return stringBuilder.toString();
    }

}
