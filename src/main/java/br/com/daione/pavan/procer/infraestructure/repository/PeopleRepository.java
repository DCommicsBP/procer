package br.com.daione.pavan.procer.infraestructure.repository;

import br.com.daione.pavan.procer.infraestructure.entity.PeopleEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface PeopleRepository extends ReactiveCrudRepository<PeopleEntity, Long> {
    Flux<PeopleEntity> findAllBy(Pageable pageable);
    Mono<PeopleEntity> findByCpf(String cpf);
    Mono<PeopleEntity> findByEmail(String email);
    Flux<PeopleEntity> findByFirstName(String firstName);
    Flux<PeopleEntity> findByLastName(String lastName);
    Flux<PeopleEntity> findByIsActivated(boolean isActivated);

}
