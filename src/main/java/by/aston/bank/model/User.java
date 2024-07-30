package by.aston.bank.model;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@ToString(callSuper = true)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.CHAR)
@DiscriminatorValue("U")
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity<Long> {

    @Column(name = "name")
    private String name;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "user_type", insertable = false, updatable = false)
    private Character userType;

}
