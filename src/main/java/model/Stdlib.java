package model;

import program.MyRunTimeException;

import java.util.HashSet;
import java.util.List;

class Stdlib {

    static final HashSet<String> FUNCTIONS = new HashSet<String>() {{
        add("print");
        add("head");
        add("tail");
        add("length");
    }};

    static FunctionCall print = new FunctionCall("print"){
        @Override
        public Executable execute(Scope scope) throws MyRunTimeException {
            System.out.print(this.getArguments().get(0).execute(scope));
            return null;
        }
    };

    static FunctionCall head = new FunctionCall("head"){
        @Override
        public Literal execute(Scope scope) throws MyRunTimeException {
            if(this.getArguments().size() > 0){
                Executable array = this.getArguments().get(0);
                array = array.execute(scope);
                if( array instanceof Array){
                    List<Literal> elements = ((Array) array).getElements();
                    if(elements.size() > 0){
                        return elements.get(0);
                    } else throw new MyRunTimeException("head's list cannot be empty ");
                } else throw new MyRunTimeException("Argument of the standard head function must be an Array");
            } else throw new MyRunTimeException("Argument of the standard head function must be an Array");
        }
    };

    static FunctionCall tail = new FunctionCall("tail"){
        @Override
        public Array execute(Scope scope) throws MyRunTimeException {
            if(this.getArguments().size() > 0){
                Executable array = this.getArguments().get(0);
                array = array.execute(scope);
                if( array instanceof Array){
                    List<Literal> elements = ((Array) array).getElements();
                    if(elements.size() > 0){
                        Array arrayToReturn = new Array(((Array) array).getElementsType());
                        for(int i = 1; i < elements.size();++i){
                            arrayToReturn.addElement(elements.get(i));
                        }
                        return arrayToReturn;
                    } else throw new MyRunTimeException("tail's list cannot be empty ");
                } else throw new MyRunTimeException("Argument of the standard tail function must be an Array");
            } else throw new MyRunTimeException("Argument of the standard tail function must be an Array");
        }
    };

    static FunctionCall length = new FunctionCall("length"){
        @Override
        public Literal execute(Scope scope) throws MyRunTimeException {
            if(this.getArguments().size() > 0){
                Executable array = this.getArguments().get(0);
                array = array.execute(scope);
                if( array instanceof Array){
                    List<Literal> elements = ((Array) array).getElements();
                    return new MyInteger(elements.size());
                } else throw new MyRunTimeException("Argument of the standard length function must be an Array");
            } else throw new MyRunTimeException("Argument of the standard length function must be an Array");
        }
    };
}
