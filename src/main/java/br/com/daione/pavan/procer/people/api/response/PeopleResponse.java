package br.com.daione.pavan.procer.people.api.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PeopleResponse {

    private Long id;
    private String cpfReturn;
    private String emailReturn;
    private String firstNameReturn;
    private boolean isActivatedReturn;
    private boolean isNewReturn;
    private String isLastNameReturn;

}
