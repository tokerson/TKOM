package model;

import semcheck.MyRunTimeException;

import java.util.List;
import java.util.Map;

public interface Function {

    String getName();

    MyType getReturnType();

    Scope getScope();

    List<Parameter> getParameters();

    Executable execute(final Map<String, Executable> evaluatedArguments) throws MyRunTimeException;

}
