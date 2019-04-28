import model.Token.Token;
import lexer.Lexer;
import lexer.LexerException;
import model.Token.TokenType;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

import static org.junit.Assert.assertEquals;


public class LexerTests {

    @Test
    public void isRecognizingIdentifier() throws Exception {
        String text = "x";
        Token token = getTokenFromString(text);
        assertEquals("token's content should be x", "x", token.getContent());
        assertEquals("token's type should be IDENTIFIER", TokenType.IDENTIFIER, token.getTokenType());
    }

    @Test
    public void isRecognizingAddOperator() throws Exception {
        String text = "+";
        Token token = getTokenFromString(text);
        assertEquals("token's content should be + ", "+", token.getContent());
        assertEquals("token's type should be ADD_OPERATOR", TokenType.ADD_OPERATOR, token.getTokenType());
    }

    @Test
    public void isRecognizingInteger() throws Exception {
        String text = "123";
        Token token = getTokenFromString(text);
        assertEquals("token's content should be 123", "123", token.getContent());
        assertEquals("token's type should be INTEGER", TokenType.INTEGER, token.getTokenType());
    }

    @Test
    public void isRecognizingDouble() throws Exception {
        String text = "123.4";
        Token token = getTokenFromString(text);
        assertEquals("token's content should be 123.4", "123.4", token.getContent());
        assertEquals("token's type should be DOUBLE", TokenType.DOUBLE, token.getTokenType());
    }

    @Test
    public void isRecognizingIdentifierFollowedAndBegginingWithWhiteSpaces() throws Exception {
        String text = "   x   ";
        Token token = getTokenFromString(text);
        assertEquals("token's content should be x", "x", token.getContent());
        assertEquals("token's type should be IDENTIFIER", TokenType.IDENTIFIER, token.getTokenType());
    }

    @Test
    public void isRecognizingIntegerFollowedAndBegginingWithWhiteSpaces() throws Exception {
        String text = "   123  ";
        Token token = getTokenFromString(text);
        assertEquals("token's content should be 123", "123", token.getContent());
        assertEquals("token's type should be INTEGER", TokenType.INTEGER, token.getTokenType());
    }

    @Test
    public void isNotRecognizingDoubleFollowedAndBegginingWithWhiteSpaces() throws Exception {
        String text = "   123. 4  ";
        Token token = getTokenFromString(text);
        assertEquals("token's content should be 123.", "123.", token.getContent());
        assertEquals("token's type should be DOUBLE", TokenType.DOUBLE, token.getTokenType());
    }

    @Test
    public void isReturningMinusToken() throws Exception {
        String text = "-4";
        Token token = getTokenFromString(text);
        assertEquals("token's content should be -.", "-", token.getContent());
        assertEquals("token's type should be SUBSTRACT_OPERATOR", TokenType.SUBSTRACT_OPERATOR, token.getTokenType());
    }

    @Test
    public void isIgnoringComments() throws Exception {
        String text = "//comment  ";
        Token token = getTokenFromString(text);
        assertEquals("There should be no token", TokenType.END, token.getTokenType());
    }

    @Test
    public void isIdentifiyingTokenInNextLineAfterComment() throws Exception {
        String text = "//comment  \ndef";
        Token token = getTokenFromString(text);
        assertEquals("token's content should be def", "def", token.getContent());
        assertEquals("token's type should be FUNCTION_DECL", TokenType.FUNCTION_DECL, token.getTokenType());
    }

    @Test
    public void isIdentifiyingStrings() throws Exception {
        String text = "\"hello world\"";
        Token token = getTokenFromString(text);
        assertEquals("token's content should be \"hello world\"", "hello world", token.getContent());
        assertEquals("token's type should be STRING", TokenType.STRING, token.getTokenType());
    }

    @Test(expected = LexerException.class)
    public void isReturningUndefinedWhenStringIsNotClosed() throws Exception {
        String text = "\"hello world";
        Token token = getTokenFromString(text);
    }

    @Test
    public void isReturningProperMultiLineString() throws Exception {
        String text = "\"hello \n\n world\"";
        Token token = getTokenFromString(text);
        assertEquals("token's content should be \"hello \n\n world\"", "hello \n\n world", token.getContent());
        assertEquals("token's type should be STRING", TokenType.STRING, token.getTokenType());
    }

    @Test
    public void isReturningStringWithQuotesInside() throws Exception {
        String text = "\"hello \\\"world\\\"\"";
        Token token = getTokenFromString(text);
        assertEquals("token's content should be \"hello \\\"world\\\"\"", "hello \"world\"", token.getContent());
        assertEquals("token's type should be STRING", TokenType.STRING, token.getTokenType());
    }

    @Test
    public void isReturningStringWithBackslashesInside() throws Exception {
        String text = "\"hello \\world\\\"";
        Token token = getTokenFromString(text);
        assertEquals("token's content should be \"hello \\world\\\"", "hello \\world\\", token.getContent());
        assertEquals("token's type should be STRING", TokenType.STRING, token.getTokenType());
    }

    @Test
    public void isReturningEndTokenWhenTheFileIsEmpty() throws Exception {
        String text = "";
        Token token = getTokenFromString(text);
        assertEquals("token's type should be END", TokenType.END, token.getTokenType());
    }

    @Test
    public void isReturningEndTokenWhenTheFileIsFullOfNewLines() throws Exception {
        String text = "\n\n\n";
        Token token = getTokenFromString(text);
        assertEquals("token's content should be \"\"", "", token.getContent());
        assertEquals("token's type should be END", TokenType.END, token.getTokenType());
    }


    private Token getTokenFromString(String string) throws Exception {
        Lexer lexer = new Lexer(convertStringToInputStreamReader(string));
        return lexer.getNextToken();
    }

    private InputStreamReader convertStringToInputStreamReader(String string) {
        return new InputStreamReader(new ByteArrayInputStream(string.getBytes()));
    }
}
