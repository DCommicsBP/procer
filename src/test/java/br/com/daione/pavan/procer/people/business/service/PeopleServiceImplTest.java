package br.com.daione.pavan.procer.people.business.service;

import br.com.daione.pavan.procer.data.PeopleData;
import br.com.daione.pavan.procer.people.api.request.PeopleRequest;
import br.com.daione.pavan.procer.people.api.response.PeopleResponse;
import br.com.daione.pavan.procer.people.business.converter.PeopleConverter;
import br.com.daione.pavan.procer.people.infraestructure.entity.PeopleEntity;
import br.com.daione.pavan.procer.people.infraestructure.repository.PeopleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PeopleServiceImplTest {

    @Mock
    PeopleRepository repository;
    @InjectMocks
    @Spy
    PeopleServiceImpl service;


    @Test
    void searchAll() {
        Flux<PeopleResponse> response = PeopleData.listPeopleEntities().map(PeopleConverter::entityToResponse);
        Flux<PeopleEntity> entity = PeopleData.listPeopleEntities();
        BDDMockito.when(repository.findAll()).thenReturn(entity);
        BDDMockito.when(service.list()).thenReturn(response);

        Flux<PeopleResponse> list = service.list();

        assertEquals(entity.count().block(), response.count().block());
        BDDMockito.verify(repository).findAll();

    }

    @Test
    void searchById() {
        Mono<PeopleEntity> peopleEntity = PeopleData.firstData1();
        Mono<PeopleResponse> peopleResponse = PeopleData.firstData1().map(PeopleConverter::entityToResponse);
        final Long id = peopleEntity.block().getId();

        BDDMockito.when(repository.findById(id)).thenReturn(peopleEntity);

        Mono<PeopleResponse> peopleServiceReturn = service.findById(id);

        assertEquals(peopleServiceReturn.block().getId(), id);
        BDDMockito.verify(repository).findById(id);
    }

    @Test
    void searchByCpf() {

        Mono<PeopleEntity> peopleEntity = PeopleData.firstData1();
        Mono<PeopleResponse> peopleResponse = PeopleData.firstData1()
                .map(PeopleConverter::entityToResponse);

        final String cpf = peopleEntity.block().getCpf();

        BDDMockito.when(repository.findByCpf(cpf)).thenReturn(peopleEntity);

        Mono<PeopleResponse> peopleServiceReturn = service.findByCPf(cpf);

        assertEquals(peopleServiceReturn.block().getCpfReturn(), cpf);
        BDDMockito.verify(repository).findByCpf(cpf);
    }


    @Test
    void searchByFirstName() {
        Flux<PeopleEntity> peopleEntity = Flux.just(PeopleData.firstData1().block());
        Flux<PeopleResponse> peopleResponse = Flux.just(PeopleData.firstData1().block())
                .map(PeopleConverter::entityToResponse);

        final String firstName = peopleEntity.blockFirst().getFirstName();

        BDDMockito.when(repository.findByFirstName(firstName)).thenReturn(peopleEntity);

        Flux<PeopleResponse> peopleServiceReturn = service.findByFirstName(firstName);

        assertEquals(peopleServiceReturn.count().block(), peopleEntity.count().block());
        BDDMockito.verify(repository).findByFirstName(firstName);
    }


    @Test
    void searchByLastName() {
        Flux<PeopleEntity> peopleEntity = Flux.just(PeopleData.firstData1().block());
        Flux<PeopleResponse> peopleResponse = Flux.just(PeopleData.firstData1().block())
                .map(PeopleConverter::entityToResponse);

        final String lastName = peopleEntity.blockLast().getFirstName();

        BDDMockito.when(repository.findByLastName(lastName)).thenReturn(peopleEntity);

        Flux<PeopleResponse> peopleServiceReturn = service.findByLastName(lastName);

        assertEquals(peopleServiceReturn.count().block(), peopleResponse.count().block());
        BDDMockito.verify(repository).findByLastName(lastName);
    }

    @Test
    void searchByActive() {
        Flux<PeopleEntity> peopleEntityFlux = Flux.from(PeopleData.firstData1());
        boolean isActive = PeopleData.firstData1().block().isActivated();
        final Long count = peopleEntityFlux.count().block();

        BDDMockito.when(repository.findByIsActivated(isActive)).thenReturn(peopleEntityFlux);

        Flux<PeopleResponse> peopleServiceReturn = service.findByIsActivated(isActive);

        assertEquals(count, peopleServiceReturn.count().block());
        BDDMockito.verify(repository).findByIsActivated(isActive);
    }

    @Test
    void update(){
        Mono<PeopleRequest>  peopleRequest = PeopleData.request1();

        Mono<PeopleEntity> entityMono = peopleRequest.map(element-> PeopleConverter.requestToEntity(peopleRequest.block(), false));
        BDDMockito.when(repository.findById(1L))
                .thenReturn(entityMono);


        StepVerifier.create(service.update(peopleRequest.block(), 1L ))
                .expectSubscription()
                .thenAwait()
                .expectComplete();

        BDDMockito.verify(repository).findById(1L);
    }

    @Test
    void delete(){

        BDDMockito.when(repository.deleteById(1L))
                .thenReturn(Mono.empty());

        StepVerifier.create(service.delete(1L) )
                .expectComplete();

        BDDMockito.verify(repository).deleteById(1L);
    }

    @Test
    void create(){
        Mono<PeopleRequest>  peopleRequest = PeopleData.request1();

        Mono<PeopleEntity> entityMono = peopleRequest.map(element-> PeopleConverter.requestToEntity(peopleRequest.block(), true));

        BDDMockito.when(repository.save(entityMono.block()))
                .thenReturn(entityMono);

        StepVerifier.create(service.create(peopleRequest.block() ))
                .expectSubscription();

        BDDMockito.verify(repository).save(entityMono.block());
    }

    @Test
    void pageable (){
        PageRequest pageRequest = PageRequest.of(0, 20).withSort(Sort.by("firstName").ascending());

        Mono<Page<PeopleResponse>> peopleResponseFlux = PeopleData.listPeopleEntities()
                .map(PeopleConverter::entityToResponse)
                .collectList()
                .zipWith(Mono.just(10L))
                .map(element -> new PageImpl<>(element.getT1(), pageRequest, element.getT2()));

        BDDMockito.when(repository.findAllBy(pageRequest)).thenReturn(PeopleData.listPeopleEntities());
        BDDMockito.when(repository.count()).thenReturn(Mono.just(5L));

        Mono<Page<PeopleResponse>> peopleServiceReturn = service.fingByPaged(0, 20);

        BDDMockito.verify(repository).findAllBy(pageRequest);
        BDDMockito.verify(repository).count();

    }
}