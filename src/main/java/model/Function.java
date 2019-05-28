package model;

import semcheck.MyRunTimeException;

import java.util.List;
import java.util.Map;

public interface Function {

    String getName();

    MyType getReturnType();

    Scope getScope();

    void setScope(Scope scope);

    List<Parameter> getParameters();

    Executable execute(Scope scope, final Map<String, Executable> evaluatedArguments) throws MyRunTimeException;

}
