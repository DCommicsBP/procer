package br.com.daione.pavan.procer.people.business.service;

import br.com.daione.pavan.procer.people.infraestructure.entity.PeopleEntity;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PeopleService {
    Mono<PeopleEntity> create(PeopleEntity entity);
    Mono<PeopleEntity> searchById(Long id);
    Mono<PeopleEntity> searchByCpf(String cpf);
    Mono<PeopleEntity> searchByEmail(String email);
    Flux<PeopleEntity> searchAll();
    Flux<PeopleEntity> searchPaginated(Pageable pageable);
    Flux<PeopleEntity> searchByFirstName(String firstName);
    Flux<PeopleEntity> searchByLastName(String lastName);
    Flux<PeopleEntity> searchByActive(boolean isActive);
    Mono<Void> deleteById(Long id);
    Mono<Long> count();

}
