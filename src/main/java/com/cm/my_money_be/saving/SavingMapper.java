package com.cm.my_money_be.saving;

import com.cm.my_money_be.user.User;
import java.math.BigDecimal;

public class SavingMapper {

    private SavingMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static SavingDto toSavingDto(Saving saving, BigDecimal dailyAmount){
        SavingDto savingDto = new SavingDto();

        savingDto.setId(saving.getId());
        savingDto.setName(saving.getName());
        savingDto.setType(saving.getType());
        savingDto.setAmount(saving.getAmount());
        savingDto.setDailyAmount(dailyAmount);
        savingDto.setSaved(saving.getSaved());
        savingDto.setActive(saving.isActive());
        savingDto.setFinalDate(saving.getFinalDate());

        return savingDto;
    }

    public static Saving toSavingModel(SavingDto savingDto, long userId ){
        Saving saving = new Saving();

        saving.setUserId(userId);
        saving.setId(savingDto.getId());
        saving.setName(savingDto.getName());
        saving.setType(savingDto.getType());
        saving.setAmount(savingDto.getAmount());
        saving.setSaved(savingDto.getSaved());
        saving.setFinalDate(savingDto.getFinalDate());
        saving.setActive(savingDto.isActive());

        return saving;
    }
}
