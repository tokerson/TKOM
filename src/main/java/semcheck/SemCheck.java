package semcheck;

import model.*;
import model.Program.Program;

public class SemCheck {
    private Scope mainScope;
    private Program program;

    public void check(Program program) throws Exception {
        this.program = program;
        this.mainScope = program.getScope();
        for(Node function : program.getStatements()) {
            if(function instanceof Function){
                if(!mainScope.addFunction((Function)function)){
                    throw new Exception("Redefinition of function " + ((Function) function).getName() + " within same scope");
                }
                else {
                    checkFunction((Function)function);
                }
            }
            else checkIfStatement(mainScope);

        }
    }

    private void checkIfStatement(Scope scope) {
        checkBody(scope);
    }

    private void checkBody(Scope scope) {
    }

    private void checkFunction(Function function) {

    }
}
