package com.cm.my_money_be.saving;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SavingRepository extends JpaRepository<Saving, Long> {

    @Query("SELECT p FROM Saving p WHERE updateDate < CURRENT_DATE and (p.type <> 'TARGET' or p.amount > p.saved)")
    List<Saving> findActiveSavingsToUpdate();

    @Query("SELECT s FROM Saving s WHERE s.userId = ?1")
    List<Saving> findByUserId(long userId);
}

