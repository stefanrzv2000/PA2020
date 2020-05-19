package sample.exceptions;

public class InvalidMethodException extends RuntimeException{
    private String method;
    private String classname;


    public InvalidMethodException(String method, String classname) {
        this.method = method;
        this.classname = classname;
    }

    @Override
    public String toString() {
        return "The class " + classname + " does not have the " + method + " method.";
    }
}
