package model;

import model.Token.TokenType;
import program.MyRunTimeException;

public class MyString extends Literal<MyString, String> {

    private String content;

    public MyString(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String getValue() {
        return content;
    }

    @Override
    public MyString add(Literal second) {
        this.content = this.content.concat(second.toString());
        return this;
    }

    @Override
    public MyString substract(Literal second) throws MyRunTimeException {
        throw new MyRunTimeException("Cannot substract anything from String");
    }

    @Override
    public MyString multiply(Literal second) throws MyRunTimeException {
        throw new MyRunTimeException("Cannot multiply String by anything");
    }

    @Override
    public MyString divide(Literal second) throws MyRunTimeException {
        throw new MyRunTimeException("Cannot divide String by anything");
    }

    @Override
    public MyString isGreaterThan(Literal second) throws MyRunTimeException {
        throw new MyRunTimeException("Cannot compare String with anything");
    }

    @Override
    public MyString isGreaterOrEqualThan(Literal second) throws MyRunTimeException {
        throw new MyRunTimeException("Cannot compare String with anything");
    }

    @Override
    public MyString isLessThan(Literal second) throws MyRunTimeException {
        throw new MyRunTimeException("Cannot compare String with anything");
    }

    @Override
    public MyString isLessOrEqualThan(Literal second) throws MyRunTimeException {
        throw new MyRunTimeException("Cannot compare String with anything");
    }

    @Override
    public MyType getEvaluatedType() {
        return new MyType(true, TokenType.STRING);
    }

    @Override
    public Executable execute(Scope scope) throws MyRunTimeException {
        return this;
    }

    @Override
    public Type getType() {
        return Type.String;
    }

    @Override
    public String toString() {
        return this.content;
    }
}
