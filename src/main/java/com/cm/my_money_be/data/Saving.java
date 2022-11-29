package com.cm.my_money_be.data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "savings")
public class Saving {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH} )
    @JoinColumn(name = "user")
    private User user;
    private String name;
    private float amount;
    private float saved;
    private Date date;
    private boolean active;

    public Saving(){
        super();
    }

    public Saving(User user, String name, float amount, float saved, Date date, boolean active) {
        this.user = user;
        this.name = name;
        this.amount = amount;
        this.saved = saved;
        this.date = date;
        this.active = active;
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

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public float getSaved() {
        return saved;
    }

    public void setSaved(float saved) {
        this.saved = saved;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
                ", amount=" + amount +
                ", saved=" + saved +
                ", date=" + date +
                ", active=" + active +
                '}';
    }
}
