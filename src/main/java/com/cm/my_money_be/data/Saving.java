package com.cm.my_money_be.data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "savings")
public class Saving {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH} )
    @JoinColumn(name = "user")
    private User user;
    private String name;
    private String type;
    private BigDecimal amount;
    private BigDecimal saved;
    private LocalDate startingDate;
    private LocalDate finalDate;
    private LocalDate updateDate;
    private boolean active;

    public Saving(){
        super();
    }

    public Saving(User user, String name, String type, BigDecimal amount, BigDecimal saved, LocalDate startingDate, LocalDate finalDate, LocalDate updateDate, boolean active) {
        this.user = user;
        this.name = name;
        this.type = type;
        this.amount = amount;
        this.saved = saved;
        this.startingDate = startingDate;
        this.finalDate = finalDate;
        this.updateDate = updateDate;
        this.active = active;
    }


    public long getId() {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getSaved() {
        return saved;
    }

    public void setSaved(BigDecimal saved) {
        this.saved = saved;
    }

    public LocalDate getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(LocalDate startingDate) {
        this.startingDate = startingDate;
    }

    public LocalDate getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(LocalDate finalDate) {
        this.finalDate = finalDate;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }


    @Override
    public String toString() {
        return "Saving{" +
                "id=" + id +
                ", user=" + user.getId() +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", amount=" + amount +
                ", saved=" + saved +
                ", startingDate=" + startingDate +
                ", finalDate=" + finalDate +
                ", updateDate=" + updateDate +
                ", active=" + active +
                '}';
    }
}
