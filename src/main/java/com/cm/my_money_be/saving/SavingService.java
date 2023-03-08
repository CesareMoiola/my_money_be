package com.cm.my_money_be.saving;

import java.math.BigDecimal;
import java.util.List;

public interface SavingService {

    List<SavingDto> getSavings(long userId);

    void saveSaving(long userId, SavingDto savingDto);

    void deleteSaving(long savingId);

    void activateSaving(long savingId);

    void deactivateSaving(long savingId);

    void updateSaving(SavingDto savingDto);

    void updateSaved(long savingId);

    BigDecimal getDailySaving(long savingId);

    BigDecimal getTotalSaved(long userId);

    BigDecimal getRemainingToSaveThisMonth(long userId);

    BigDecimal getDailySavings(long userId);

    BigDecimal getMonthlySaving(long userId);
}
