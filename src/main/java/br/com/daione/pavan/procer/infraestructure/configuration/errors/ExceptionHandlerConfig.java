package br.com.daione.pavan.procer.infraestructure.configuration.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestControllerAdvice
public class ExceptionHandlerConfig {


    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO notFoundError(NotFoundException ex) {
        return errorBuilder(ex, HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public ErrorDTO badGatewayError(BadRequestException ex) {
        return errorBuilder(ex, HttpStatus.BAD_GATEWAY.value());
    }

    @ExceptionHandler(InternalServerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDTO badGatewayError(InternalServerException ex) {
        return errorBuilder(ex, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }



    private ErrorDTO errorBuilder(Exception ex, int statusCode) {
        List<StackTraceDTO> stackTraceDTOList = new ArrayList<>();
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setTimeException(LocalDateTime.now());
        errorDTO.setMessage(ex.getMessage());
        if(statusCode != 0)
            errorDTO.setStatusCode(statusCode);

        Arrays.stream(ex.getStackTrace()).forEach(element-> stackTraceDTOList.add(StackTraceDTO.builder()
                .className(element.getClassName())
                .fileName(element.getFileName())
                .lineNumber(element.getLineNumber())
                .methodName(element.getMethodName())
                .build()));

        return errorDTO;
    }
}
