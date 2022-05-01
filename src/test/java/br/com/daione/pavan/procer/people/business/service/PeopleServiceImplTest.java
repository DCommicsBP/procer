package br.com.daione.pavan.procer.people.business.service;

import br.com.daione.pavan.procer.people.business.service.data.PeopleData;
import br.com.daione.pavan.procer.people.infraestructure.entity.PeopleEntity;
import br.com.daione.pavan.procer.people.infraestructure.repository.PeopleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PeopleServiceImplTest {

    @Mock
    PeopleRepository repository;
    @InjectMocks
    PeopleServiceImpl service;


    @Test
    void searchAll() {
        Flux<PeopleEntity> peopleEntityFlux = PeopleData.listPeopleEntities();
        BDDMockito.when(repository.findAll()).thenReturn(peopleEntityFlux);

        Flux<PeopleEntity> peopleServiceReturn = service.searchAll();

        assertEquals(peopleServiceReturn.count().block(), peopleEntityFlux.count().block());
        BDDMockito.verify(repository).findAll();

    }

    @Test
    void searchPaginated() {
        Flux<PeopleEntity> peopleEntityFlux = PeopleData.listPeopleEntities();
        BDDMockito.when(repository.findAllBy(Pageable.unpaged())).thenReturn(peopleEntityFlux);

        Flux<PeopleEntity> peopleServiceReturn = service.searchPaginated(Pageable.unpaged());

        assertEquals(peopleServiceReturn.count().block(), peopleEntityFlux.count().block());
        BDDMockito.verify(repository).findAllBy(Pageable.unpaged());

    }

    @Test
    void searchById() {
        Mono<PeopleEntity> peopleEntity = PeopleData.firstData1();
        final Long id = peopleEntity.block().getId();

        BDDMockito.when(repository.findById(id)).thenReturn(peopleEntity);

        Mono<PeopleEntity> peopleServiceReturn = service.searchById(id);

        assertEquals(peopleServiceReturn.block().getId(), id);
        BDDMockito.verify(repository).findById(id);
    }

    @Test
    void searchByCpf() {

        Mono<PeopleEntity> peopleEntity = PeopleData.firstData1();
        final String cpf = peopleEntity.block().getCpf();

        BDDMockito.when(repository.findByCpf(cpf)).thenReturn(peopleEntity);

        Mono<PeopleEntity> peopleServiceReturn = service.searchByCpf(cpf);

        assertEquals(peopleServiceReturn.block().getCpf(), cpf);
        BDDMockito.verify(repository).findByCpf(cpf);
    }

    @Test
    void searchByEmail() {
        Mono<PeopleEntity> peopleEntity = PeopleData.firstData1();
        final String email = peopleEntity.block().getEmail();

        BDDMockito.when(repository.findByEmail(email)).thenReturn(peopleEntity);

        Mono<PeopleEntity> peopleServiceReturn = service.searchByEmail(email);

        assertEquals(peopleServiceReturn.block().getEmail(), email);
        BDDMockito.verify(repository).findByEmail(email);
    }

    @Test
    void searchByFirstName() {
        Mono<PeopleEntity> peopleEntity = PeopleData.firstData1();
        final String email = peopleEntity.block().getEmail();

        BDDMockito.when(repository.findByEmail(email)).thenReturn(peopleEntity);

        Mono<PeopleEntity> peopleServiceReturn = service.searchByEmail(email);

        assertEquals(peopleServiceReturn.block().getEmail(), email);
        BDDMockito.verify(repository).findByEmail(email);
    }

    @Test
    void searchByLastName() {
        Flux<PeopleEntity> peopleEntityFlux = Flux.from(PeopleData.firstData1());
        String lastName = PeopleData.firstData1().block().getLastName();
        final Long count = peopleEntityFlux.count().block();

        BDDMockito.when(repository.findByLastName(lastName)).thenReturn(peopleEntityFlux);

        Flux<PeopleEntity> peopleServiceReturn = service.searchByLastName(lastName);

        assertEquals(count, peopleServiceReturn.count().block());
        BDDMockito.verify(repository).findByLastName(lastName);
    }

    @Test
    void searchByActive() {
        Flux<PeopleEntity> peopleEntityFlux = Flux.from(PeopleData.firstData1());
        boolean isActive = PeopleData.firstData1().block().isActivated();
        final Long count = peopleEntityFlux.count().block();

        BDDMockito.when(repository.findByIsActivated(isActive)).thenReturn(peopleEntityFlux);

        Flux<PeopleEntity> peopleServiceReturn = service.searchByActive(isActive);

        assertEquals(count, peopleServiceReturn.count().block());
        BDDMockito.verify(repository).findByIsActivated(isActive);
    }

    @Test
    void create() {
        PeopleEntity peopleEntity = PeopleData.firstData1().block();

        Mono<PeopleEntity> peopleEntityMono = PeopleData.firstData1();

        BDDMockito.when(repository.save(peopleEntity)).thenReturn(peopleEntityMono);

        Mono<PeopleEntity> peopleServiceReturn = service.create(peopleEntity);

        peopleServiceReturn.doOnSuccess(element -> {
            Assertions.assertEquals(element.getLastName(), peopleEntity);
        });

        BDDMockito.verify(repository).save(peopleEntity);
    }

}