package semcheck;

import model.*;
import model.Program.Program;

public class SemCheck {
    private Scope mainScope;
    private Program program;

    public void check(Program program) throws Exception {
        this.program = program;
        this.mainScope = program.getScope();
//        for(Node function : program.getStatements()) {
//            if(function instanceof Function){
//                if(!mainScope.addToScope(((Function) function).getName(),((Function) function).getReturnType())){
//                    throw new Exception("Redefinition of function " + ((Function) function).getName() + " within same scope");
//                }
//                else {
//                    checkFunction((Function)function);
//                }
//            }
//            else checkIfStatement(mainScope);
//        }
//        for(Node function: this.program.getStatements()) {
//            if(function instanceof Function){
//                ((Function) function).getScope().setParentScope(mainScope);
//            }
//        }
    }

    private void checkIfStatement(Scope scope) {
        checkBody(scope);
    }

    private void checkBody(Scope scope) {
    }

    private void checkFunction(Function function) {

    }
}
