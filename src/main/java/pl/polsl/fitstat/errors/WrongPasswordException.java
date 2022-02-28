package pl.polsl.fitstat.errors;

public class WrongPasswordException extends RuntimeException{

    public WrongPasswordException(String message){
        super(message);
    }
}
