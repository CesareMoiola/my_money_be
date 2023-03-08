package com.cm.my_money_be.user;

import lombok.Data;
import javax.persistence.*;

@Entity
@Data
@Table(name = "Users")
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
