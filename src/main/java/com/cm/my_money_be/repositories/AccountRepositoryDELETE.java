package com.cm.my_money_be.repositories;

import com.cm.my_money_be.data.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Collection;

//@Repository
public interface AccountRepositoryDELETE extends JpaRepository<Account, Long> {

    /*@Query(value = "select * from account a where a.user = ?1 and a.active = 1", nativeQuery = true)
    Collection<Account> findByUser(long userId);*/
}
