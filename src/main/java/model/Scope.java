package model;

import java.util.HashMap;
import java.util.Map;

public class Scope {
    private Scope parentScope = null;

    private Map<String, Function> functions = new HashMap<>();

    public boolean addFunction(Function function) {
        if(functions.containsKey(function.getName())){
            return false;
        }
        else {
            functions.put(function.getName(), function);
            return true;
        }
    }

    public Function getFunction(String name) {
        return functions.get(name);
    }

}
