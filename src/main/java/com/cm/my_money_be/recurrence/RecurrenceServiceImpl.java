package com.cm.my_money_be.recurrence;

import com.cm.my_money_be.exception.NotFoundException;
import com.cm.my_money_be.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import static com.cm.my_money_be.recurrence.RecurrenceType.EARNING;
import static com.cm.my_money_be.recurrence.RecurrenceType.EXPENSE;

@Service
public class RecurrenceServiceImpl implements RecurrenceService{

    private final RecurrenceRepository recurrenceRepository;

    @Autowired
    public RecurrenceServiceImpl( RecurrenceRepository recurrenceRepository){
        this.recurrenceRepository = recurrenceRepository;
    }

    @Override
    public List<RecurrenceDto> getRecurrences(long userId) {

        List<RecurrenceDto> recurrencesDto = new ArrayList<>();

        for (Recurrence recurrence : recurrenceRepository.findByUserId(userId)){
            recurrencesDto.add(RecurrenceMapper.toRecurrenceDto(recurrence));
        }

        return recurrencesDto;
    }

    @Override
    public void saveNewRecurrence(long userId, RecurrenceDto recurrenceDto) {
        recurrenceRepository.save(RecurrenceMapper.toRecurrence(userId, recurrenceDto));
    }

    @Override
    public void deleteRecurrence(long recurrenceId) {
        recurrenceRepository.delete( getRecurrence(recurrenceId) );
    }

    @Override
    public void updateRecurrence(RecurrenceDto recurrenceDto) {
        Recurrence recurrence = getRecurrence(recurrenceDto.getId());

        recurrence.setName(recurrenceDto.getName());
        recurrence.setAmount(recurrenceDto.getAmount());
        recurrence.setCompleted(recurrenceDto.isCompleted());
        recurrence.setType(recurrenceDto.getType());

        recurrenceRepository.save(recurrence);
    }

    @Override
    public BigDecimal getTotalOfRecurrences(long userId){
        List<Recurrence> recurrences = recurrenceRepository.findByUserId(userId);
        BigDecimal total = BigDecimal.ZERO;

        for(Recurrence recurrence : recurrences){
            if(recurrence.getType().equals(EARNING)) total = total.add(recurrence.getAmount().abs());
            if(recurrence.getType().equals(EXPENSE)) total = total.subtract(recurrence.getAmount().abs());
        }

        return total;
    }

    @Override
    public BigDecimal getTotalOfRemainingRecurrences(long userId){
        List<Recurrence> recurrences = recurrenceRepository.findByUserId(userId);
        BigDecimal total = BigDecimal.ZERO;

        for(Recurrence recurrence : recurrences) {
            if (!recurrence.isCompleted()) {
                if (recurrence.getType().equals(EARNING)) total = total.add(recurrence.getAmount().abs());
                if (recurrence.getType().equals(EXPENSE)) total = total.subtract(recurrence.getAmount().abs());
            }
        }

        return total;
    }

    public BigDecimal getAmountOfCompletedEarnings(long userId){
        List<Recurrence> recurrences = recurrenceRepository.findByUserId(userId);
        BigDecimal earnings = BigDecimal.valueOf(0);

        for(Recurrence recurrence : recurrences){
            if(recurrence.getType().equals(EARNING) && recurrence.isCompleted()) {
                earnings = earnings.add(recurrence.getAmount().abs());
            }
        }

        return earnings;
    }


    private Recurrence getRecurrence(long recurrenceId){
        return recurrenceRepository.findById(recurrenceId)
                .orElseThrow(()-> new NotFoundException("Recurrence " + recurrenceId + " was not found"));
    }
}
