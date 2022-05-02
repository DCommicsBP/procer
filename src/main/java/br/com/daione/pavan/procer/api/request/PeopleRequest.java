package br.com.daione.pavan.procer.api.request;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PeopleRequest {

    @NotEmpty(message = "Você deve fornecer um CPF.")
    private String newCpf;
    @NotEmpty(message = "Você deve fornecer um email.")
    private String newEmail;
    @NotEmpty(message = "Você deve fornecer um nome.")
    private String newFirstName;
    @NotNull(message = "Você deve informar se o registro estará ativo.")
    private boolean newIsActivated;
    @NotNull(message = "Você deve informar o sobrenome.")
    private String newIsLastName;
}
