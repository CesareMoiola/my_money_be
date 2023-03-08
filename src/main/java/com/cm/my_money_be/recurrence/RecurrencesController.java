package com.cm.my_money_be.recurrence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/recurrence")
@CrossOrigin(origins = "http://localhost:3000")
public class RecurrencesController {

    @Autowired
    RecurrenceService recurrenceService;

    /**
     * Get all recurrences owned by the user
     * @param userId User id
     * @return recurrences
     */
    @GetMapping("/{userId}")
    public ResponseEntity<List<RecurrenceDto>> getRecurrences(@PathVariable("userId") long userId){
        List<RecurrenceDto> recurrencesDto = recurrenceService.getRecurrences(userId);
        return new ResponseEntity<>(recurrencesDto, HttpStatus.OK);
    }

    /**
     * Save a recurrence
     * @param userId User id
     * @param recurrenceDto Recurrence dto
     * @return Http response
     */
    @PostMapping("/save{userId}")
    public ResponseEntity<String> saveRecurrence(@PathVariable("userId") long userId, @RequestBody RecurrenceDto recurrenceDto){
        recurrenceService.saveNewRecurrence(userId, recurrenceDto);
        return ResponseEntity.ok("Recurrence saved");
    }

    /**
     * Delete recurrence
     * @param recurrenceId Recurrence id
     * @return Http response
     */
    @DeleteMapping("/{recurrenceId}")
    public ResponseEntity<String> deleteRecurrence(@PathVariable("recurrenceId") long recurrenceId){
        recurrenceService.deleteRecurrence(recurrenceId);
        return ResponseEntity.ok("Recurrence deleted");
    }

    /**
     * Edit recurrence
     * @param recurrenceDto Recurrence updated
     * @return Http response
     */
    @PutMapping("/update")
    public ResponseEntity<String> updateRecurrence(@RequestBody RecurrenceDto recurrenceDto){
        recurrenceService.updateRecurrence(recurrenceDto);
        return ResponseEntity.ok("Recurrence updated");
    }
}
