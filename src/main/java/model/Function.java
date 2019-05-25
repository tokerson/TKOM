package model;

import semcheck.MyRunTimeException;

import java.util.List;
import java.util.Map;

public interface Function {

    String getName();

    MyType getReturnType();

    Scope getScope();

    List<Parameter> getParameters();

    Executable execute(Scope scope, final Map<String, Expression> evaluatedArguments) throws MyRunTimeException;

}
