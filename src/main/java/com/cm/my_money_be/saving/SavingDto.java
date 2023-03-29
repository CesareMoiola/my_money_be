package com.cm.my_money_be.saving;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class SavingDto implements Serializable {
    private Long id;
    private String name;
    private SavingType type;
    private BigDecimal amount;
    private BigDecimal dailyAmount;
    private BigDecimal saved;
    private boolean active;
    private LocalDate finalDate;

    public SavingDto(){
        super();
    }

    public SavingDto(Long id, String name, SavingType type, BigDecimal amount, BigDecimal dailyAmount, BigDecimal saved, boolean active, LocalDate finalDate) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.amount = amount;
        this.dailyAmount = dailyAmount;
        this.saved = saved;
        this.active = active;
        this.finalDate = finalDate;
    }
}
