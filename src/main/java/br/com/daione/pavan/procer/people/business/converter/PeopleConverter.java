package br.com.daione.pavan.procer.people.business.converter;

import br.com.daione.pavan.procer.people.api.request.PeopleRequest;
import br.com.daione.pavan.procer.people.api.response.PeopleResponse;
import br.com.daione.pavan.procer.people.infraestructure.entity.PeopleEntity;

public abstract class PeopleConverter {

    public static PeopleEntity requestToEntity(PeopleRequest request, boolean isNewFlag) {
            return PeopleEntity
                    .builder()
                    .cpf(request.getNewCpf())
                    .email(request.getNewEmail())
                    .firstName(request.getNewFirstName())
                    .isNew(isNewFlag)
                    .isActivated(request.isNewIsActivated())
                    .lastName(request.getNewIslastName())
                    .build();
    }

    public static PeopleResponse entityToResponse(PeopleEntity entity) {
        return PeopleResponse.builder()
                .id(entity.getId())
                .isLastNameReturn(entity.getLastName())
                .cpfReturn(entity.getCpf())
                .emailReturn(entity.getEmail())
                .firstNameReturn(entity.getFirstName())
                .isActivatedReturn(entity.isActivated())
                .isNewReturn(entity.isNew())
                .build();

    }

}
