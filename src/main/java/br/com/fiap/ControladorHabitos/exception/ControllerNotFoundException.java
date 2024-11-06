package br.com.fiap.ControladorHabitos.exception;

public class ControllerNotFoundException extends RuntimeException {

    public ControllerNotFoundException(String msg) {
        super(msg);
    }
}
