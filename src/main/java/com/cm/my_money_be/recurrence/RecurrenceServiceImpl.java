package com.cm.my_money_be.recurrence;

import com.cm.my_money_be.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class RecurrenceServiceImpl implements RecurrenceService{

    @Autowired
    RecurrenceRepository recurrenceRepository;

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

    private Recurrence getRecurrence(long recurrenceId){
        return recurrenceRepository.findById(recurrenceId)
                .orElseThrow(()-> new NotFoundException("Recurrence " + recurrenceId + " was not found"));
    }
}
