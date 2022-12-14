package com.cm.my_money_be.data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "recurrences")
public class Recurrence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH} )
    @JoinColumn(name = "user")
    private User user;
    private String name;
    private BigDecimal amount;
    private boolean completed;
    private String type;

    public Recurrence(){
        super();
    }

    public Recurrence(User user, String name, BigDecimal amount, String type) {
        this.user = user;
        this.name = name;
        this.amount = amount.abs();
        this.completed = false;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount.abs();
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Recurrence{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", amount=" + amount +
                ", completed=" + completed +
                ", type='" + type + '\'' +
                '}';
    }
}
