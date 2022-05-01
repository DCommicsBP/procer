package br.com.daione.pavan.procer.people.business;

import br.com.daione.pavan.procer.people.api.request.PeopleRequest;
import br.com.daione.pavan.procer.people.api.response.PeopleResponse;
import br.com.daione.pavan.procer.people.business.converter.PeopleConverter;
import br.com.daione.pavan.procer.people.business.service.PeopleService;
import br.com.daione.pavan.procer.people.infraestructure.entity.PeopleEntity;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class PeopleBusiness {

    private PeopleService service;

    public Mono<PeopleResponse> create(PeopleRequest request) {
        PeopleEntity peopleEntity = PeopleConverter.requestToEntity(request, true);
        return service.create(peopleEntity)
                .map(PeopleConverter::entityToResponse);

    }

    public Mono<Void> delete(Long id) {
        return service.deleteById(id);
    }

    public Flux<PeopleResponse> list() {
        return service.searchAll()
                .map(PeopleConverter::entityToResponse);
    }

    public Mono<PeopleResponse> findById(Long id) {
        return service.searchById(id)
                .map(PeopleConverter::entityToResponse);
    }

    public Mono<PeopleResponse> update(PeopleRequest request) {
        PeopleEntity peopleEntity = PeopleConverter.requestToEntity(request, false);
        return service.create(peopleEntity)
                .map(PeopleConverter::entityToResponse);
    }

    public Mono<PeopleResponse> findByCPf(String cpf) {
        return service.searchByCpf(cpf)
                .map(PeopleConverter::entityToResponse);
    }

    public Flux<PeopleResponse> findByFirstName(String firstName) {
        return service.searchByFirstName(firstName)
                .map(PeopleConverter::entityToResponse);
    }

    public Flux<PeopleResponse> findByLastName(String lastName) {
        return service.searchByFirstName(lastName)
                .map(PeopleConverter::entityToResponse);
    }

    public Flux<PeopleResponse> findByIsActivated(boolean isActivated) {
       return service.searchByActive(isActivated)
                .map(PeopleConverter::entityToResponse);
    }

    public Mono<Page<PeopleResponse>> fingByPaged(int page, int size){

        PageRequest pageRequest = PageRequest.of(page, size).withSort(Sort.by("firstName").ascending());

        return service.searchPaginated(pageRequest)
                .doOnError(element-> element.getCause())
                .collectList()
                .zipWith(service.count())
                .map(element-> new PageImpl<>(element.getT1(), pageRequest, element.getT2())
                        .map(PeopleConverter::entityToResponse));
    }


}
