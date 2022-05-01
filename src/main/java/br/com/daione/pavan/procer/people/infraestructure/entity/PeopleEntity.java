package br.com.daione.pavan.procer.people.infraestructure.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

@Data
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
    @Transient
    private boolean isNew;
    private String lastName;

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
