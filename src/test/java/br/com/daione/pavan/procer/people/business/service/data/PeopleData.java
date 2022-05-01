package br.com.daione.pavan.procer.people.business.service.data;

import br.com.daione.pavan.procer.people.infraestructure.entity.PeopleEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;

public abstract class PeopleData {

    public static Flux<PeopleEntity> listPeopleEntities(){
        try {
            PeopleEntity[] peopleEntities = new ObjectMapper().readValue(new ClassPathResource("./data.json").getFile(), PeopleEntity[].class);
            return Flux.just(peopleEntities);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

public static Mono<PeopleEntity> firstData1 (){
    try {
        PeopleEntity peopleEntity = new ObjectMapper().readValue(new ClassPathResource("./uniqueData1.json").getFile(), PeopleEntity.class);
        return Mono.just(peopleEntity);

    } catch (IOException e) {
        e.printStackTrace();
    }
    return null;
}

    public static Mono<PeopleEntity> firstData2 (){
        try {
            PeopleEntity peopleEntity = new ObjectMapper().readValue(new ClassPathResource("./uniqueData2.json").getFile(), PeopleEntity.class);
            return Mono.just(peopleEntity);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
