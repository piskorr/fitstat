package pl.polsl.fitstat.errors;

public class NoPermissionToResourceException extends RuntimeException{

    public NoPermissionToResourceException(String message){
        super(message);
    }
}
