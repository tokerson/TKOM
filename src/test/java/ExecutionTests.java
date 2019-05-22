import lexer.Lexer;
import model.Program.Program;
import org.junit.Test;
import parser.Parser;
import semcheck.MyRunTimeException;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

public class ExecutionTests {

    @Test(expected = MyRunTimeException.class)
    public void isThrowingExceptionWhenExecutingHeadWithNoArguments() throws Exception {
        String text = "head();";
        Program program = parse(text);
        program.execute();
    }

    @Test(expected = MyRunTimeException.class)
    public void isThrowingExceptionWhenExecutingHeadWithEmptyList() throws Exception {
        String text = "head([]);";
        Program program = parse(text);
        program.execute();
    }

    @Test(expected = MyRunTimeException.class)
    public void isThrowingExceptionWhenExecutingTailWithNoArguments() throws Exception {
        String text = "tail();";
        Program program = parse(text);
        program.execute();
    }

    @Test(expected = MyRunTimeException.class)
    public void isThrowingExceptionWhenExecutingTailWithEmptyList() throws Exception {
        String text = "tail([]);";
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
