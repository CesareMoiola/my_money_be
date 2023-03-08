package com.cm.my_money_be.recurrence;

import java.util.List;

public interface RecurrenceService {

    List<RecurrenceDto> getRecurrences(long userId);

    void saveNewRecurrence(long userId, RecurrenceDto recurrenceDto);

    void deleteRecurrence(long recurrenceId);

    void updateRecurrence(RecurrenceDto recurrenceDto);
}
