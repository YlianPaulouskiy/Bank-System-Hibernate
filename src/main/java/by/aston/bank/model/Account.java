package by.aston.bank.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "accounts")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Account extends BaseEntity<Long> {

    @Column(name = "number", nullable = false, unique = true)
    private String number;
    @Column(name = "cash", nullable = false)
    private BigDecimal cash;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "bank_id", referencedColumnName = "id", nullable = false)
    private Bank bank;
}
