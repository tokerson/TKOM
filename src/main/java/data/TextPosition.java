package data;

public class TextPosition implements Cloneable {
    private int lineNumber;
    private int characterNumber;

    public TextPosition() {
        this(1,0);
    }

    private TextPosition(int lineNumber, int characterNumber) {
        this.lineNumber = lineNumber;
        this.characterNumber = characterNumber;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setToNextLine(){
        this.lineNumber++;
        this.characterNumber = 0;
    }

    public void incrementCharacterNumber(){
        this.characterNumber++;
    }

    public int getCharacterNumber() {
        return characterNumber;
    }

    @Override
    public TextPosition clone() throws CloneNotSupportedException {
        return (TextPosition) super.clone();
    }
}
