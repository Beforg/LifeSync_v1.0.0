package br.com.myegoo.app.myego.utils.exception;

public class TratadorDeErros extends RuntimeException{
    public TratadorDeErros(String message) {
        super(message);
    }
}
