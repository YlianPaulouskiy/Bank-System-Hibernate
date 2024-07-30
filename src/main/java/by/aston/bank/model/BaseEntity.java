package by.aston.bank.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@MappedSuperclass
public abstract class BaseEntity<K extends Serializable> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private K id;

}
