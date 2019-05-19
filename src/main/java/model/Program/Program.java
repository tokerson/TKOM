package model.Program;

import model.Instruction;
import model.Node;

import java.util.ArrayList;

public class Program {
    private ArrayList<Node> statements;

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
}
