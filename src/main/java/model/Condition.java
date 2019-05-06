package model;

public class Condition extends Expression {
    private boolean isNegated = false;

    public void setNegated(boolean value){
        isNegated = value;
    }

    public boolean isNegated(){
        return isNegated;
    }
}
