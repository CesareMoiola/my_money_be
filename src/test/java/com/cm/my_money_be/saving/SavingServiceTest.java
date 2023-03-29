package com.cm.my_money_be.saving;

import com.cm.my_money_be.utils.DateUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static com.cm.my_money_be.saving.SavingType.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SavingServiceTest {

    private SavingService savingService;

    @Mock
    private SavingRepository savingRepositoryMock;

    private final LocalDate today = LocalDate.of(2023,3,21);

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        savingService = new SavingServiceImpl(savingRepositoryMock);
        List<Saving> savings = getSavings();

        given(savingRepositoryMock.findById(1L)).willReturn(Optional.of(savings.get(0)));
        given(savingRepositoryMock.findById(2L)).willReturn(Optional.of(savings.get(1)));
        given(savingRepositoryMock.findById(3L)).willReturn(Optional.of(savings.get(2)));
        given(savingRepositoryMock.findById(4L)).willReturn(Optional.of(savings.get(3)));
        given(savingRepositoryMock.findById(5L)).willReturn(Optional.of(savings.get(4)));

        List<Saving> savingsOfUser1 = new ArrayList<>();
        savingsOfUser1.add(savings.get(0));
        savingsOfUser1.add(savings.get(1));
        savingsOfUser1.add(savings.get(2));
        given(savingRepositoryMock.findByUserId(1L)).willReturn(savingsOfUser1);

        List<Saving> savingsOfUser2 = new ArrayList<>();
        savingsOfUser2.add(savings.get(3));
        savingsOfUser2.add(savings.get(4));
        given(savingRepositoryMock.findByUserId(2L)).willReturn(savingsOfUser2);
    }

    @Test
    void getSavingsTest(){

        List<SavingDto> expected = new ArrayList<>();

        expected.add(
                new SavingDto(
                        1L,"saving 1", SavingType.DAILY, BigDecimal.valueOf(1.5), BigDecimal.valueOf(1.5), BigDecimal.valueOf(76.5), true, null
                )
        );

        expected.add(
                new SavingDto(
                        2L,"saving 2", SavingType.MONTHLY, BigDecimal.valueOf(30), BigDecimal.valueOf(0.97), BigDecimal.valueOf(90), true, null
                )
        );

        expected.add(
                new SavingDto(
                        3L,"saving 3", TARGET, BigDecimal.valueOf(500), BigDecimal.valueOf(1.58), BigDecimal.valueOf(50), true, LocalDate.of(2024,1,1)
                )
        );

        try (MockedStatic<DateUtils> utilities = Mockito.mockStatic(DateUtils.class, Mockito.CALLS_REAL_METHODS)) {
            utilities.when(DateUtils::today).thenReturn(today);

            assertIterableEquals(expected, savingService.getSavings(1L));
        }
    }

    @Test
    void saveSavingTest(){
        long userId = 33L;
        long savingId = 9292L;

        try (MockedStatic<DateUtils> utilities = Mockito.mockStatic(DateUtils.class, Mockito.CALLS_REAL_METHODS)) {
            utilities.when(DateUtils::today).thenReturn(today);


            when(savingRepositoryMock.save(any(Saving.class))).thenAnswer(invocation -> {
                Saving saving = invocation.getArgument(0);
                saving.setId(savingId);
                return saving;
            });

            SavingDto savingDto = new SavingDto(null, "new saving", TARGET, BigDecimal.valueOf(150), BigDecimal.valueOf(999), BigDecimal.valueOf(50), true, LocalDate.of(2024,1,1) );

            Saving expected = new Saving(userId, "new saving", TARGET, BigDecimal.valueOf(150), BigDecimal.valueOf(50));
            expected.setId(savingId);
            expected.setStartingDate(DateUtils.today());
            expected.setFinalDate(LocalDate.of(2024,1,1));
            expected.setUpdateDate(DateUtils.today());
            expected.setActive(true);

            savingService.saveSaving(userId,savingDto);

            verify(savingRepositoryMock).save(expected);
        }
    }

    @Test
    void updateSavingTest(){

        try (MockedStatic<DateUtils> utilities = Mockito.mockStatic(DateUtils.class, Mockito.CALLS_REAL_METHODS)) {
            utilities.when(DateUtils::today).thenReturn(today);

            SavingDto savingDto = new SavingDto(1L, "updated saving", MONTHLY, BigDecimal.valueOf(15.99), BigDecimal.valueOf(1.10), BigDecimal.valueOf(80.99), false, LocalDate.of(2024, 1, 2));
            Saving expected = new Saving(1L, "updated saving", MONTHLY, BigDecimal.valueOf(15.99), BigDecimal.valueOf(80.99));
            expected.setId(1L);
            expected.setFinalDate(LocalDate.of(2024, 1, 2));
            expected.setUpdateDate(DateUtils.today());

            savingService.updateSaving(savingDto);

            verify(savingRepositoryMock).save(expected);
        }
    }

    @Test
    void deleteSavingTest(){
        long accountToDelete = 1L;

        savingService.deleteSaving(accountToDelete);

        verify(savingRepositoryMock).deleteById(accountToDelete);
    }

    @Test
    void getTotalSavedTest(){
        long userId = 1L;
        BigDecimal expected = BigDecimal.valueOf(216.5);
        assertEquals(expected, savingService.getTotalSaved(userId));
    }

    @Test
    void getMonthlySavingTest(){
        long userId = 1L;
        BigDecimal expected = BigDecimal.valueOf(125.55);

        try (MockedStatic<DateUtils> utilities = Mockito.mockStatic(DateUtils.class, Mockito.CALLS_REAL_METHODS)) {
            utilities.when(DateUtils::today).thenReturn(today);

            assertEquals(expected, savingService.getMonthlySaving(userId));
        }
    }

    @Test
    void getDailySavingsTest(){
        BigDecimal expected = BigDecimal.valueOf(4.05);

        try (MockedStatic<DateUtils> utilities = Mockito.mockStatic(DateUtils.class, Mockito.CALLS_REAL_METHODS)) {
            utilities.when(DateUtils::today).thenReturn(today);

            assertEquals(expected, savingService.getDailySavings(1L));
        }
    }

    private List<Saving> getSavings(){
        List<Saving> savings = new ArrayList<>();

        Saving saving1 = new Saving(1L, "saving 1", DAILY, BigDecimal.valueOf(1.5), BigDecimal.valueOf(76.5));
        saving1.setId(1L);
        saving1.setActive(true);
        savings.add(saving1);

        Saving saving2 = new Saving(1L, "saving 2", MONTHLY, BigDecimal.valueOf(30), BigDecimal.valueOf(90));
        saving2.setId(2L);
        saving2.setActive(true);
        savings.add(saving2);

        Saving saving3 = new Saving(1L, "saving 3", TARGET, BigDecimal.valueOf(500), BigDecimal.valueOf(50));
        saving3.setId(3L);
        saving3.setActive(true);
        saving3.setStartingDate(LocalDate.of(2000,1,1));
        saving3.setFinalDate(LocalDate.of(2024,1,1));
        savings.add(saving3);

        Saving saving4 = new Saving(2L, "saving 4", TARGET, BigDecimal.valueOf(450), BigDecimal.valueOf(135));
        saving4.setId(4L);
        saving4.setActive(false);
        saving3.setStartingDate(LocalDate.of(2000,1,1));
        saving4.setFinalDate(LocalDate.of(2024,1,1));
        savings.add(saving4);

        Saving saving5 = new Saving(2L, "saving 5", ANNUAL, BigDecimal.valueOf(360), BigDecimal.valueOf(130));
        saving5.setId(5L);
        saving5.setActive(true);
        savings.add(saving5);

        return savings;
    }
}
