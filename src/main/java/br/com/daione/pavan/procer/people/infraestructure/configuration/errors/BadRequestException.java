package br.com.daione.pavan.procer.people.infraestructure.configuration.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException{
    public BadRequestException(String erro) {
        super(erro);
    }
}
