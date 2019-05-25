package model;

import java.util.List;

public interface Function {

    String getName();

    MyType getReturnType();

    Scope getScope();

    List<Parameter> getParameters();
}
