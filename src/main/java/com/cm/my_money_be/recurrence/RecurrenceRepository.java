package com.cm.my_money_be.recurrence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecurrenceRepository  extends JpaRepository<Recurrence, Long> {

    @Query("SELECT r FROM Recurrence r WHERE r.userId = ?1")
    List<Recurrence> findByUserId(long userId);
}
