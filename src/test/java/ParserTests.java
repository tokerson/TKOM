import lexer.Lexer;
import model.FunctionAssignment;
import model.FunctionDeclaration;
import model.IfStatement;
import model.Node;
import program.Program;
import model.Token.TokenType;
import org.junit.Test;
import parser.Parser;
import parser.ParserException;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ParserTests {

    @Test
    public void isParsingSimpleFunctionAssignmentWithPositiveInteger() throws Exception {
        String text = "def Int x = 4;";
        Program program = parse(text);
        assertEquals("First statement should be", FunctionAssignment.class,program.getStatement(0).getClass());
    }

    @Test
    public void isParsingSimpleFunctionAssignmentWithAdditiveOperationOnIntegers() throws Exception {
        String text = "def Int x = 4 + 4;";
        Program program = parse(text);
        assertEquals("First statement should be", FunctionAssignment.class,program.getStatement(0).getClass());
    }

    @Test
    public void isParsingSimpleFunctionAssignmentWhenAssignedToOtherFunctionCallWithArguments() throws Exception {
        String text = "def Int x = y(4);";
        Program program = parse(text);
        assertEquals("First statement should be", FunctionAssignment.class,program.getStatement(0).getClass());
    }

    @Test
    public void isParsingSimpleFunctionAssignmentWhenAssignedToOtherFunctionCallWithoutArguments() throws Exception {
        String text = "def Int x = y;";
        Program program = parse(text);
        assertEquals("First statement should be", FunctionAssignment.class,program.getStatement(0).getClass());
    }

    @Test
    public void isParsingSimpleFunctionAssignmentWithAdditiveOperationOnDoubles() throws Exception {
        String text = "def Double x = 4.2 + 4.1;";
        Program program = parse(text);
        assertEquals("First statement should be", FunctionAssignment.class,program.getStatement(0).getClass());
    }

    @Test
    public void isParsingSimpleFunctionAssignmentWithMultiplicativeOperationOnIntegers() throws Exception {
        String text = "def Int x = 4 * 4;";
        Program program = parse(text);
        assertEquals("First statement should be", FunctionAssignment.class,program.getStatement(0).getClass());
    }

    @Test
    public void isParsingSimpleFunctionAssignmentWithMultiplicativeOperationOnDoubles() throws Exception {
        String text = "def Double x = 4.2 * 4.1;";
        Program program = parse(text);
        assertEquals("First statement should be", FunctionAssignment.class,program.getStatement(0).getClass());
    }

    @Test
    public void isParsingSimpleFunctionAssignmentWithNegativeInteger() throws Exception {
        String text = "def Int x = -4;";
        Program program = parse(text);
        assertEquals("First statement should be", FunctionAssignment.class,program.getStatement(0).getClass());
    }

    @Test
    public void isParsingSimpleFunctionAssignmentWithPositiveDouble() throws Exception {
        String text = "def Double x = 4.25;";
        Program program = parse(text);
        assertEquals("First statement should be", FunctionAssignment.class,program.getStatement(0).getClass());
    }

    @Test
    public void isParsingSimpleFunctionAssignmentWithNegativeDouble() throws Exception {
        String text = "def Double x = -4.25;";
        Program program = parse(text);
        assertEquals("First statement should be", FunctionAssignment.class,program.getStatement(0).getClass());
    }

    @Test
    public void isParsingFunctionAssignmentWithIntgerInsideParenthesis() throws Exception {
        String text = "def Int x = (4);";
        Program program = parse(text);
        assertEquals("First statement should be", FunctionAssignment.class,program.getStatement(0).getClass());
    }

    @Test
    public void isParsingFunctionAssignmentWithParenthesis() throws Exception {
        String text = "def Int x = 2*(1+4);";
        Program program = parse(text);
        assertEquals("First statement should be", FunctionAssignment.class,program.getStatement(0).getClass());
    }

    @Test(expected = ParserException.class)
    public void isThrowingExceptionWhenNonVoidFunctionIsNotReturningAnything() throws Exception {
        String text = "def Int x(){}";
        Program program = parse(text);
    }

    @Test(expected = ParserException.class)
    public void isThrowingExceptionWhenNonVoidFunctionIsReturningEmptyStatement() throws Exception {
        String text = "def Int x(){return;}";
        Program program = parse(text);
    }

    @Test
    public void isParsingAFunctionWithNestedFunctionAssignmentsAndReturnStatement() throws Exception {
        String text = "def Double x(){" +
                "def Int x = 4;" +
                "return 4.2;" +
                "}";
        Program program = parse(text);
        assertEquals("First statement should be", FunctionDeclaration.class,program.getStatement(0).getClass());
    }

    @Test
    public void isParsingFunctionParametersCorrectly() throws Exception {
        String text = "def Double x(Int: z, Double: y){" +
                "return 4.2;" +
                "}";
        Program program = parse(text);
        assertEquals("First statement should be", FunctionDeclaration.class,program.getStatement(0).getClass());
        FunctionDeclaration functionDeclaration = (FunctionDeclaration) program.getStatement(0);
        assertEquals("First parameter should be", TokenType.INT_TYPE,functionDeclaration.getParameters().get(0).getType());
        assertEquals("Second parameter should be", TokenType.DOUBLE_TYPE,functionDeclaration.getParameters().get(1).getType());
    }

    @Test
    public void isParsingFunctionParametersCorrectlyIfTheyAreArray() throws Exception {
        String text = "def Double x([]Int: y){" +
                "return 4.2;" +
                "}";
        Program program = parse(text);
        assertEquals("First statement should be", FunctionDeclaration.class,program.getStatement(0).getClass());
        FunctionDeclaration functionDeclaration = (FunctionDeclaration) program.getStatement(0);
        assertEquals("First parameter should be", TokenType.INT_TYPE,functionDeclaration.getParameters().get(0).getType());
        assertEquals("First parameter should be", true,functionDeclaration.getParameters().get(0).isArray());
    }

    @Test
    public void isParsingFunctionDeclarationThatHasReturnTypeofArray() throws Exception {
        String text = "def []Int x(){return 0;}";
        Program program = parse(text);
        assertEquals("First statement should be", FunctionDeclaration.class,program.getStatement(0).getClass());
        FunctionDeclaration functionDeclaration = (FunctionDeclaration) program.getStatement(0);
        assertEquals("Return Type is Array",true,functionDeclaration.getReturnType().isArray());
        assertEquals("Return Type is Array of Type Int",TokenType.INT_TYPE,functionDeclaration.getReturnType().getType());
    }

    @Test
    public void isParsingAFunctionAssignmentWhenAssigningToArray() throws Exception {
        String text = "def []Int x = [1, 2];";
        Program program = parse(text);
        assertEquals("First statement should be", FunctionAssignment.class,program.getStatement(0).getClass());
        FunctionAssignment functionAssignment= (FunctionAssignment) program.getStatement(0);
        assertEquals("Return Type is Array",true,functionAssignment.getReturnType().isArray());
        assertEquals("Return Type is Array of Type Int",TokenType.INT_TYPE,functionAssignment.getReturnType().getType());

        assertEquals("Expression should be of type Array","model.Array",functionAssignment.getOperand(0).getClass().getCanonicalName());
    }

    @Test(expected = Exception.class)
    public void isThrowingAnExceptionWhenFunctionDeclarationHasReturnTypeArrayWithoutType() throws Exception {
        String text = "def [] x(){return 0;}";
        Program program = parse(text);
    }

    @Test(expected = Exception.class)
    public void isThrowingAnExceptionWhenFunctionDeclarationHasParameterAsArrayWithoutType() throws Exception {
        String text = "def Int x([]:a){return 0;}";
        Program program = parse(text);
    }

    @Test(expected = Exception.class)
    public void isThrowingAnExceptionWhenGivenAFunctionWithNestedFunctionAssignmentAndNoReturnStatement() throws Exception {
        String text = "def Double x(){" +
                "def Int y = 4;" +
                "}";
        Program program = parse(text);
    }

    @Test
    public void isParsingIfStatement() throws Exception {
        String text = "if(4 > 2){}";
        Program program = parse(text);
        assertEquals("First statement should be", IfStatement.class,program.getStatement(0).getClass());
    }

    @Test
    public void isParsingIfWithElseifStatement() throws Exception {
        String text = "if(4 > 2){}\nelsif( 2 > 3){}";
        Program program = parse(text);
        assertEquals("First statement should be", IfStatement.class,program.getStatement(0).getClass());
        IfStatement ifStatement = (IfStatement) program.getStatement(0);
        assertEquals("Elsif statement should be inside", 1,ifStatement.getElsifConditions().size());
    }

    @Test
    public void isParsingIfWith2ElseifStatements() throws Exception {
        String text = "if(4 > 2){}\nelsif( 2 > 3){}\nelsif(3 == 2){}";
        Program program = parse(text);
        assertEquals("First statement should be", IfStatement.class,program.getStatement(0).getClass());
        IfStatement ifStatement = (IfStatement) program.getStatement(0);
        assertEquals("Elsif statement should be inside", 2,ifStatement.getElsifConditions().size());
    }

    @Test
    public void isParsingIfWithElseStatements() throws Exception {
        String text = "if(4 > 2){}\nelse{}";
        Program program = parse(text);
        assertEquals("First statement should be", IfStatement.class,program.getStatement(0).getClass());
        IfStatement ifStatement = (IfStatement) program.getStatement(0);
        assertNotNull("Elsif statement should be inside",ifStatement.getElseBlock());
    }
    @Test(expected = ParserException.class)
    public void isThrowingExceptionWhenTwoElseStatements() throws Exception {
        String text = "if(4 > 2){}\nelse{}\nelse{}";
        Program program = parse(text);
    }

    @Test(expected = ParserException.class)
    public void isThrowingExceptionWhenElsifIsAfterElseStatements() throws Exception {
        String text = "if(4 > 2){}\nelse{}\nelseif( 1 == 1 ){}";
        Program program = parse(text);
    }

    @Test(expected = Exception.class)
    public void isThrowingAnExceptionWhenTwoFunctionsWithTheSameNameAreDeclaredInTheMainScope() throws Exception {
        String text = "def Int x = 4;\n" +
                "def Int x = 2";
        Program program = parse(text);
    }

    @Test(expected = ParserException.class)
    public void isThrowingAnExceptionWhenTwoFunctionsWithTheSameNameAreDeclaredInTheSameScope() throws Exception {
        String text = "def Int x(){\n" +
                "def Int y = 4;\n" +
                "def Int y = 2;\n" +
                "return 2;}";
        Program program = parse(text);
        program.execute();
    }

    @Test(expected = ParserException.class)
    public void isThrowingAnExceptionWhenFunctionIsDeclaredInTheScopeButIsInParametersAsWell() throws Exception {
        String text = "def Int x(Int:y){\n" +
                "def Int y = 4;\n" +
                "return 2;}";
        Program program = parse(text);
        program.execute();
    }

    @Test
    public void isParsingFunctionDeclarationCorrectlyWhenThereIsARedeclarationOfFunctionWithTheSameName() throws Exception {
        String text = "def Int x(Int:y){\n" +
                "def Int x = 4;\n" +
                "return 2;}";
        Program program = parse(text);
        assertEquals("First statement should be a function declaration", FunctionDeclaration.class,program.getStatement(0).getClass());
        FunctionDeclaration functionDeclaration = (FunctionDeclaration) program.getStatement(0);
        assertEquals("Function's scope should contain function x", true,functionDeclaration.getScope().isInScope("x"));
        assertEquals("Function's scope should contain function y", true,functionDeclaration.getScope().isInScope("y"));
    }

    @Test(expected = ParserException.class)
    public void isThrowingAnExceptionWhenInitializingArrayWithDifferentTypes() throws Exception {
        String text = "def []Int array = [1,2,3.0];";
        Program program = parse(text);
    }

    @Test(expected = ParserException.class)
    public void isThrowingExceptionWhenComparingEqualityString() throws Exception {
        String text = "print(\"string\" == 1 )"; //function call
        Program program = parse(text);
        program.execute();
    }

    @Test(expected = ParserException.class)
    public void isThrowingExceptionWhenComparingInEqualityString() throws Exception {
        String text = "print(\"string\" != 1 )"; //function call
        Program program = parse(text);
        program.execute();
    }

    @Test(expected = ParserException.class)
    public void isThrowingExceptionWhenComparingLessThanString() throws Exception {
        String text = "print(\"string\" < 1 )"; //function call
        Program program = parse(text);
        program.execute();
    }

    @Test(expected = ParserException.class)
    public void isThrowingExceptionWhenComparingLessOrEqualsThanString() throws Exception {
        String text = "print(\"string\" <= 1 )"; //function call
        Program program = parse(text);
        program.execute();
    }

    @Test(expected = ParserException.class)
    public void isThrowingExceptionWhenComparingGreaterThanString() throws Exception {
        String text = "print(\"string\" > 1 )"; //function call
        Program program = parse(text);
        program.execute();
    }

    @Test(expected = ParserException.class)
    public void isThrowingExceptionWhenComparingGreaterOrEqualsThanString() throws Exception {
        String text = "print(\"string\" >= 1 )"; //function call
        Program program = parse(text);
        program.execute();
    }



    private Program parse(String string) throws Exception {
        Lexer lexer = new Lexer(convertStringToInputStreamReader(string));
        Parser parser = new Parser(lexer);
        return parser.parse();
    }

    private InputStreamReader convertStringToInputStreamReader(String string) {
        return new InputStreamReader(new ByteArrayInputStream(string.getBytes()));
    }

}
