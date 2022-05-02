package br.com.daione.pavan.procer.people.infraestructure.configuration.errors;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class ErrorDTO {

    private String message;
    private int statusCode;
    private LocalDateTime timeException;
    private List<StackTraceDTO> stackTrace;
}
