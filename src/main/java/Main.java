import lexer.Lexer;
import lexer.LexerException;
import model.Node;
import model.Program.Program;
import model.Token.Token;
import model.Token.TokenType;
import parser.Parser;

import java.io.*;

public class Main {

    public static void main(String[] args) {
        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(
                    new FileInputStream( new File("src/main/resources/test.txt"))
            );

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Lexer lexer = new Lexer(inputStreamReader);
        Parser parser = new Parser(lexer);
        Program program;
        try {
            program = parser.parse();
            for (Node statement: program.getStatements()) {
                System.out.println(statement);
            }

        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }


//        Lexer lexer = new Lexer(inputStreamReader);
//        Token token = null;
//        try {
//            token = lexer.getNextToken();
//            while(token.getTokenType() != TokenType.END ) {
//                System.out.println(token.getContent().concat(" " + token.getTokenType().toString() + " line: " + token.getTextPosition().getLineNumber() + " char: " + token.getTextPosition().getCharacterNumber()));
//                token = lexer.getNextToken();
//            }
//            System.out.println(token.getContent().concat(" " + token.getTokenType().toString()));
//
//        } catch (LexerException e) {
//            e.printStackTrace();
//            System.out.println(e.toString());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


    }
}
