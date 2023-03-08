package com.cm.my_money_be.user;

import com.cm.my_money_be.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    void deleteByEmail(String email);

    @Query("SELECT DISTINCT p.email FROM User p")
    List<String> findAllEmail();

    @Query("SELECT DISTINCT p.id FROM User p")
    List<Long> findAllId();
}
