package data;

public class TextPosition {
    private int lineNumber;
    private int characterNumber;

    public TextPosition() {
        this(1,1);
    }

    public TextPosition(int lineNumber, int characterNumber) {
        this.lineNumber = lineNumber;
        this.characterNumber = characterNumber;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setToNextLine(){
        this.lineNumber++;
        this.characterNumber = 1;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public void incrementLineNumber() {
        this.lineNumber++;
    }

    public void incrementCharacterNumber(){
        this.characterNumber++;
    }

    public int getCharacterNumber() {
        return characterNumber;
    }

    public void setCharacterNumber(int characterNumber) {
        this.characterNumber = characterNumber;
    }
}
