package model;

import program.MyRunTimeException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FunctionDeclaration extends Node implements Function{
    private String identifier;
    private MyType returnType;
    private List<Parameter> parameters = new ArrayList<>();
    private BodyBlock bodyBlock;

    public FunctionDeclaration(String identifier, MyType returnType) {
        this.identifier = identifier;
        this.returnType = returnType;
        this.bodyBlock = new BodyBlock();
    }

    public MyType getReturnType() {
        return returnType;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public Parameter getParameter(String identifier){
        for (Parameter parameter : parameters){
            if (parameter.getName().equals(identifier)){
                return parameter;
            }
        }
        return null;
    }

    public boolean addToScope(Function function){
        return this.bodyBlock.getScope().addFunction(function);
    }

    @Override
    public Executable execute(Scope scope, Map<String, Executable> evaluatedArguments) throws MyRunTimeException {
        for (String key: evaluatedArguments.keySet()){
            Function function = new FunctionAssignment(key, evaluatedArguments.get(key), getParameter(key).getParameterType());
            bodyBlock.getScope().addFunction(function);
        }

        Executable evalutedValue = bodyBlock.execute(bodyBlock.getScope());



//        if( evalutedValue instanceof Literal)
        return evalutedValue;
    }

    public void checkReturnedType(){

    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    public void setBodyBlock(BodyBlock bodyBlock) {
        this.bodyBlock = bodyBlock;
        this.bodyBlock.setScope(bodyBlock.getScope());
    }

    public BodyBlock getBodyBlock() {
        return bodyBlock;
    }

    public Scope getScope() {
        return bodyBlock.getScope();
    }

    public void setScope(Scope scope) {
        this.bodyBlock.setScope(scope);
    }

    public void setParentScope(Scope scope){
        this.bodyBlock.setParentScope(scope);
    }

    @Override
    public Type getType() {
        return Type.FunctionDeclaration;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("def ");
        stringBuilder.append(returnType);
        stringBuilder.append(" ");
        stringBuilder.append(identifier);
        stringBuilder.append("( ");
        for(int i = 0; i < parameters.size(); i++) {
            stringBuilder.append(parameters.get(i).toString());
            if( i != parameters.size() - 1 ) stringBuilder.append(", ");
        }
        stringBuilder.append(")");
        stringBuilder.append(bodyBlock) ;

        return stringBuilder.toString();
    }

    @Override
    public String getName() {
        return identifier;
    }
}
