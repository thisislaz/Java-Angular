package net.thisislaz.angularwithspringboot.exceptions;

public class ApiException  extends RuntimeException{

    public ApiException(String message) {
        super(message);
    }
}
