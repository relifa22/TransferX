package lt.javau12.TransferX.exeptions;

public class ValidationException extends RuntimeException{
    public ValidationException(String message){
        super(message);
    }
}
