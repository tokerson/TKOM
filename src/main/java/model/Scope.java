package model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Scope {
    private Scope parentScope = null;

    private Map<String, Function> functions = new HashMap<>();
    private Set<String> functionAndTypes = new HashSet<String>();

    public boolean addFunction(Function function) {
        if(functions.containsKey(function.getName())){
            return false;
        }
        else {
            functions.put(function.getName(), function);
            return true;
        }
    }

    public boolean addToScope(String identifier){
        if(functionAndTypes.contains(identifier)){
            return false;
        }
        else {
            functionAndTypes.add(identifier);
            return true;
        }
    }

    public Function getFunction(String name) {
        return functions.get(name);
    }

}
