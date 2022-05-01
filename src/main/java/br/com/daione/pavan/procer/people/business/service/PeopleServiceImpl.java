package br.com.daione.pavan.procer.people.business.service;

import br.com.daione.pavan.procer.people.infraestructure.entity.PeopleEntity;
import br.com.daione.pavan.procer.people.infraestructure.repository.PeopleRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class PeopleServiceImpl implements PeopleService{

    static Logger LOG = LoggerFactory.getLogger(PeopleServiceImpl.class);

    private PeopleRepository repository;

    @Override
    public Flux<PeopleEntity> searchAll() {
        return repository.findAll()
                .doOnError(element-> {
                   LOG.error("CAUSE: " + element.getCause().getLocalizedMessage(), element);
                });
    }

    @Override
    public Flux<PeopleEntity> searchPaginated(Pageable pageable) {
        return repository.findAllBy(pageable)
                .doOnError(element-> {
                    LOG.error("CAUSE: " + element.getCause().getLocalizedMessage(), element);
                });
    }

    @Override
    public Mono<PeopleEntity> create(PeopleEntity entity) {
        return repository.save(entity)
                .doOnError(element-> {
                    LOG.error("CAUSE: " + element.getCause().getLocalizedMessage(), element);
                });
    }

    @Override
    public Mono<PeopleEntity> searchById(Long id) {
        return repository.findById(id)
                .doOnError(element-> {
                    LOG.error("CAUSE: " + element.getCause().getLocalizedMessage(), element);
                });
    }

    @Override
    public Mono<PeopleEntity> searchByCpf(String cpf) {
        return repository.findByCpf(cpf);
    }

    @Override
    public Mono<PeopleEntity> searchByEmail(String email) {
        return repository.findByEmail(email)
                .doOnError(element-> {
                    LOG.error("CAUSE: " + element.getCause().getLocalizedMessage(), element);
                });
    }

    @Override
    public Flux<PeopleEntity> searchByFirstName(String firstName) {
        return repository.findByFirstName(firstName)
                .doOnError(element-> {
                    LOG.error("CAUSE: " + element.getCause().getLocalizedMessage(), element);
                });
    }

    @Override
    public Flux<PeopleEntity> searchByLastName(String lastName) {
        return repository.findByLastName(lastName)
                .doOnError(element-> {
                    LOG.error("CAUSE: " + element.getCause().getLocalizedMessage(), element);
                });
    }

    @Override
    public Flux<PeopleEntity> searchByActive(boolean isActive) {
        return repository.findByIsActivated(isActive)
                .doOnError(element-> {
                    LOG.error("CAUSE: " + element.getCause().getLocalizedMessage(), element);
                });
    }

    @Override
    public Mono<Void> deleteById(Long id) {
        return repository.deleteById(id)
                .doOnError(element-> {
            LOG.error("CAUSE: " + element.getCause().getLocalizedMessage(), element);
        });
    }

    @Override
    public Mono<Long> count() {
        return repository.count()
                .doOnError(element-> {
                    LOG.error("CAUSE: " + element.getCause().getLocalizedMessage(), element);
                });
    }
}
