package semcheck;

import model.*;
import model.Program.Program;

public class SemCheck {
    private Scope mainScope;
    private Program program;

    public void check(Program program) throws Exception {
        this.program = program;
        this.mainScope = program.getScope();
        for(Node statement : program.getStatements()) {
            if(statement instanceof FunctionCall){
                checkFuncCall((FunctionCall) statement, mainScope);
            }

        }

    }

    private void checkFuncCall(FunctionCall functionCall, Scope scope) throws MyRunTimeException {
        if(Stdlib.FUNCTIONS.contains(functionCall.getName())){
            return;
        }

        if(!scope.isInScope(functionCall.getName())){
            throw new MyRunTimeException("Function " + functionCall.getName() + " wasn't declared in this scope.");
        }
        checkFunctionParameters(functionCall);
    }

    private void checkFunctionParameters(FunctionCall functionCall) {

    }

    private void checkIfStatement(Scope scope) {
        checkBody(scope);
    }

    private void checkBody(Scope scope) {
    }

    private void checkFunction(Function function) {

    }
}
