package model;

import program.MyRunTimeException;

public class Return extends Node implements Executable{

    private Executable returnStatement;

    public Return(Executable returnStatement) {
        this.returnStatement = returnStatement;
    }

    @Override
    public String toString() {
        return "return " + returnStatement;
    }

    @Override
    public Executable execute(Scope scope) throws MyRunTimeException {
        return returnStatement.execute(scope);
    }
}
