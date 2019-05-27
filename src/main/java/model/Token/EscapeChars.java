package model.Token;

import java.util.HashMap;

public class EscapeChars {
    public static final HashMap<Character, Character> ESCAPE_CHARS = new HashMap<Character, Character>() {{
        put('b', '\b');
        put('n', '\n');
        put('r', '\r');
        put('t', '\t');
        put('"', '\"');
        put('\\', '\\');
    }};
}
