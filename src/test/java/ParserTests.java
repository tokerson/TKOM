import lexer.Lexer;
import model.FunctionAssignment;
import model.Node;
import model.Program.Program;
import model.Token.Token;
import model.Token.TokenType;
import org.junit.Test;
import parser.Parser;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

import static org.junit.Assert.assertEquals;

public class ParserTests {

    @Test
    public void isParsingSimpleFunctionAssignmentWithPositiveInteger() throws Exception {
        String text = "def Int x = 4;";
        Program program = parse(text);
        assertEquals("First statement should be", Node.Type.FunctionAssignment,program.getStatements().get(0).getType());
    }

    @Test
    public void isParsingSimpleFunctionAssignmentWithAdditiveOperationOnIntegers() throws Exception {
        String text = "def Int x = 4 + 4;";
        Program program = parse(text);
        System.out.println(program.getStatements().get(0));
        assertEquals("First statement should be", Node.Type.FunctionAssignment,program.getStatements().get(0).getType());
    }

    @Test
    public void isParsingSimpleFunctionAssignmentWithAdditiveOperationOnDoubles() throws Exception {
        String text = "def Double x = 4.2 + 4.1;";
        Program program = parse(text);
        System.out.println(program.getStatements().get(0));
        assertEquals("First statement should be", Node.Type.FunctionAssignment,program.getStatements().get(0).getType());
    }

    @Test
    public void isParsingSimpleFunctionAssignmentWithMultiplicativeOperationOnIntegers() throws Exception {
        String text = "def Int x = 4 * 4;";
        Program program = parse(text);
        System.out.println(program.getStatements().get(0));
        assertEquals("First statement should be", Node.Type.FunctionAssignment,program.getStatements().get(0).getType());
    }

    @Test
    public void isParsingSimpleFunctionAssignmentWithMultiplicativeOperationOnDoubles() throws Exception {
        String text = "def Double x = 4.2 * 4.1;";
        Program program = parse(text);
        System.out.println(program.getStatements().get(0));
        assertEquals("First statement should be", Node.Type.FunctionAssignment,program.getStatements().get(0).getType());
    }

    @Test
    public void isParsingSimpleFunctionAssignmentWithNegativeInteger() throws Exception {
        String text = "def Int x = -4;";
        Program program = parse(text);
        assertEquals("First statement should be", Node.Type.FunctionAssignment,program.getStatements().get(0).getType());
    }

    @Test
    public void isParsingSimpleFunctionAssignmentWithPositiveDouble() throws Exception {
        String text = "def Double x = 4.25;";
        Program program = parse(text);
        assertEquals("First statement should be", Node.Type.FunctionAssignment,program.getStatements().get(0).getType());
    }

    @Test
    public void isParsingSimpleFunctionAssignmentWithNegativeDouble() throws Exception {
        String text = "def Double x = -4.25;";
        Program program = parse(text);
        System.out.println(program.getStatements().get(0));
        assertEquals("First statement should be", Node.Type.FunctionAssignment,program.getStatements().get(0).getType());
    }

    @Test
    public void isParsingFunctionAssignmentWithIntgerInsideParenthesis() throws Exception {
        String text = "def Int x = (4);";
        Program program = parse(text);
        System.out.println(program.getStatements().get(0));
        assertEquals("First statement should be", Node.Type.FunctionAssignment,program.getStatements().get(0).getType());
    }

    @Test
    public void isParsingFunctionAssignmentWithParenthesis() throws Exception {
        String text = "def Int x = 2*(1+4);";
        Program program = parse(text);
        System.out.println(program.getStatements().get(0));
        assertEquals("First statement should be", Node.Type.FunctionAssignment,program.getStatements().get(0).getType());
    }

    @Test(expected = Exception.class)
    public void isThrowingExceptionWhenVoidFunctionReturnsSomething() throws Exception {
        String text = "def void x(){return 1;}";
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
