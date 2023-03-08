package com.cm.my_money_be.recurrence;

public class RecurrenceMapper {

    private RecurrenceMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static RecurrenceDto toRecurrenceDto( Recurrence recurrence ){
        return new RecurrenceDto(
                recurrence.getId(),
                recurrence.getName(),
                recurrence.getAmount(),
                recurrence.isCompleted(),
                recurrence.getType()
        );
    }

    public static Recurrence toRecurrence(long userId, RecurrenceDto recurrenceDto ){
        return new Recurrence(
                userId,
                recurrenceDto.getName(),
                recurrenceDto.getAmount(),
                recurrenceDto.getType()
        );
    }
}
