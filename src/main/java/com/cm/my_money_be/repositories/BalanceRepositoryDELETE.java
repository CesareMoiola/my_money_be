package com.cm.my_money_be.repositories;

import com.cm.my_money_be.data.Balance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.sql.Date;

//@Repository
public interface BalanceRepositoryDELETE extends JpaRepository<Balance, Long> {

    /*@Query(value = "select * from balance where account = ?1 and date <= ?2 order by date desc limit 1;", nativeQuery = true)
    Balance findBalanceByAccountAndDate(long id, Date date);

    @Modifying
    @Query("update balance set account.amount = ?1 where account = ?2 and date = ?3 ")
    int updateBalanceForAccount(Integer status, Long id);

    @Modifying
    @Query("insert balance set account.amount = ?1 where account = ?2 and date = ?3 ")
    int setBalanceForAccount(Integer status, Long id);*/
}
