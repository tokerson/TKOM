package model;

import com.sun.xml.internal.ws.wsdl.writer.document.soap.Body;
import program.MyRunTimeException;

import java.util.LinkedList;
import java.util.List;

public class IfStatement extends Node implements Executable {

    private Condition condition;
    private BodyBlock thenBlock;
    private List<Condition> elsifConditions = new LinkedList<>();
    private List<BodyBlock> elsifBodys = new LinkedList<>();
    private BodyBlock elseBlock = null;

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public BodyBlock getThenBlock() {
        return thenBlock;
    }

    public void setThenBlock(BodyBlock thenBlock) {
        this.thenBlock = thenBlock;
    }

    public List<Condition> getElsifConditions() {
        return elsifConditions;
    }

    public void setElsifConditions(List<Condition> elsifConditions) {
        this.elsifConditions = elsifConditions;
    }

    public List<BodyBlock> getElsifBodys() {
        return elsifBodys;
    }

    public void setElsifBodys(List<BodyBlock> elsifBodys) {
        this.elsifBodys = elsifBodys;
    }

    public BodyBlock getElseBlock() {
        return elseBlock;
    }

    public void setElseBlock(BodyBlock elseBlock) {
        this.elseBlock = elseBlock;
    }

    public void addElseIf(Condition condition, BodyBlock bodyBlock) {
        elsifConditions.add(condition);
        elsifBodys.add(bodyBlock);
    }

    @Override
    public Type getType() {
        return Type.IfStatement;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("if (");
        stringBuilder.append(condition);
        stringBuilder.append(" )");
        stringBuilder.append(thenBlock);

        for (int i = 0; i < elsifConditions.size(); ++i) {
            stringBuilder.append(" elsif (");
            stringBuilder.append(elsifConditions.get(i));
            stringBuilder.append(" )");
            stringBuilder.append(elsifBodys.get(i));
        }
        if (elseBlock != null) {
            stringBuilder.append(" else");
            stringBuilder.append(elseBlock);
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    @Override
    public Executable execute(Scope scope) throws MyRunTimeException {
        if (condition.execute(scope).isTrue()) {
            thenBlock.setParentScope(scope);
            return thenBlock.execute(thenBlock.getScope());
        }

        for (int i = 0; i < elsifBodys.size(); ++i) {
            if (elsifConditions.get(i).execute(scope).isTrue()) {
                BodyBlock elseifBody = elsifBodys.get(i);
                elseifBody.setParentScope(scope);
                return elseifBody.execute(elseifBody.getScope());
            }
        }

        if (elseBlock != null) {
            elseBlock.setParentScope(scope);
            return elseBlock.execute(elseBlock.getScope());
        }

        return null;
    }

}
