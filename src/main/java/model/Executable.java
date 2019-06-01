package model;

import program.MyRunTimeException;

public interface Executable {
    Executable execute(final Scope scope) throws MyRunTimeException;
}
