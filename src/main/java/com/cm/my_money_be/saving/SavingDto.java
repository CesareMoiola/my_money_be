package com.cm.my_money_be.saving;

import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class SavingDto implements Serializable {
    private long id;
    private String name;
    private SavingType type;
    private BigDecimal amount;
    private BigDecimal dailyAmount;
    private BigDecimal saved;
    private boolean active;
    private LocalDate finalDate;
}
