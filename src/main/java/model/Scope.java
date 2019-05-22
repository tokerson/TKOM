package model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Scope {

    private Scope parentScope = null;
    private Map<String, MyType> functionAndTypes = new HashMap<String, MyType>();


    public boolean addToScope(String identifier, MyType myType){
        if(functionAndTypes.containsKey(identifier)){
            return false;
        }
        else {
            functionAndTypes.put(identifier, myType);
            return true;
        }
    }

    public void setParentScope(final Scope parentScope) {
        this.parentScope = parentScope;
    }

}
