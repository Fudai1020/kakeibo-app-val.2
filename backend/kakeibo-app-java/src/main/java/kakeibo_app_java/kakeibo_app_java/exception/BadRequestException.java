package kakeibo_app_java.kakeibo_app_java.exception;

public class BadRequestException extends RuntimeException{
    public BadRequestException(String message){
        super(message);
    }
}
