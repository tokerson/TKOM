package semcheck;

public class MyRunTimeException extends Exception{

    private String message;

    public MyRunTimeException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage(){
        return "[RUNTIME_EXCEPTION] " + this.message;
    }
}
