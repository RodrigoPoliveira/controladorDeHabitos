package br.com.fiap.ControladorHabitos.exception;

public class BusinessValidationException extends RuntimeException{

    public BusinessValidationException(String msg) {
        super(msg);
    }
}
