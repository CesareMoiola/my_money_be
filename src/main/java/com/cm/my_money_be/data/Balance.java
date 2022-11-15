package com.cm.my_money_be.data;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "balance")
public class Balance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH} )
    @JoinColumn(name = "account")
    private Account account;
    private Date date;
    private float amount;


    public Balance(){
        super();
    }

    public Balance(Account account, Date date, float amount) {
        this.account = account;
        this.date = date;
        this.amount = amount;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Balance{" +
                "id=" + id +
                ", account=" + account.getId() +
                ", date=" + date +
                ", amount=" + amount +
                '}';
    }
}
