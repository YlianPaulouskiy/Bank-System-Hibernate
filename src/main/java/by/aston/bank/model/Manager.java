package by.aston.bank.model;

import by.aston.bank.model.converter.DateConverter;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@DiscriminatorValue("M")
@EqualsAndHashCode(callSuper = true)
public class Manager extends User {

    @Convert(converter = DateConverter.class)
    @Column(name = "start_work", nullable = false)
    private String startWork;
    @Convert(converter = DateConverter.class)
    @Column(name = "end_work")
    private String endWork;

}
