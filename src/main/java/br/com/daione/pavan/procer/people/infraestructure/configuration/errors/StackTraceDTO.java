package br.com.daione.pavan.procer.people.infraestructure.configuration.errors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class StackTraceDTO {
    private String className;
    private String methodName;
    private String fileName;
    private int lineNumber;
}