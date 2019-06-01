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
                    new FileInputStream( new File(args[0]))
            );

            Lexer lexer = new Lexer(inputStreamReader);
            Parser parser = new Parser(lexer);
            Program program;
            try {
                program = parser.parse();
                program.execute();

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        } catch (FileNotFoundException e) {
            System.out.println("Error Loading the file");
        }
    }
}
