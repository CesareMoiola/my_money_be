package com.cm.my_money_be.saving;

import com.cm.my_money_be.exception.NotFoundException;
import com.cm.my_money_be.saving.saving_strategy.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class SavingServiceImpl implements SavingService {

    @Autowired
    private SavingRepository savingRepository;

    private Saving getSaving(long savingId){

        return savingRepository
                .findById(savingId)
                .orElseThrow(() -> new NotFoundException("Saving " + savingId + " doesn't exist"));
    }

    @Override
    public List<SavingDto> getSavings(long userId){

        List<Saving> savings = savingRepository.findByUserId(userId);
        List<SavingDto> savingsDto = new ArrayList<>();

        for( Saving saving : savings) {
            SavingReader strategy = new SavingReader(saving);
            SavingDto savingDto = SavingMapper.toSavingDto(saving, strategy.getDailySaving());
            savingsDto.add(savingDto);
        }

        if(savingsDto.isEmpty()) throw new NotFoundException("No savings are found for user " + userId);

        return savingsDto;
    }

    @Override
    public void saveSaving(long userId, SavingDto savingDto){

        LocalDate today = LocalDate.now();
        Saving saving = SavingMapper.toSavingModel(savingDto, userId);
        saving.setUpdateDate(today);
        saving.setStartingDate(today);
        savingRepository.save(saving);
    }

    @Override
    public void deleteSaving(long savingId) {
        savingRepository.deleteById(savingId);
    }

    @Override
    public void activateSaving(long savingId) {
        Saving saving = getSaving(savingId);
        saving.setActive(true);
        savingRepository.save(saving);
    }

    @Override
    public void deactivateSaving(long savingId) {
        Saving saving = getSaving(savingId);
        saving.setActive(false);
        savingRepository.save(saving);
    }

    @Override
    public void updateSaving(SavingDto savingDto) {
        Saving oldSaving = getSaving(savingDto.getId());
        Saving newSaving = SavingMapper.toSavingModel(savingDto, oldSaving.getUserId());

        newSaving.setStartingDate(oldSaving.getStartingDate());
        newSaving.setUpdateDate(LocalDate.now());

        savingRepository.save(newSaving);
    }

    /**
     * Update saved of a saving. If saved has been uploaded return true
     * @param savingId ID of saving to update
     */
    @Override
    public void updateSaved(long savingId){

        Saving saving = getSaving(savingId);
        SavingReader savingReader = new SavingReader(saving);

        if(savingReader.isSavedToUpdate()) return;

        BigDecimal amountToUpdate = savingReader.getAmountToUpdate();

        BigDecimal newSaved = saving.getSaved().add(amountToUpdate);
        saving.setSaved(newSaved);
        saving.setUpdateDate(LocalDate.now());

        savingRepository.save(saving);
    }

    /**
     * Get total saved of all user's savings
     * @param userId User id
     * @return Total amount
     */
    @Override
    public BigDecimal getTotalSaved(long userId){
        List<Saving> savings = savingRepository.findByUserId(userId);
        BigDecimal totalSaved = BigDecimal.ZERO;

        for (Saving saving : savings){
            totalSaved = totalSaved.add(saving.getSaved());
        }

        return totalSaved;
    }

    @Override
    public BigDecimal getRemainingToSaveThisMonth(long userId){
        BigDecimal amount = BigDecimal.ZERO;
        List<Saving> savings = savingRepository.findByUserId(userId);

        for(Saving saving : savings){
            if(saving.isActive()) {
                SavingReader savingReader = new SavingReader(saving);
                BigDecimal savingAmount = savingReader.getRemainingToSaveThisMonth();
                amount = amount.add(savingAmount);
            }
        }

        return amount;
    }

    @Override
    public BigDecimal getDailySaving(long savingId){
        Saving saving = savingRepository.findById(savingId)
                .orElseThrow( () ->  new NotFoundException("Saving " + savingId + " doesn't exist"));
        SavingReader savingReader = new SavingReader(saving);
        return savingReader.getDailySaving();
    }

    @Override
    public BigDecimal getDailySavings(long userId){
        BigDecimal dailySavings = BigDecimal.valueOf(0);
        List<Saving> savings = savingRepository.findByUserId(userId);

        for(Saving saving : savings){
            SavingReader savingReader = new SavingReader(saving);
            if(saving.isActive()) dailySavings = dailySavings.add(savingReader.getDailySaving());
        }

        return dailySavings;
    }

    @Override
    public BigDecimal getMonthlySaving(long userId){
        BigDecimal amount = BigDecimal.valueOf(0);

        for(Saving saving : savingRepository.findByUserId(userId)){
            amount = amount.add(getMonthlySaving(saving.getId()));
        }

        return amount;
    }
}
