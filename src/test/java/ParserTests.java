import lexer.Lexer;
import model.FunctionAssignment;
import model.IfStatement;
import model.Node;
import model.Program.Program;
import model.Token.Token;
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
        assertEquals("First statement should be", Node.Type.FunctionAssignment,program.getStatement(0).getType());
    }

    @Test
    public void isParsingSimpleFunctionAssignmentWithAdditiveOperationOnIntegers() throws Exception {
        String text = "def Int x = 4 + 4;";
        Program program = parse(text);
        assertEquals("First statement should be", Node.Type.FunctionAssignment,program.getStatement(0).getType());
    }

    @Test
    public void isParsingSimpleFunctionAssignmentWhenAssignedToOtherFunctionCallWithArguments() throws Exception {
        String text = "def Int x = y(4);";
        Program program = parse(text);
        assertEquals("First statement should be", Node.Type.FunctionAssignment,program.getStatement(0).getType());
    }

    @Test
    public void isParsingSimpleFunctionAssignmentWhenAssignedToOtherFunctionCallWithoutArguments() throws Exception {
        String text = "def Int x = y;";
        Program program = parse(text);
        System.out.println(program.getStatements().get(0));
        assertEquals("First statement should be", Node.Type.FunctionAssignment,program.getStatement(0).getType());
    }

    @Test
    public void isParsingSimpleFunctionAssignmentWithAdditiveOperationOnDoubles() throws Exception {
        String text = "def Double x = 4.2 + 4.1;";
        Program program = parse(text);
        assertEquals("First statement should be", Node.Type.FunctionAssignment,program.getStatement(0).getType());
    }

    @Test
    public void isParsingSimpleFunctionAssignmentWithMultiplicativeOperationOnIntegers() throws Exception {
        String text = "def Int x = 4 * 4;";
        Program program = parse(text);
        assertEquals("First statement should be", Node.Type.FunctionAssignment,program.getStatement(0).getType());
    }

    @Test
    public void isParsingSimpleFunctionAssignmentWithMultiplicativeOperationOnDoubles() throws Exception {
        String text = "def Double x = 4.2 * 4.1;";
        Program program = parse(text);
        assertEquals("First statement should be", Node.Type.FunctionAssignment,program.getStatement(0).getType());
    }

    @Test
    public void isParsingSimpleFunctionAssignmentWithNegativeInteger() throws Exception {
        String text = "def Int x = -4;";
        Program program = parse(text);
        assertEquals("First statement should be", Node.Type.FunctionAssignment,program.getStatement(0).getType());
    }

    @Test
    public void isParsingSimpleFunctionAssignmentWithPositiveDouble() throws Exception {
        String text = "def Double x = 4.25;";
        Program program = parse(text);
        assertEquals("First statement should be", Node.Type.FunctionAssignment,program.getStatement(0).getType());
    }

    @Test
    public void isParsingSimpleFunctionAssignmentWithNegativeDouble() throws Exception {
        String text = "def Double x = -4.25;";
        Program program = parse(text);
        assertEquals("First statement should be", Node.Type.FunctionAssignment,program.getStatement(0).getType());
    }

    @Test
    public void isParsingFunctionAssignmentWithIntgerInsideParenthesis() throws Exception {
        String text = "def Int x = (4);";
        Program program = parse(text);
        assertEquals("First statement should be", Node.Type.FunctionAssignment,program.getStatement(0).getType());
    }

    @Test
    public void isParsingFunctionAssignmentWithParenthesis() throws Exception {
        String text = "def Int x = 2*(1+4);";
        Program program = parse(text);
        assertEquals("First statement should be", Node.Type.FunctionAssignment,program.getStatement(0).getType());
    }

    @Test(expected = Exception.class)
    public void isThrowingExceptionWhenVoidFunctionReturnsSomething() throws Exception {
        String text = "def void x(){return 1;}";
        Program program = parse(text);
    }

    @Test
    public void isParsingVoidFunctionDeclaration() throws Exception {
        String text = "def void x(){}";
        Program program = parse(text);
        assertEquals("First statement should be", Node.Type.FunctionDeclaration,program.getStatement(0).getType());
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
    public void isParsingAFunctionWithNestedFunctionAssignments() throws Exception {
        String text = "def void x(){" +
                "def Int x = 4;" +
                "}";
        Program program = parse(text);
        assertEquals("First statement should be", Node.Type.FunctionDeclaration,program.getStatement(0).getType());
    }

    @Test
    public void isParsingIfStatement() throws Exception {
        String text = "if(4 > 2){}";
        Program program = parse(text);
        assertEquals("First statement should be", Node.Type.IfStatement,program.getStatement(0).getType());
    }

    @Test
    public void isParsingIfWithElseifStatement() throws Exception {
        String text = "if(4 > 2){}\nelsif( 2 > 3){}";
        Program program = parse(text);
        assertEquals("First statement should be", Node.Type.IfStatement,program.getStatement(0).getType());
        IfStatement ifStatement = (IfStatement) program.getStatement(0);
        assertEquals("Elsif statement should be inside", 1,ifStatement.getElsifConditions().size());
    }

    @Test
    public void isParsingIfWith2ElseifStatements() throws Exception {
        String text = "if(4 > 2){}\nelsif( 2 > 3){}\nelsif(3 == 2){}";
        Program program = parse(text);
        assertEquals("First statement should be", Node.Type.IfStatement,program.getStatement(0).getType());
        IfStatement ifStatement = (IfStatement) program.getStatement(0);
        assertEquals("Elsif statement should be inside", 2,ifStatement.getElsifConditions().size());
    }

    @Test
    public void isParsingIfWithElseStatements() throws Exception {
        String text = "if(4 > 2){}\nelse{}";
        Program program = parse(text);
        assertEquals("First statement should be", Node.Type.IfStatement,program.getStatement(0).getType());
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

    private Program parse(String string) throws Exception {
        Lexer lexer = new Lexer(convertStringToInputStreamReader(string));
        Parser parser = new Parser(lexer);
        return parser.parse();
    }

    private InputStreamReader convertStringToInputStreamReader(String string) {
        return new InputStreamReader(new ByteArrayInputStream(string.getBytes()));
    }

}
