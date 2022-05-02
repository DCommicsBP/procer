package br.com.daione.pavan.procer.business.service;

import br.com.daione.pavan.procer.api.request.PeopleRequest;
import br.com.daione.pavan.procer.infraestructure.entity.PeopleEntity;
import br.com.daione.pavan.procer.api.response.PeopleResponse;
import br.com.daione.pavan.procer.business.converter.PeopleConverter;
import br.com.daione.pavan.procer.infraestructure.repository.PeopleRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class PeopleServiceImpl implements PeopleService {


    static Logger LOG = LoggerFactory.getLogger(PeopleServiceImpl.class);


    private PeopleRepository repository;

    @Override
    public Mono<PeopleResponse> create(PeopleRequest request) {
        PeopleEntity peopleEntity = PeopleConverter.requestToEntity(request, true);
        return repository.save(peopleEntity)
                .map(PeopleConverter::entityToResponse);
    }

    @Override
    public Mono<Void> delete(Long id) {
        return repository.deleteById(id);
    }

    @Override
    public Flux<PeopleResponse> list() {
        return repository.findAll()
                .map(PeopleConverter::entityToResponse);
    }

    @Override
    public Mono<PeopleResponse> findById(Long id) {
        return repository.findById(id)
                .map(PeopleConverter::entityToResponse);
    }

    @Override
    public Mono<PeopleResponse> update(PeopleRequest request, Long id) {

        Mono<PeopleEntity> peopleEntityMono = this.repository.findById(id);
        PeopleEntity peopleEntity = PeopleConverter.requestToEntity(request, false);
        return peopleEntityMono
                .flatMap(element -> {
                    element.setId(id);
                    return repository.save(peopleEntity);
                })
                .map(PeopleConverter::entityToResponse);
    }

    @Override
    public Mono<PeopleResponse> findByCPf(String cpf) {
        return repository.findByCpf(cpf)
                .map(PeopleConverter::entityToResponse);
    }

    @Override

    public Flux<PeopleResponse> findByFirstName(String firstName) {
        return repository.findByFirstName(firstName)
                .map(PeopleConverter::entityToResponse);
    }

    @Override
    public Flux<PeopleResponse> findByLastName(String lastName) {
        return repository.findByLastName(lastName)
                .map(PeopleConverter::entityToResponse);
    }

    @Override

    public Flux<PeopleResponse> findByIsActivated(boolean isActivated) {
        return repository.findByIsActivated(isActivated)
                .map(PeopleConverter::entityToResponse);
    }

    @Override
    public Mono<Page<PeopleResponse>> fingByPaged(int page, int size) {

        PageRequest pageRequest = PageRequest.of(page, size).withSort(Sort.by("firstName").ascending());

        return repository.findAllBy(pageRequest)
                .doOnError(element -> element.getCause())
                .collectList()
                .zipWith(repository.count())
                .map(element -> new PageImpl<>(element.getT1(), pageRequest, element.getT2())
                        .map(PeopleConverter::entityToResponse));
    }
}
