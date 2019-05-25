import lexer.Lexer;
import model.IfStatement;
import model.Node;
import model.Program.Program;
import org.junit.Test;
import parser.Parser;
import semcheck.MyRunTimeException;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

import static org.junit.Assert.assertEquals;


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

    @Test
    public void isEvaluatingIntegersGreaterThanZeroAsTrue() throws Exception {
        String text = "if( 1 ){}";
        Program program = parse(text);
        assertEquals("First statement should be If Statement", Node.Type.IfStatement,program.getStatement(0).getType());
        IfStatement ifStatement = (IfStatement) program.getStatement(0);
        assertEquals("Condition should be true", true, ifStatement.getCondition().execute(program.getScope()).isTrue());
    }

    @Test
    public void isEvaluatingDoublesGreaterThanZeroAsTrue() throws Exception {
        String text = "if( 1.0 ){}";
        Program program = parse(text);
        assertEquals("First statement should be If Statement", Node.Type.IfStatement,program.getStatement(0).getType());
        IfStatement ifStatement = (IfStatement) program.getStatement(0);
        assertEquals("Condition should be true", true, ifStatement.getCondition().execute(program.getScope()).isTrue());
    }

    @Test
    public void isEvaluatingIntegersLessThanZeroAsFalse() throws Exception {
        String text = "if( -1 ){}";
        Program program = parse(text);
        assertEquals("First statement should be If Statement", Node.Type.IfStatement,program.getStatement(0).getType());
        IfStatement ifStatement = (IfStatement) program.getStatement(0);
        assertEquals("Condition should be true", false, ifStatement.getCondition().execute(program.getScope()).isTrue());
    }

    @Test
    public void isEvaluatingDoublesLessThanZeroAsFalse() throws Exception {
        String text = "if( -1.0 ){}";
        Program program = parse(text);
        assertEquals("First statement should be If Statement", Node.Type.IfStatement,program.getStatement(0).getType());
        IfStatement ifStatement = (IfStatement) program.getStatement(0);
        assertEquals("Condition should be true", false, ifStatement.getCondition().execute(program.getScope()).isTrue());
    }

    @Test
    public void isEvaluatingZeroAsFalse() throws Exception {
        String text = "if( 0 ){}";
        Program program = parse(text);
        assertEquals("First statement should be If Statement", Node.Type.IfStatement,program.getStatement(0).getType());
        IfStatement ifStatement = (IfStatement) program.getStatement(0);
        assertEquals("Condition should be true", false, ifStatement.getCondition().execute(program.getScope()).isTrue());
    }

    @Test
    public void isEvaluatingEqualityForSameIntegersAsTrue() throws Exception {
        String text = "if( 1 == 1 ){}";
        Program program = parse(text);
        assertEquals("First statement should be If Statement", Node.Type.IfStatement,program.getStatement(0).getType());
        IfStatement ifStatement = (IfStatement) program.getStatement(0);
        assertEquals("Condition should be true", true, ifStatement.getCondition().execute(program.getScope()).isTrue());
    }

    @Test
    public void isEvaluatingEqualityForSameDoublesAsTrue() throws Exception {
        String text = "if( 1.0 == 1.0 ){}";
        Program program = parse(text);
        assertEquals("First statement should be If Statement", Node.Type.IfStatement,program.getStatement(0).getType());
        IfStatement ifStatement = (IfStatement) program.getStatement(0);
        assertEquals("Condition should be true", true, ifStatement.getCondition().execute(program.getScope()).isTrue());
    }

    @Test
    public void isEvaluatingInEqualityForSameIntegersAsFalse() throws Exception {
        String text = "if( 1 != 1 ){}";
        Program program = parse(text);
        assertEquals("First statement should be If Statement", Node.Type.IfStatement,program.getStatement(0).getType());
        IfStatement ifStatement = (IfStatement) program.getStatement(0);
        assertEquals("Condition should be false", false, ifStatement.getCondition().execute(program.getScope()).isTrue());
    }

    @Test
    public void isEvaluatingInEqualityForSameDoublesAsFalse() throws Exception {
        String text = "if( 1.0 != 1.0 ){}";
        Program program = parse(text);
        assertEquals("First statement should be If Statement", Node.Type.IfStatement,program.getStatement(0).getType());
        IfStatement ifStatement = (IfStatement) program.getStatement(0);
        assertEquals("Condition should be false", false, ifStatement.getCondition().execute(program.getScope()).isTrue());
    }

    @Test
    public void isEvaluatingInEqualityForDifferentDoublesAsTrue() throws Exception {
        String text = "if( 1.0 != 2.0 ){}";
        Program program = parse(text);
        assertEquals("First statement should be If Statement", Node.Type.IfStatement,program.getStatement(0).getType());
        IfStatement ifStatement = (IfStatement) program.getStatement(0);
        assertEquals("Condition should be true", true, ifStatement.getCondition().execute(program.getScope()).isTrue());
    }

    @Test
    public void isEvaluatingInEqualityForDifferentIntegersAsTrue() throws Exception {
        String text = "if( 1 != 2 ){}";
        Program program = parse(text);
        assertEquals("First statement should be If Statement", Node.Type.IfStatement,program.getStatement(0).getType());
        IfStatement ifStatement = (IfStatement) program.getStatement(0);
        assertEquals("Condition should be true", true, ifStatement.getCondition().execute(program.getScope()).isTrue());
    }

    @Test
    public void isEvaluatingInEqualityForDifferentIntegerAndDoubleAsTrue() throws Exception {
        String text = "if( 1 != 2.0 ){}";
        Program program = parse(text);
        assertEquals("First statement should be If Statement", Node.Type.IfStatement,program.getStatement(0).getType());
        IfStatement ifStatement = (IfStatement) program.getStatement(0);
        assertEquals("Condition should be true", true, ifStatement.getCondition().execute(program.getScope()).isTrue());
    }

    @Test
    public void isEvaluatingInEqualityForSameIntegerAndDoubleAsFalse() throws Exception {
        String text = "if( 1 != 1.0 ){}";
        Program program = parse(text);
        assertEquals("First statement should be If Statement", Node.Type.IfStatement,program.getStatement(0).getType());
        IfStatement ifStatement = (IfStatement) program.getStatement(0);
        assertEquals("Condition should be false", false, ifStatement.getCondition().execute(program.getScope()).isTrue());
    }

    @Test
    public void isEvaluatingGreaterThanAsTrue() throws Exception {
        String text = "if( 1 > 0 ){}";
        Program program = parse(text);
        assertEquals("First statement should be If Statement", Node.Type.IfStatement,program.getStatement(0).getType());
        IfStatement ifStatement = (IfStatement) program.getStatement(0);
        assertEquals("Condition should be true", true, ifStatement.getCondition().execute(program.getScope()).isTrue());
    }

    @Test
    public void isEvaluatingGreaterEqualsThanAsTrue() throws Exception {
        String text = "if( 1 >= 0 ){}";
        Program program = parse(text);
        assertEquals("First statement should be If Statement", Node.Type.IfStatement,program.getStatement(0).getType());
        IfStatement ifStatement = (IfStatement) program.getStatement(0);
        assertEquals("Condition should be true", true, ifStatement.getCondition().execute(program.getScope()).isTrue());
    }

    @Test
    public void isEvaluatingLessThanAsTrue() throws Exception {
        String text = "if( -1 < 1 ){}";
        Program program = parse(text);
        assertEquals("First statement should be If Statement", Node.Type.IfStatement,program.getStatement(0).getType());
        IfStatement ifStatement = (IfStatement) program.getStatement(0);
        assertEquals("Condition should be true", true, ifStatement.getCondition().execute(program.getScope()).isTrue());
    }

    @Test
    public void isEvaluatingLessEqualsThanAsTrue() throws Exception {
        String text = "if( -1 =< 1 ){}";
        Program program = parse(text);
        assertEquals("First statement should be If Statement", Node.Type.IfStatement,program.getStatement(0).getType());
        IfStatement ifStatement = (IfStatement) program.getStatement(0);
        assertEquals("Condition should be true", true, ifStatement.getCondition().execute(program.getScope()).isTrue());
    }

    @Test
    public void isEvaluatingLessOrEqualsForSameIntsAsTrue() throws Exception {
        String text = "if( -1 =< -1 ){}";
        Program program = parse(text);
        assertEquals("First statement should be If Statement", Node.Type.IfStatement,program.getStatement(0).getType());
        IfStatement ifStatement = (IfStatement) program.getStatement(0);
        assertEquals("Condition should be true", true, ifStatement.getCondition().execute(program.getScope()).isTrue());
    }

    @Test
    public void isEvaluatingAndOfTwoTruesAsTrue() throws Exception {
        String text = "if( 1 && 1 ){}";
        Program program = parse(text);
        assertEquals("First statement should be If Statement", Node.Type.IfStatement,program.getStatement(0).getType());
        IfStatement ifStatement = (IfStatement) program.getStatement(0);
        assertEquals("Condition should be true", true, ifStatement.getCondition().execute(program.getScope()).isTrue());
    }

    @Test
    public void isEvaluatingAndOfTrueAndFalseAsFalse() throws Exception {
        String text = "if( 1 && 0 ){}";
        Program program = parse(text);
        assertEquals("First statement should be If Statement", Node.Type.IfStatement,program.getStatement(0).getType());
        IfStatement ifStatement = (IfStatement) program.getStatement(0);
        assertEquals("Condition should be false", false, ifStatement.getCondition().execute(program.getScope()).isTrue());
    }

    @Test
    public void isEvaluatingAndOfTrueFalsesAsFalse() throws Exception {
        String text = "if( -1 && 0 ){}";
        Program program = parse(text);
        assertEquals("First statement should be If Statement", Node.Type.IfStatement,program.getStatement(0).getType());
        IfStatement ifStatement = (IfStatement) program.getStatement(0);
        assertEquals("Condition should be false", false, ifStatement.getCondition().execute(program.getScope()).isTrue());
    }

    @Test
    public void isEvaluatingOrOfTrueAndFalseAsTrue() throws Exception {
        String text = "if( 1 || 0 ){}";
        Program program = parse(text);
        assertEquals("First statement should be If Statement", Node.Type.IfStatement,program.getStatement(0).getType());
        IfStatement ifStatement = (IfStatement) program.getStatement(0);
        assertEquals("Condition should be true", true, ifStatement.getCondition().execute(program.getScope()).isTrue());
    }

    @Test
    public void isEvaluatingOrOfTwoTruesAsTrue() throws Exception {
        String text = "if( 1 || 1 ){}";
        Program program = parse(text);
        assertEquals("First statement should be If Statement", Node.Type.IfStatement,program.getStatement(0).getType());
        IfStatement ifStatement = (IfStatement) program.getStatement(0);
        assertEquals("Condition should be true", true, ifStatement.getCondition().execute(program.getScope()).isTrue());
    }

    @Test
    public void isEvaluatingOrOfTwoFalsesAsFalse() throws Exception {
        String text = "if( -1 || 0 ){}";
        Program program = parse(text);
        assertEquals("First statement should be If Statement", Node.Type.IfStatement,program.getStatement(0).getType());
        IfStatement ifStatement = (IfStatement) program.getStatement(0);
        assertEquals("Condition should be false", false, ifStatement.getCondition().execute(program.getScope()).isTrue());
    }

    @Test
    public void isEvaluatingAlternativeOfTrueAndFalseAsTrue() throws Exception {
        String text = "if( 1 || 0 && 1 ){}";
        Program program = parse(text);
        assertEquals("First statement should be If Statement", Node.Type.IfStatement,program.getStatement(0).getType());
        IfStatement ifStatement = (IfStatement) program.getStatement(0);
        assertEquals("Condition should be true", true, ifStatement.getCondition().execute(program.getScope()).isTrue());
    }

    @Test
    public void isEvaluatingParenthesisAlternativeFirstAndReturnsTrue() throws Exception {
        String text = "if((1 || 0 ) && 1 ){}";
        Program program = parse(text);
        assertEquals("First statement should be If Statement", Node.Type.IfStatement,program.getStatement(0).getType());
        IfStatement ifStatement = (IfStatement) program.getStatement(0);
        assertEquals("Condition should be true", true, ifStatement.getCondition().execute(program.getScope()).isTrue());
    }

    @Test
    public void isEvaluatingParenthesisAlternativeFirstAndReturnsFalse() throws Exception {
        String text = "if((1 || 0 ) && 0 ){}";
        Program program = parse(text);
        assertEquals("First statement should be If Statement", Node.Type.IfStatement,program.getStatement(0).getType());
        IfStatement ifStatement = (IfStatement) program.getStatement(0);
        assertEquals("Condition should be false", false, ifStatement.getCondition().execute(program.getScope()).isTrue());
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
