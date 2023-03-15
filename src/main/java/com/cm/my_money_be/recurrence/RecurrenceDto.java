package com.cm.my_money_be.recurrence;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class RecurrenceDto implements Serializable {
    private Long id;
    private String name;
    private BigDecimal amount;
    private boolean completed;
    private RecurrenceType type;

    public RecurrenceDto() {
        super();
    }

    public RecurrenceDto(Long id, String name, BigDecimal amount, boolean completed, RecurrenceType type) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.completed = completed;
        this.type = type;
    }
}
