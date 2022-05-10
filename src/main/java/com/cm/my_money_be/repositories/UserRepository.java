package com.cm.my_money_be.repositories;

import com.cm.my_money_be.data.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByEmail(String email);
    void deleteByEmail(String email);
}
