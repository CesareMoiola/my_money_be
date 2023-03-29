package com.cm.my_money_be.saving;

import lombok.Data;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "savings")
public class Saving {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="user")
    private Long userId;
    private String name;
    @Enumerated(EnumType.STRING)
    private SavingType type;
    private BigDecimal amount;
    private BigDecimal saved;
    private LocalDate startingDate;
    private LocalDate finalDate;
    private LocalDate updateDate;
    private boolean active;

    public Saving() {
        super();
    }

    public Saving(long userId, String name, SavingType type, BigDecimal amount, BigDecimal saved) {
        this.userId = userId;
        this.name = name;
        this.type = type;
        this.amount = amount;
        this.saved = saved;
    }
}
