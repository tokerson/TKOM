package model;

import model.Token.TokenType;

public class MyType {
    private boolean array;
    private TokenType type;

    public MyType(boolean array, TokenType type) {
        this.array = array;
        this.type = type;
    }

    public boolean isArray() {
        return array;
    }

    public void setArray(boolean array) {
        this.array = array;
    }

    public TokenType getType() {
        return type;
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        if(isArray()){
            stringBuilder.append("[]");
        }
        stringBuilder.append(type);
        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object o){
        if(o instanceof MyType){
            return isArray() == ((MyType) o).isArray() && getType() == ((MyType) o).getType();
        }
        return false;
    }
}
