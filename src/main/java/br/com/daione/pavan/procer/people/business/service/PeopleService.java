package br.com.daione.pavan.procer.people.business.service;

import br.com.daione.pavan.procer.people.api.request.PeopleRequest;
import br.com.daione.pavan.procer.people.api.response.PeopleResponse;
import org.springframework.data.domain.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PeopleService {

    Mono<PeopleResponse> create(PeopleRequest request);

    Mono<Void> delete(Long id);

    Flux<PeopleResponse> list();

    Mono<PeopleResponse> findById(Long id);

    Mono<PeopleResponse> update(PeopleRequest request, Long id);

    Mono<PeopleResponse> findByCPf(String cpf);

    Flux<PeopleResponse> findByFirstName(String firstName);

    Flux<PeopleResponse>findByLastName(String lastName);

    Flux<PeopleResponse> findByIsActivated(boolean isActivated);

    Mono<Page<PeopleResponse>> fingByPaged(int page, int size);
}
