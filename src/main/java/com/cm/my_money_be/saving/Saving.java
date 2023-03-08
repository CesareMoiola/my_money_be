package com.cm.my_money_be.saving;

import com.cm.my_money_be.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "savings")
public class Saving {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name="user")
    private long userId;
    private String name;
    @Enumerated(EnumType.STRING)
    private SavingType type;
    private BigDecimal amount;
    private BigDecimal saved;
    private LocalDate startingDate;
    private LocalDate finalDate;
    private LocalDate updateDate;
    private boolean active;
}
