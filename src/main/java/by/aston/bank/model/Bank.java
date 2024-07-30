package by.aston.bank.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "banks")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Bank extends BaseEntity<Long> {

    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "accounts",
            joinColumns = {@JoinColumn(name = "bank_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private List<User> users = new ArrayList<>();

    @OneToMany(mappedBy = "bank", cascade = CascadeType.ALL)
    private List<Account> accounts = new ArrayList<>();

}
