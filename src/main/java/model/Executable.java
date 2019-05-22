package model;

import semcheck.MyRunTimeException;

public interface Executable {
    Node execute(final Scope scope) throws MyRunTimeException;
}
