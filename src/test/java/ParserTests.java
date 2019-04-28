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
        String text = "def x = 4;";
        Program program = parse(text);
        assertEquals("First statement should be", Node.Type.FunctionAssignment,program.getStatements().get(0).getType());
    }

    @Test
    public void isParsingSimpleFunctionAssignmentWithNegativeInteger() throws Exception {
        String text = "def x = -4;";
        Program program = parse(text);
        assertEquals("First statement should be", Node.Type.FunctionAssignment,program.getStatements().get(0).getType());
    }

    @Test
    public void isParsingSimpleFunctionAssignmentWithPositiveDouble() throws Exception {
        String text = "def x = 4.25;";
        Program program = parse(text);
        assertEquals("First statement should be", Node.Type.FunctionAssignment,program.getStatements().get(0).getType());
    }

    @Test
    public void isParsingSimpleFunctionAssignmentWithNegativeDouble() throws Exception {
        String text = "def x = -4.25;";
        Program program = parse(text);
        System.out.println(program.getStatements().get(0));
        assertEquals("First statement should be", Node.Type.FunctionAssignment,program.getStatements().get(0).getType());
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
