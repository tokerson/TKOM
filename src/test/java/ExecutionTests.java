import lexer.Lexer;
import model.*;
import model.Token.TokenType;
import parser.ParserException;
import program.Program;
import org.junit.Test;
import parser.Parser;
import program.MyRunTimeException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

import static org.junit.Assert.*;


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
    public void isThrowingExceptionWhenExecutingHeadWithArgumentBeingNonList() throws Exception {
        String text = "head(1);";
        Program program = parse(text);
        program.execute();
    }

    @Test
    public void headIsReturningFirstElementOfAnArray() throws Exception {
        String text = "def Int x = head([1]);";
        Program program = parse(text);
        program.execute();
        assertEquals("First statement should be Function Assignment", FunctionAssignment.class.getName(),program.getStatement(0).getClass().getName());
        FunctionAssignment functionAssignment = (FunctionAssignment) program.getStatement(0);
        assertEquals("x should be literal 1", new MyInteger(1).getValue(), ((MyInteger)functionAssignment.execute(program.getScope(),null)).getValue());
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

    @Test(expected = MyRunTimeException.class)
    public void isThrowingExceptionWhenExecutingTailWithArgumentBeingNonList() throws Exception {
        String text = "tail(1);";
        Program program = parse(text);
        program.execute();
    }

    @Test(expected = MyRunTimeException.class)
    public void isThrowingExceptionWhenExecutingLengthWithArgumentBeingNonList() throws Exception {
        String text = "length(1);";
        Program program = parse(text);
        program.execute();
    }

    @Test(expected = MyRunTimeException.class)
    public void isThrowingExceptionWhenExecutingLengthWithNoArguments() throws Exception {
        String text = "length();";
        Program program = parse(text);
        program.execute();
    }

    @Test
    public void lengthIsReturningLengthOfAnArray() throws Exception {
        String text = "def Int x = length([1]);";
        Program program = parse(text);
        program.execute();
        assertEquals("First statement should be Function Assignment", FunctionAssignment.class.getName(),program.getStatement(0).getClass().getName());
        FunctionAssignment functionAssignment = (FunctionAssignment) program.getStatement(0);
        assertEquals("x should be literal 1", new MyInteger(1).getValue(), ((MyInteger)functionAssignment.execute(program.getScope(),null)).getValue());
    }

    @Test
    public void lengthIsReturning0WhenArrayIsEmpty() throws Exception {
        String text = "def Int x = length([]);";
        Program program = parse(text);
        program.execute();
        assertEquals("First statement should be Function Assignment", FunctionAssignment.class.getName(),program.getStatement(0).getClass().getName());
        FunctionAssignment functionAssignment = (FunctionAssignment) program.getStatement(0);
        assertEquals("x should be literal 0", new MyInteger(0).getValue(), ((MyInteger)functionAssignment.execute(program.getScope(),null)).getValue());
    }

    @Test
    public void tailIsReturningArrayWithoutFirstElement() throws Exception {
        String text = "def Int x = tail([1,2,3,4]);";
        Program program = parse(text);
        program.execute();
        assertEquals("First statement should be Function Assignment", FunctionAssignment.class.getName(),program.getStatement(0).getClass().getName());
        FunctionAssignment functionAssignment = (FunctionAssignment) program.getStatement(0);
        assertEquals("x should be an array [2,3,4]", "[2,3,4]", ((Array)functionAssignment.execute(program.getScope(),null)).toString());
    }

    @Test
    public void tailIsReturningEmptyArrayWhenGivenOneElementArray() throws Exception {
        String text = "def Int x = tail([1]);";
        Program program = parse(text);
        program.execute();
        assertEquals("First statement should be Function Assignment", FunctionAssignment.class.getName(),program.getStatement(0).getClass().getName());
        FunctionAssignment functionAssignment = (FunctionAssignment) program.getStatement(0);
        assertEquals("x should be an empty array", 0, ((Array)functionAssignment.execute(program.getScope(),null)).getElements().size());
    }

    @Test
    public void isEvaluatingIntegersGreaterThanZeroAsTrue() throws Exception {
        String text = "if( 1 ){}";
        Program program = parse(text);
        assertEquals("First statement should be If Statement", IfStatement.class,program.getStatement(0).getClass());
        IfStatement ifStatement = (IfStatement) program.getStatement(0);
        assertEquals("Condition should be true", true, ifStatement.getCondition().execute(program.getScope()).isTrue());
    }

    @Test
    public void isEvaluatingDoublesGreaterThanZeroAsTrue() throws Exception {
        String text = "if( 1.0 ){}";
        Program program = parse(text);
        assertEquals("First statement should be If Statement", IfStatement.class,program.getStatement(0).getClass());
        IfStatement ifStatement = (IfStatement) program.getStatement(0);
        assertEquals("Condition should be true", true, ifStatement.getCondition().execute(program.getScope()).isTrue());
    }

    @Test
    public void isEvaluatingIntegersLessThanZeroAsFalse() throws Exception {
        String text = "if( -1 ){}";
        Program program = parse(text);
        assertEquals("First statement should be If Statement",IfStatement.class,program.getStatement(0).getClass());
        IfStatement ifStatement = (IfStatement) program.getStatement(0);
        assertEquals("Condition should be true", false, ifStatement.getCondition().execute(program.getScope()).isTrue());
    }

    @Test
    public void isEvaluatingDoublesLessThanZeroAsFalse() throws Exception {
        String text = "if( -1.0 ){}";
        Program program = parse(text);
        assertEquals("First statement should be If Statement", IfStatement.class,program.getStatement(0).getClass());
        IfStatement ifStatement = (IfStatement) program.getStatement(0);
        assertEquals("Condition should be true", false, ifStatement.getCondition().execute(program.getScope()).isTrue());
    }

    @Test
    public void isEvaluatingZeroAsFalse() throws Exception {
        String text = "if( 0 ){}";
        Program program = parse(text);
        assertEquals("First statement should be If Statement", IfStatement.class,program.getStatement(0).getClass());
        IfStatement ifStatement = (IfStatement) program.getStatement(0);
        assertEquals("Condition should be true", false, ifStatement.getCondition().execute(program.getScope()).isTrue());
    }

    @Test
    public void isEvaluatingEqualityForSameIntegersAsTrue() throws Exception {
        String text = "if( 1 == 1 ){}";
        Program program = parse(text);
        assertEquals("First statement should be If Statement", IfStatement.class,program.getStatement(0).getClass());
        IfStatement ifStatement = (IfStatement) program.getStatement(0);
        assertEquals("Condition should be true", true, ifStatement.getCondition().execute(program.getScope()).isTrue());
    }

    @Test
    public void isEvaluatingEqualityForSameDoublesAsTrue() throws Exception {
        String text = "if( 1.0 == 1.0 ){}";
        Program program = parse(text);
        assertEquals("First statement should be If Statement", IfStatement.class,program.getStatement(0).getClass());
        IfStatement ifStatement = (IfStatement) program.getStatement(0);
        assertEquals("Condition should be true", true, ifStatement.getCondition().execute(program.getScope()).isTrue());
    }

    @Test
    public void isEvaluatingInEqualityForSameIntegersAsFalse() throws Exception {
        String text = "if( 1 != 1 ){}";
        Program program = parse(text);
        assertEquals("First statement should be If Statement", IfStatement.class,program.getStatement(0).getClass());
        IfStatement ifStatement = (IfStatement) program.getStatement(0);
        assertEquals("Condition should be false", false, ifStatement.getCondition().execute(program.getScope()).isTrue());
    }

    @Test
    public void isEvaluatingInEqualityForSameDoublesAsFalse() throws Exception {
        String text = "if( 1.0 != 1.0 ){}";
        Program program = parse(text);
        assertEquals("First statement should be If Statement", IfStatement.class,program.getStatement(0).getClass());
        IfStatement ifStatement = (IfStatement) program.getStatement(0);
        assertEquals("Condition should be false", false, ifStatement.getCondition().execute(program.getScope()).isTrue());
    }

    @Test
    public void isEvaluatingInEqualityForDifferentDoublesAsTrue() throws Exception {
        String text = "if( 1.0 != 2.0 ){}";
        Program program = parse(text);
        assertEquals("First statement should be If Statement", IfStatement.class,program.getStatement(0).getClass());
        IfStatement ifStatement = (IfStatement) program.getStatement(0);
        assertEquals("Condition should be true", true, ifStatement.getCondition().execute(program.getScope()).isTrue());
    }

    @Test
    public void isEvaluatingInEqualityForDifferentIntegersAsTrue() throws Exception {
        String text = "if( 1 != 2 ){}";
        Program program = parse(text);
        assertEquals("First statement should be If Statement", IfStatement.class,program.getStatement(0).getClass());
        IfStatement ifStatement = (IfStatement) program.getStatement(0);
        assertEquals("Condition should be true", true, ifStatement.getCondition().execute(program.getScope()).isTrue());
    }

    @Test
    public void isEvaluatingInEqualityForDifferentIntegerAndDoubleAsTrue() throws Exception {
        String text = "if( 1 != 2.0 ){}";
        Program program = parse(text);
        assertEquals("First statement should be If Statement", IfStatement.class,program.getStatement(0).getClass());
        IfStatement ifStatement = (IfStatement) program.getStatement(0);
        assertEquals("Condition should be true", true, ifStatement.getCondition().execute(program.getScope()).isTrue());
    }

    @Test
    public void isEvaluatingInEqualityForSameIntegerAndDoubleAsFalse() throws Exception {
        String text = "if( 1 != 1.0 ){}";
        Program program = parse(text);
        assertEquals("First statement should be If Statement", IfStatement.class,program.getStatement(0).getClass());
        IfStatement ifStatement = (IfStatement) program.getStatement(0);
        assertEquals("Condition should be false", false, ifStatement.getCondition().execute(program.getScope()).isTrue());
    }

    @Test
    public void isEvaluatingGreaterThanAsTrue() throws Exception {
        String text = "if( 1 > 0 ){}";
        Program program = parse(text);
        assertEquals("First statement should be If Statement", IfStatement.class,program.getStatement(0).getClass());
        IfStatement ifStatement = (IfStatement) program.getStatement(0);
        assertEquals("Condition should be true", true, ifStatement.getCondition().execute(program.getScope()).isTrue());
    }

    @Test
    public void isEvaluatingGreaterEqualsThanAsTrue() throws Exception {
        String text = "if( 1 >= 0 ){}";
        Program program = parse(text);
        assertEquals("First statement should be If Statement", IfStatement.class,program.getStatement(0).getClass());
        IfStatement ifStatement = (IfStatement) program.getStatement(0);
        assertEquals("Condition should be true", true, ifStatement.getCondition().execute(program.getScope()).isTrue());
    }

    @Test
    public void isEvaluatingLessThanAsTrue() throws Exception {
        String text = "if( -1 < 1 ){}";
        Program program = parse(text);
        assertEquals("First statement should be If Statement", IfStatement.class,program.getStatement(0).getClass());
        IfStatement ifStatement = (IfStatement) program.getStatement(0);
        assertEquals("Condition should be true", true, ifStatement.getCondition().execute(program.getScope()).isTrue());
    }

    @Test
    public void isEvaluatingLessEqualsThanAsTrue() throws Exception {
        String text = "if( -1 <= 1 ){}";
        Program program = parse(text);
        assertEquals("First statement should be If Statement", IfStatement.class,program.getStatement(0).getClass());
        IfStatement ifStatement = (IfStatement) program.getStatement(0);
        assertEquals("Condition should be true", true, ifStatement.getCondition().execute(program.getScope()).isTrue());
    }

    @Test
    public void isEvaluatingLessOrEqualsForSameIntsAsTrue() throws Exception {
        String text = "if( -1 <= -1 ){}";
        Program program = parse(text);
        assertEquals("First statement should be If Statement", IfStatement.class,program.getStatement(0).getClass());
        IfStatement ifStatement = (IfStatement) program.getStatement(0);
        assertEquals("Condition should be true", true, ifStatement.getCondition().execute(program.getScope()).isTrue());
    }

    @Test
    public void isEvaluatingAndOfTwoTruesAsTrue() throws Exception {
        String text = "if( 1 && 1 ){}";
        Program program = parse(text);
        assertEquals("First statement should be If Statement", IfStatement.class,program.getStatement(0).getClass());
        IfStatement ifStatement = (IfStatement) program.getStatement(0);
        assertTrue("Condition should be true", ifStatement.getCondition().execute(program.getScope()).isTrue());
    }

    @Test
    public void isEvaluatingAndOfTrueAndFalseAsFalse() throws Exception {
        String text = "if( 1 && 0 ){}";
        Program program = parse(text);
        assertEquals("First statement should be If Statement", IfStatement.class,program.getStatement(0).getClass());
        IfStatement ifStatement = (IfStatement) program.getStatement(0);
        assertFalse("Condition should be false", ifStatement.getCondition().execute(program.getScope()).isTrue());
    }

    @Test
    public void isEvaluatingAndOfTrueFalsesAsFalse() throws Exception {
        String text = "if( -1 && 0 ){}";
        Program program = parse(text);
        assertEquals("First statement should be If Statement", IfStatement.class,program.getStatement(0).getClass());
        IfStatement ifStatement = (IfStatement) program.getStatement(0);
        assertFalse("Condition should be false", ifStatement.getCondition().execute(program.getScope()).isTrue());
    }

    @Test
    public void isEvaluatingOrOfTrueAndFalseAsTrue() throws Exception {
        String text = "if( 1 || 0 ){}";
        Program program = parse(text);
        assertEquals("First statement should be If Statement", IfStatement.class,program.getStatement(0).getClass());
        IfStatement ifStatement = (IfStatement) program.getStatement(0);
        assertTrue("Condition should be true", ifStatement.getCondition().execute(program.getScope()).isTrue());
    }

    @Test
    public void isEvaluatingOrOfTwoTruesAsTrue() throws Exception {
        String text = "if( 1 || 1 ){}";
        Program program = parse(text);
        assertEquals("First statement should be If Statement", IfStatement.class,program.getStatement(0).getClass());
        IfStatement ifStatement = (IfStatement) program.getStatement(0);
        assertTrue("Condition should be true", ifStatement.getCondition().execute(program.getScope()).isTrue());
    }

    @Test
    public void isEvaluatingOrOfTwoFalsesAsFalse() throws Exception {
        String text = "if( -1 || 0 ){}";
        Program program = parse(text);
        assertEquals("First statement should be If Statement", IfStatement.class,program.getStatement(0).getClass());
        IfStatement ifStatement = (IfStatement) program.getStatement(0);
        assertFalse("Condition should be false", ifStatement.getCondition().execute(program.getScope()).isTrue());
    }

    @Test
    public void isEvaluatingAlternativeOfTrueAndFalseAsTrue() throws Exception {
        String text = "if( 1 || 0 && 1 ){}";
        Program program = parse(text);
        assertEquals("First statement should be If Statement", IfStatement.class,program.getStatement(0).getClass());
        IfStatement ifStatement = (IfStatement) program.getStatement(0);
        assertTrue("Condition should be true", ifStatement.getCondition().execute(program.getScope()).isTrue());
    }

    @Test
    public void isEvaluatingParenthesisAlternativeFirstAndReturnsTrue() throws Exception {
        String text = "if((1 || 0 ) && 1 ){}";
        Program program = parse(text);
        assertEquals("First statement should be If Statement", IfStatement.class,program.getStatement(0).getClass());
        IfStatement ifStatement = (IfStatement) program.getStatement(0);
        assertTrue("Condition should be true", ifStatement.getCondition().execute(program.getScope()).isTrue());
    }

    @Test
    public void isReturningFalseWhenComparingIntToDifferentDouble() throws Exception {
        String text = "if( 1 == 1.4 ){}";
        Program program = parse(text);
        assertEquals("First statement should be If Statement", IfStatement.class,program.getStatement(0).getClass());
        IfStatement ifStatement = (IfStatement) program.getStatement(0);
        assertFalse("Condition should be false", ifStatement.getCondition().execute(program.getScope()).isTrue());
    }

    @Test(expected = MyRunTimeException.class)
    public void isThrowingExceptionWhenComparingIntToString() throws Exception {
        String text = "if( 1 == \"1.4\" ){}";
        Program program = parse(text);
        assertEquals("First statement should be If Statement", IfStatement.class,program.getStatement(0).getClass());
        IfStatement ifStatement = (IfStatement) program.getStatement(0);
        ifStatement.execute(program.getScope());
    }

    @Test(expected = MyRunTimeException.class)
    public void isThrowingExceptionWhenComparingDoubleToString() throws Exception {
        String text = "if( 1.0 == \"1.4\" ){}";
        Program program = parse(text);
        assertEquals("First statement should be If Statement", IfStatement.class,program.getStatement(0).getClass());
        IfStatement ifStatement = (IfStatement) program.getStatement(0);
        ifStatement.execute(program.getScope());
    }

    @Test
    public void isEvaluatingParenthesisAlternativeFirstAndReturnsFalse() throws Exception {
        String text = "if((1 || 0 ) && 0 ){}";
        Program program = parse(text);
        assertEquals("First statement should be If Statement", IfStatement.class,program.getStatement(0).getClass());
        IfStatement ifStatement = (IfStatement) program.getStatement(0);
        assertFalse("Condition should be false", ifStatement.getCondition().execute(program.getScope()).isTrue());
    }

    @Test(expected = MyRunTimeException.class)
    public void isThrowingExceptionWhenCallingFunctionFromDeeperScope() throws Exception {
        String text = "def Int x(){\n" +
                "        if( 2 > 1  ) {\n" +
                "            def Int c = 2;\n" +
                "            if( 2 >1 ){\n" +
                "                def Int d = 4;\n" +
                "            }\n" +
                "            return d;\n" +
                "        }\n" +
                "        return 3;\n" +
                "    }" +
                "x"; //function call
        Program program = parse(text);
        program.execute();
    }

    @Test
    public void testIfPrintsInteger() throws Exception {
        String text = "print(1);"; //function call
        Program program = parse(text);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        program.execute();
        assertEquals("1", outContent.toString());
    }

    @Test
    public void testIfPrintsDouble() throws Exception {
        String text = "print(1.34);"; //function call
        Program program = parse(text);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        program.execute();
        assertEquals("1.34", outContent.toString());
    }

    @Test
    public void testIfPrintsString() throws Exception {
        String text = "print(\"1\");"; //function call
        Program program = parse(text);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        program.execute();
        assertEquals("1", outContent.toString());
    }

    @Test
    public void testIfPrintsStringWithEscapeChars() throws Exception {
        String text = "print(\"1\n\t\");"; //function call
        Program program = parse(text);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        program.execute();
        assertEquals("1\n\t", outContent.toString());
    }

    @Test
    public void testIfPrintsConcatenatedNumbersAndString() throws Exception {
        String text = "print(1 + \" is a relatively small number\");"; //function call
        Program program = parse(text);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        program.execute();
        assertEquals("1 is a relatively small number", outContent.toString());
    }

    @Test
    public void testIfPrintsConcatenatedStringAndNumbers() throws Exception {
        String text = "print(\"A relatively small number is \" + 1);"; //function call
        Program program = parse(text);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        program.execute();
        assertEquals("A relatively small number is 1", outContent.toString());
    }

    @Test(expected = MyRunTimeException.class)
    public void isThrowingExceptionWhenSubstractingFromString() throws Exception {
        String text = "print(\"string\" -1 )"; //function call
        Program program = parse(text);
        program.execute();
    }

    @Test(expected = MyRunTimeException.class)
    public void isThrowingExceptionWhenMultiplyingString() throws Exception {
        String text = "print(\"string\"*1 )"; //function call
        Program program = parse(text);
        program.execute();
    }

    @Test(expected = MyRunTimeException.class)
    public void isThrowingExceptionWhenDividingString() throws Exception {
        String text = "print(\"string\"/1 )"; //function call
        Program program = parse(text);
        program.execute();
    }

    @Test
    public void isAddingInts() throws Exception {
        String text = "def Int x = 1 + 1;";
        Program program = parse(text);
        program.execute();
        assertEquals("First statement should be Function Assignment", FunctionAssignment.class.getName(),program.getStatement(0).getClass().getName());
        FunctionAssignment functionAssignment = (FunctionAssignment) program.getStatement(0);
        assertEquals("x should be literal 2", new MyInteger(2).getValue(), ((MyInteger)functionAssignment.execute(program.getScope(),null)).getValue());
    }

    @Test
    public void isAddingDoubleToIntWhenNoRemainer() throws Exception {
        String text = "def Int x = 1 + 1.0;" +
                "x;";
        Program program = parse(text);
        program.execute();
        assertEquals("First statement should be Function Assignment", FunctionAssignment.class.getName(),program.getStatement(0).getClass().getName());
        FunctionAssignment functionAssignment = (FunctionAssignment) program.getStatement(0);
        assertEquals("x should be literal 2", new MyInteger(2).getValue(), ((MyInteger)functionAssignment.execute(program.getScope(),null)).getValue());
    }

    @Test
    public void isSubstractingInts() throws Exception {
        String text = "def Int x = 2 - 1;";
        Program program = parse(text);
        program.execute();
        assertEquals("First statement should be Function Assignment", FunctionAssignment.class.getName(),program.getStatement(0).getClass().getName());
        FunctionAssignment functionAssignment = (FunctionAssignment) program.getStatement(0);
        assertEquals("x should be literal 1", new MyInteger(1).getValue(), ((MyInteger)functionAssignment.execute(program.getScope(),null)).getValue());
    }

    @Test
    public void isSubstractingDoubleFromInt() throws Exception {
        String text = "def Int x = 2 - 1.0;";
        Program program = parse(text);
        program.execute();
        assertEquals("First statement should be Function Assignment", FunctionAssignment.class.getName(),program.getStatement(0).getClass().getName());
        FunctionAssignment functionAssignment = (FunctionAssignment) program.getStatement(0);
        assertEquals("x should be literal 1", new MyInteger(1).getValue(), ((MyInteger)functionAssignment.execute(program.getScope(),null)).getValue());
    }

    @Test(expected = MyRunTimeException.class)
    public void isThrownigExceptionWhenSubstractingDoubleFromInt() throws Exception {
        String text = "def Int x = 2 - 1.4;" +
                "x;";
        Program program = parse(text);
        program.execute();
    }

    @Test(expected = MyRunTimeException.class)
    public void isThrownigExceptionWhenSubstractingStringFromInt() throws Exception {
        String text = "def Int x = 2 - \"1\";" +
                "x;";
        Program program = parse(text);
        program.execute();
    }

    @Test(expected = MyRunTimeException.class)
    public void isThrownigExceptionWhenSubstractingStringFromDouble() throws Exception {
        String text = "def Int x = 2.0 - \"1\";" +
                "x;";
        Program program = parse(text);
        program.execute();
    }

    @Test(expected = MyRunTimeException.class)
    public void isThrownigExceptionWhenMultiplyingIntByString() throws Exception {
        String text = "def Int x = 2 * \"1\";" +
                "x;";
        Program program = parse(text);
        program.execute();
    }

    @Test(expected = MyRunTimeException.class)
    public void isThrownigExceptionWhenDividingIntByNaN() throws Exception {
        String text = "def Int x = 2 / \"1\";" +
                "x;";
        Program program = parse(text);
        program.execute();
    }

    @Test(expected = MyRunTimeException.class)
    public void isThrownigExceptionWhenDividingDoubleByNaN() throws Exception {
        String text = "def Double x = 2.0 / \"1\";" +
                "x;";
        Program program = parse(text);
        program.execute();
    }

    @Test
    public void isDividingInts() throws Exception {
        String text = "def Int x = 5 / 2 ;";
        Program program = parse(text);
        program.execute();
        assertEquals("First statement should be Function Assignment", FunctionAssignment.class.getName(),program.getStatement(0).getClass().getName());
        FunctionAssignment functionAssignment = (FunctionAssignment) program.getStatement(0);
        assertEquals("x should be literal 2", new MyInteger(2).getValue(), ((MyInteger)functionAssignment.execute(program.getScope(),null)).getValue());
    }

    @Test
    public void isDividingIntByDouble() throws Exception {
        String text = "def Int x = 5 / 2.5 ;";
        Program program = parse(text);
        program.execute();
        assertEquals("First statement should be Function Assignment", FunctionAssignment.class.getName(),program.getStatement(0).getClass().getName());
        FunctionAssignment functionAssignment = (FunctionAssignment) program.getStatement(0);
        assertEquals("x should be literal 2", new MyInteger(2).getValue(), ((MyInteger)functionAssignment.execute(program.getScope(),null)).getValue());
    }

    @Test
    public void isSubstractingFromIntFromDouble() throws Exception {
        String text = "def Int x = 2.4 - 1;";
        Program program = parse(text);
        program.execute();
        assertEquals("First statement should be Function Assignment", FunctionAssignment.class.getName(),program.getStatement(0).getClass().getName());
        FunctionAssignment functionAssignment = (FunctionAssignment) program.getStatement(0);
        assertEquals("x should be literal 1.4", new MyDouble(1.4).getValue(), ((MyDouble)functionAssignment.execute(program.getScope(),null)).getValue());
    }

    @Test
    public void isMultipyingInts() throws Exception {
        String text = "def Int x = 2*3;";
        Program program = parse(text);
        program.execute();
        assertEquals("First statement should be Function Assignment", FunctionAssignment.class.getName(),program.getStatement(0).getClass().getName());
        FunctionAssignment functionAssignment = (FunctionAssignment) program.getStatement(0);
        assertEquals("x should be literal 6", new MyInteger(6).getValue(), ((MyInteger)functionAssignment.execute(program.getScope(),null)).getValue());
    }

    @Test
    public void isMultipyingIntByDouble() throws Exception {
        String text = "def Int x = 2*3.3;";
        Program program = parse(text);
        program.execute();
        assertEquals("First statement should be Function Assignment", FunctionAssignment.class.getName(),program.getStatement(0).getClass().getName());
        FunctionAssignment functionAssignment = (FunctionAssignment) program.getStatement(0);
        assertEquals("x should be literal 6", new MyInteger(6).getValue(), ((MyInteger)functionAssignment.execute(program.getScope(),null)).getValue());
    }

    @Test
    public void isContaingParameterWell() throws Exception {
        String text = "def Int x(Int: a){ return 0;}";
        Program program = parse(text);
        program.execute();
        assertEquals("First statement should be Function Declaration", FunctionDeclaration.class.getName(),program.getStatement(0).getClass().getName());
        FunctionDeclaration functionDeclaration = (FunctionDeclaration) program.getStatement(0);
        Parameter expectedParam = new Parameter(new MyType(false,TokenType.INT_TYPE),"a");
        assertEquals("Should containg parameter a of type INT_TYPE", expectedParam.getType(), functionDeclaration.getParameters().get(0).getType());
        assertEquals("Should containg parameter a", expectedParam.getName(), functionDeclaration.getParameters().get(0).getName());
        assertEquals(expectedParam.getParameterType(), functionDeclaration.getParameters().get(0).getParameterType());
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
