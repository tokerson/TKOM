import lexer.Lexer;
import program.Program;
import parser.Parser;
import parser.ParserException;

import java.io.*;

public class Main {

    public static void main(String[] args) {
        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(
                    new FileInputStream( new File("src/main/resources/test4.txt"))
            );

        } catch (FileNotFoundException e) {
//            e.printStackTrace();
        }

        Lexer lexer = new Lexer(inputStreamReader);
        Parser parser = new Parser(lexer);
        Program program;
        try {
            program = parser.parse();
            program.execute();

        } catch (ParserException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
