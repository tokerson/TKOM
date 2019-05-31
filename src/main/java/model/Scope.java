package model;

import java.util.HashMap;
import java.util.Map;

public class Scope {

    private Scope parentScope = null;
    private Map<String, Function> functions = new HashMap<String, Function>();

    public Map<String, Function> getFunctions() {
        return functions;
    }

    public boolean addFunction(Function function) {
        if(functions.containsKey(function.getName())){
            functions.put(function.getName(), function);
            return false;
        }
        else {
            functions.put(function.getName(), function);
            return true;
        }
    }

//    public boolean addToScope(String identifier, MyType myType){
//        if(functionAndTypes.containsKey(identifier)){
//            return false;
//        }
//        else {
//            functionAndTypes.put(identifier, myType);
//            return true;
//        }
//    }

    public void setParentScope(final Scope parentScope) {
        this.parentScope = parentScope;
    }

    public Scope getParentScope() {
        return parentScope;
    }

    public boolean isInScope(String identifier){
        return functions.containsKey(identifier);
    }

    public MyType getReturnedType(String identifier){
        return functions.get(identifier).getReturnType();
    }

}
