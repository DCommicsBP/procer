package br.com.daione.pavan.procer.people.infraestructure.entity;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@ToString
@Table("people")
@NoArgsConstructor
@AllArgsConstructor
public class PeopleEntity implements Persistable<Long> {

    @Id
    private Long id;

    private String cpf;
    private String email;
    private String firstName;
    private boolean isActivated;
    private String lastName;

    @Transient
    private boolean isNew;

    @Transient
    @Override
    public boolean isNew() {
        return this.isNew || id == null;
    }

    public PeopleEntity isNewSetter(){
        this.isNew= true;
        return this;
    }

}
