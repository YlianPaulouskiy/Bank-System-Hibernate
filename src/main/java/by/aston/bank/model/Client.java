package by.aston.bank.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@DiscriminatorValue("C")
@EqualsAndHashCode(callSuper = true)
public class Client extends User {

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Account> accounts = new ArrayList<>();


    @ManyToMany(mappedBy = "users")
    private List<Bank> banks = new ArrayList<>();

}
