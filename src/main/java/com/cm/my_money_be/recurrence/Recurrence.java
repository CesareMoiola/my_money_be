package com.cm.my_money_be.recurrence;

import lombok.Data;
import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@Table(name = "recurrences")
public class Recurrence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user")
    private long userId;
    private String name;
    private BigDecimal amount;
    private boolean completed;
    @Enumerated(EnumType.STRING)
    private RecurrenceType type;

    public Recurrence(){
        super();
    }

    public Recurrence(long userId, String name, BigDecimal amount, RecurrenceType type) {
        this.userId = userId;
        this.name = name;
        this.amount = amount.abs();
        this.completed = false;
        this.type = type;
    }
}
