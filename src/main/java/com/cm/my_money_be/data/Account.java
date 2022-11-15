package com.cm.my_money_be.data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH} )
    @JoinColumn(name = "user")
    private User user;
    private String name;
    private String image;
    private boolean active;
    private boolean favorite;
    @OneToMany(mappedBy = "account", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private List<Balance> balances;


    public Account(){ super();}

    public Account(User user, String name) {
        this.user = user;
        this.name = name;
        this.active = true;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isFavorite() { return favorite; }

    public void setFavorite(boolean favorite) { this.favorite = favorite; }

    public List<Balance> getBalances() {
        return balances;
    }

    public void setBalances(List<Balance> balances) {
        this.balances = balances;
    }

    public void setBalance(Balance balance){
        if(balances == null) balances = new ArrayList<>();
        this.balances.add(balance);
    }

    public Balance getBalance(Date date){
        Balance recentBalance = null;

        for(Balance balance : balances){
            if(recentBalance == null && balance.getDate().compareTo(date) <= 0){
                recentBalance = balance;
            }
            else if(recentBalance != null
                    && balance.getDate().compareTo(date) <= 0
                    && balance.getDate().compareTo(recentBalance.getDate()) >= 0){
                recentBalance = balance;
            }
        }

        return recentBalance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", user=" + user.getId() +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", active=" + active +
                ", balances=" + balances +
                '}';
    }
}
