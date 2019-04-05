import data.TokenType;
import lexer.Lexer;
import data.Token;

import java.io.*;

public class Main {

    public static void main(String[] args) throws Exception {
        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(
                    new FileInputStream( new File("src/main/resources/sample-code.txt"))
            );

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Lexer lexer = new Lexer(inputStreamReader);


        Token token = lexer.getNextToken();
        while(token.getTokenType() != TokenType.END ) {
            System.out.println(token.getContent().concat(" "+token.getTokenType().toString()));
            token = lexer.getNextToken();
        }
        System.out.println(token.getContent().concat(" "+token.getTokenType().toString()));
    }
}
