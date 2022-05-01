package br.com.daione.pavan.procer.people.api;

import br.com.daione.pavan.procer.people.api.request.PeopleRequest;
import br.com.daione.pavan.procer.people.api.response.PeopleResponse;
import br.com.daione.pavan.procer.people.business.PeopleBusiness;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@RequestMapping("/people")
public class PeopleController {

    private PeopleBusiness business;

    @GetMapping
    public ResponseEntity<Flux<PeopleResponse>> listALL() {
        return ResponseEntity
                .ok()
                .body(business.list()
                        .doOnError(element -> element.getCause()));
    }


    @GetMapping("/{id}")
    public ResponseEntity<Mono<PeopleResponse>> getById(@PathVariable Long id) {
        ResponseEntity<Mono<PeopleResponse>> body = ResponseEntity
                .ok()
                .body(business.findById(id));
        return body;
    }

    @GetMapping("/page/{page}/{size}")
    public ResponseEntity<Mono<Page<PeopleResponse>>> paginate(@PathVariable int page, @PathVariable int size) {
        return ResponseEntity.ok(this.business.fingByPaged(page, size));
    }

    @PostMapping
    public Mono<PeopleResponse> create(@RequestBody Mono<PeopleRequest> peopleRequestMono) {
        return peopleRequestMono.flatMap(this.business::create);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mono<PeopleResponse>> update(@PathVariable Long id,
                                                       @RequestBody Mono<PeopleRequest> peopleRequestMono) {
        return new ResponseEntity<>(peopleRequestMono
                .flatMap(element -> business.update(element, id)), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> delete(@PathVariable Long id) {
        return this.business.delete(id);
    }
}
