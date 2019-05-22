package model;

import model.Token.TokenType;
import semcheck.MyRunTimeException;

import java.util.ArrayList;
import java.util.List;

public class Stdlib {
    public static FunctionCall print = new FunctionCall("print"){
        @Override
        public Node execute(Scope scope){
            System.out.println(this.getArguments().get(0));
            return null;
        }
    };

    public static FunctionCall head = new FunctionCall("head"){
        @Override
        public Node execute(Scope scope) throws MyRunTimeException {
            if(this.getArguments().size() > 0){
                Node array = this.getArguments().get(0);
                if( array instanceof Array){
                    List<Node> elements = ((Array) array).getElements();
                    if(elements.size() > 0){
                        return elements.get(0);
                    } else throw new MyRunTimeException("head's list cannot be empty ");
                } else throw new MyRunTimeException("Argument of the standard head function must be an Array");
            } else throw new MyRunTimeException("Argument of the standard head function must be an Array");
        }
    };

    public static FunctionCall tail = new FunctionCall("tail"){
        @Override
        public Node execute(Scope scope) throws MyRunTimeException {
            if(this.getArguments().size() > 0){
                Node array = this.getArguments().get(0);
                if( array instanceof Array){
                    List<Node> elements = ((Array) array).getElements();
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
}
