package model;

import semcheck.MyRunTimeException;

public interface Executable {
    Executable execute(final Scope scope) throws MyRunTimeException;
}
