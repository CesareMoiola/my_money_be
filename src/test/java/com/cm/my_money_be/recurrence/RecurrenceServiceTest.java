package com.cm.my_money_be.recurrence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class RecurrenceServiceTest {

    RecurrenceService recurrenceService;

    @Mock
    private RecurrenceRepository recurrenceRepositoryMock;

    private List<Recurrence> recurrences;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        recurrenceService = new RecurrenceServiceImpl(recurrenceRepositoryMock);
        recurrences = getRecurrences();

        given(recurrenceRepositoryMock.findById(1L)).willReturn(Optional.of(recurrences.get(0)));
        given(recurrenceRepositoryMock.findById(2L)).willReturn(Optional.of(recurrences.get(1)));
        given(recurrenceRepositoryMock.findById(3L)).willReturn(Optional.of(recurrences.get(2)));
        given(recurrenceRepositoryMock.findById(4L)).willReturn(Optional.of(recurrences.get(3)));

        List<Recurrence> recurrencesOfUser1 = new ArrayList<>();
        recurrencesOfUser1.add(recurrences.get(0));
        recurrencesOfUser1.add(recurrences.get(1));
        given(recurrenceRepositoryMock.findByUserId(1L)).willReturn(recurrencesOfUser1);

        List<Recurrence> recurrencesOfUser2 = new ArrayList<>();
        recurrencesOfUser2.add(recurrences.get(2));
        recurrencesOfUser2.add(recurrences.get(3));
        given(recurrenceRepositoryMock.findByUserId(2L)).willReturn(recurrencesOfUser2);
    }


    @Test
    void getRecurrencesTest(){
        List<RecurrenceDto> recurrencesDtoExpected = new ArrayList<>();

        recurrencesDtoExpected.add(
                new RecurrenceDto(1L, "recurrence 1", BigDecimal.valueOf(10), false, RecurrenceType.EXPENSE)
        );
        recurrencesDtoExpected.add(
                new RecurrenceDto(2L, "recurrence 2", BigDecimal.valueOf(130), true, RecurrenceType.EARNING)
        );

        assertIterableEquals(recurrencesDtoExpected, recurrenceService.getRecurrences(1L));
        assertIterableEquals(new ArrayList<>(), recurrenceService.getRecurrences(99L));
    }

    @Test
    void saveNewRecurrenceTest(){

        RecurrenceDto newRecurrenceDto = new RecurrenceDto(null, "new recurrence", BigDecimal.TEN, true, RecurrenceType.EXPENSE);
        Recurrence newRecurrence = new Recurrence(3L, "new recurrence", BigDecimal.TEN, RecurrenceType.EXPENSE);

        recurrenceService.saveNewRecurrence(3L, newRecurrenceDto);

        verify(recurrenceRepositoryMock).save(newRecurrence);
    }

    @Test
    void deleteRecurrenceTest(){
        long recurrenceId = 4L;

        recurrenceService.deleteRecurrence(recurrenceId);

        verify(recurrenceRepositoryMock).delete(recurrences.get(3));
    }

    @Test
    void updateRecurrenceTest(){
        RecurrenceDto recurrenceDto = new RecurrenceDto(3L, "recurrence 3 updated", BigDecimal.valueOf(33), true, RecurrenceType.EARNING);

        Recurrence recurrenceUpdated = new Recurrence(2L, "recurrence 3 updated", BigDecimal.valueOf(33), RecurrenceType.EARNING);
        recurrenceUpdated.setId(3L);
        recurrenceUpdated.setCompleted(true);

        recurrenceService.updateRecurrence(recurrenceDto);

        verify(recurrenceRepositoryMock).save(recurrenceUpdated);
    }


    private List<Recurrence> getRecurrences(){

        List<Recurrence> recurrences = new ArrayList<>();

        Recurrence recurrence1 = new Recurrence(1L, "recurrence 1", BigDecimal.valueOf(10), RecurrenceType.EXPENSE);
        recurrence1.setId(1L);
        recurrences.add(recurrence1);

        Recurrence recurrence2 = new Recurrence(1L, "recurrence 2", BigDecimal.valueOf(130), RecurrenceType.EARNING);
        recurrence2.setId(2L);
        recurrence2.setCompleted(true);
        recurrences.add(recurrence2);

        Recurrence recurrence3 = new Recurrence(2L, "recurrence 3", BigDecimal.valueOf(30), RecurrenceType.EXPENSE);
        recurrence3.setId(3L);
        recurrences.add(recurrence3);

        Recurrence recurrence4 = new Recurrence(2L, "recurrence 4", BigDecimal.valueOf(110), RecurrenceType.EARNING);
        recurrence4.setId(4L);
        recurrence4.setCompleted(true);
        recurrences.add(recurrence4);

        return recurrences;
    }
}
