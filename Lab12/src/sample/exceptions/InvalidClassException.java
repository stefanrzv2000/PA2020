package sample.exceptions;

public class InvalidClassException extends RuntimeException {
    private String classname;

    public InvalidClassException(String classname){
        this.classname = classname;
    }

    @Override
    public String toString() {
        return "The class " + classname + " is not accepted!";
    }
}
