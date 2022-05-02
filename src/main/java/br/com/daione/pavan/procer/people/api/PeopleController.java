package br.com.daione.pavan.procer.people.api;

import br.com.daione.pavan.procer.people.api.request.PeopleRequest;
import br.com.daione.pavan.procer.people.api.response.PeopleResponse;
import br.com.daione.pavan.procer.people.business.service.PeopleService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/people")
public class PeopleController {

    private PeopleService service;

    @GetMapping
    public ResponseEntity<Flux<PeopleResponse>> listALL() {
        return ResponseEntity
                .ok()
                .body(service.list()
                        .doOnError(element -> element.getCause()));
    }


    @GetMapping("/{id}")
    public ResponseEntity<Mono<PeopleResponse>> getById(@PathVariable Long id) {
       return ResponseEntity
                .ok()
                .body(service.findById(id));
    }

    @GetMapping("/page/{page}/{size}")
    public ResponseEntity<Mono<Page<PeopleResponse>>> paginate(@PathVariable int page, @PathVariable int size) {
        return ResponseEntity.ok(this.service.fingByPaged(page, size));
    }

    @PostMapping
    public ResponseEntity<Mono<PeopleResponse>> create(@Valid @RequestBody Mono<PeopleRequest> peopleRequestMono) {
        return new ResponseEntity<>(peopleRequestMono.flatMap(this.service::create), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mono<PeopleResponse>> update(@PathVariable Long id,
                                                       @Valid @RequestBody Mono<PeopleRequest> peopleRequestMono) {
        return new ResponseEntity<>(peopleRequestMono
                .flatMap(element -> service.update(element, id)), HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
         this.service.delete(id);
         return ResponseEntity.noContent().build();
    }
}
