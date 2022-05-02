package br.com.daione.pavan.procer.people.api;

import br.com.daione.pavan.procer.data.PeopleData;
import br.com.daione.pavan.procer.api.request.PeopleRequest;
import br.com.daione.pavan.procer.api.response.PeopleResponse;
import br.com.daione.pavan.procer.business.converter.PeopleConverter;
import br.com.daione.pavan.procer.business.service.PeopleService;
import br.com.daione.pavan.procer.infraestructure.entity.PeopleEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@WebFluxTest
class PeopleControllerTest {
    @MockBean
    private PeopleService peopleService;

    @Autowired
    private WebTestClient webTestClient;
    private static String URL = "/people";
    @Test
    void listALL() {
        Flux<PeopleResponse> peopleResponseFlux = PeopleData.listPeopleEntities()
                .map(PeopleConverter::entityToResponse);

        BDDMockito.when(peopleService.list()).thenReturn(peopleResponseFlux);

        this.webTestClient
                .get()
                .uri(URL)
                .exchange()
                .expectBody()
                .consumeWith(element-> {
                    Assertions.assertEquals(element.getStatus(), HttpStatus.OK);
                });
        BDDMockito.verify(peopleService).list();
    }

    @Test
    void getById() {
        PeopleResponse response2 = PeopleData.response1().block();
        Mono<PeopleResponse> responseMono = PeopleData.response1();
        ResponseEntity<Mono<PeopleResponse>>  peopleEntity = ResponseEntity.ok(responseMono);
        BDDMockito
                .when(peopleService.findById(response2.getId()))
                .thenReturn(responseMono);

        webTestClient.get().uri(URL.concat("/{id}"), response2.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.cpfReturn").isEqualTo(response2.getCpfReturn())
                .jsonPath("$.id").isEqualTo(response2.getId())
                .jsonPath("$.emailReturn").isEqualTo(response2.getEmailReturn())
                .jsonPath("$.firstNameReturn").isEqualTo(response2.getFirstNameReturn())
                .jsonPath("$.activatedReturn").isEqualTo(response2.isActivatedReturn());

        BDDMockito.verify(peopleService, BDDMockito.times(1)).findById(response2.getId());
    }

    @Test
    void paginate() {
        PageRequest pageRequest = PageRequest.of(0, 20).withSort(Sort.by("firstName").ascending());

        Mono<Page<PeopleResponse>> peopleResponseFlux = PeopleData.listPeopleEntities()
                .map(PeopleConverter::entityToResponse)
                .collectList()
                .zipWith(Mono.just(10L))
                .map(element -> new PageImpl<>(element.getT1(), pageRequest, element.getT2()));


        this.webTestClient
                .get()
                .uri(URL.concat("/page/0/20"))
                .exchange()
                .expectBody()
                .consumeWith(element-> {
                    Assertions.assertEquals(element.getStatus(), HttpStatus.OK);
                });

        BDDMockito.when(peopleService.fingByPaged(0, 10)).thenReturn(peopleResponseFlux);

    }

    @Test
    void create() {
        PeopleResponse peopleResponse = PeopleData.response1().block();
        PeopleRequest peopleRequest = PeopleData.request1().block();
        PeopleEntity peopleEntity = PeopleData.firstData1().block();
        Mono<PeopleResponse> peopleResponseMono = PeopleData.response1();

        BDDMockito.when(peopleService.create(peopleRequest)).thenReturn(peopleResponseMono);

        webTestClient.post()
                .uri(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(peopleRequest))
                .exchange()
                .expectStatus().isCreated();

        BDDMockito.verify(peopleService, Mockito.times(1)).create(peopleRequest);
    }

    @Test
    void update() {
        PeopleResponse peopleResponse = PeopleData.response1().block();
        PeopleRequest peopleRequest = PeopleData.request1().block();
        peopleRequest.setNewFirstName("NOvoNome");
        PeopleEntity peopleEntity = PeopleData.firstData1().block();
        Mono<PeopleResponse> peopleResponseMono = PeopleData.response1();

        BDDMockito.when(peopleService.update(peopleRequest, peopleEntity.getId())).thenReturn(peopleResponseMono);

        webTestClient.put()
                .uri(URL.concat("/").concat(peopleResponse.getId().toString()))
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(peopleRequest))
                .exchange()
                .expectStatus().isNoContent();

        BDDMockito.verify(peopleService, Mockito.times(1)).update(peopleRequest, peopleEntity.getId());
    }

    @Test
    void delete() {
        webTestClient.delete()
                .uri(URL.concat("/3"))
                .exchange()
                .expectStatus().isNoContent();

    }
}