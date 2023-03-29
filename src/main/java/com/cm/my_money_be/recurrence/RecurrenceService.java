package com.cm.my_money_be.recurrence;

import java.math.BigDecimal;
import java.util.List;

public interface RecurrenceService {

    List<RecurrenceDto> getRecurrences(long userId);

    void saveNewRecurrence(long userId, RecurrenceDto recurrenceDto);

    void deleteRecurrence(long recurrenceId);

    void updateRecurrence(RecurrenceDto recurrenceDto);

    BigDecimal getTotalOfRecurrences(long userId);

    BigDecimal getTotalOfRemainingRecurrences(long userId);

    BigDecimal getAmountOfCompletedEarnings(long userId);
}
