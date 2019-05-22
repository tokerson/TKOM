package model.Program;

import model.FunctionCall;
import model.Instruction;
import model.Node;
import model.Scope;
import semcheck.MyRunTimeException;

import java.util.ArrayList;

public class Program {
    private ArrayList<Node> statements;

    private Scope scope = new Scope();

    public Program() {
        this.statements = new ArrayList<>();
    }

    public ArrayList<Node> getStatements() {
        return statements;
    }

    public Node getStatement(int index) {
        return statements.get(index);
    }

    public void add(Node statement) {
        statements.add(statement);
    }

    public Scope getScope() {
        return scope;
    }

    public void execute() throws MyRunTimeException {
        for (Node statement: this.getStatements()) {
            if(statement instanceof FunctionCall){
                ((FunctionCall) statement).execute(this.getScope());
            }
        }
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }
}
