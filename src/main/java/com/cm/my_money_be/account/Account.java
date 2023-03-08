package com.cm.my_money_be.account;

import lombok.Data;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user")
    private long userId;
    private String name;
    private boolean favorite;
    @OneToMany(mappedBy = "accountId", cascade = {CascadeType.ALL})
    private List<Balance> balances = new ArrayList<>();


    public Account(){
        super();
    }

    public Account(long userId, String name, boolean favorite) {
        this.userId = userId;
        this.name = name;
        this.favorite = favorite;
    }
}
