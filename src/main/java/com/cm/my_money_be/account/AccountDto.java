package com.cm.my_money_be.account;

import lombok.Data;
import org.jetbrains.annotations.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class AccountDto implements Serializable, Comparable<AccountDto> {

    private Long id;
    @NotNull
    private String name;
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;
    @NotNull
    private BigDecimal amount;
    @NotNull
    private boolean favorite;

    public AccountDto() {
        super();
    }

    public AccountDto(Long id, String name, LocalDate date, BigDecimal amount, boolean favorite) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.amount = amount;
        this.favorite = favorite;
    }

    @Override
    public int compareTo(@NotNull AccountDto o) {
        int result = 0;

        if( this.favorite == o.isFavorite()){
            result = this.amount.compareTo(o.getAmount());
        }
        else {
            if(this.favorite) result = 1;
            if(o.isFavorite()) result = -1;
        }

        return result;
    }
}
