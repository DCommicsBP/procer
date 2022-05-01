package br.com.daione.pavan.procer.people.infraestructure.repository;

import br.com.daione.pavan.procer.people.infraestructure.entity.PeopleEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface PeopleRepository extends ReactiveCrudRepository<PeopleEntity, Long> {
    Flux<PeopleEntity> findAllBy(Pageable pageable);

}
