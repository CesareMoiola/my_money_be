package com.cm.my_money_be.saving;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/saving")
@CrossOrigin(origins = "http://localhost:3000")
public class SavingController {

    @Autowired
    SavingService savingService;

    /**
     * Get all savings owned by the user
     * @param userId User id
     * @return All user's savings
     */
    @GetMapping("/{userId}")
    public ResponseEntity<List<SavingDto>> getSavings(@PathVariable("userId") Long userId) {
        List<SavingDto> savings = savingService.getSavings(userId);
        return new ResponseEntity<>(savings, HttpStatus.OK);
    }

    /**
     * Create a new saving
     * @param userId User id
     * @param savingDto New saving
     * @return Http response
     */
    @PutMapping("/{userId}")
    public ResponseEntity<String> saveSaving(@PathVariable("userId") long userId, @RequestBody SavingDto savingDto) {
        savingService.saveSaving(userId, savingDto);
        return ResponseEntity.ok("Saving saved");
    }

    /**
     * Delete a saving
     * @param savingId Saving id
     * @return Http response
     */
    @DeleteMapping("/{savingId}")
    public ResponseEntity<String> deleteSaving(@PathVariable("savingId") long savingId){
        savingService.deleteSaving(savingId);
        return ResponseEntity.ok("Saving cancelled");
    }

    /**
     * Activate a saving
     * @param savingId Saving id
     * @return Http response
     */
    @PutMapping("/activate{savingId}")
    public ResponseEntity<String> activateSaving(@PathVariable("savingId") long savingId){
        savingService.activateSaving(savingId);
        return ResponseEntity.ok("Saving activated");
    }

    /**
     * Deactivate a saving
     * @param savingId Saving id
     * @return Http response
     */
    @PutMapping("/deactivate{savingId}")
    public ResponseEntity<String> deactivateSaving(@PathVariable("savingId") long savingId){
        savingService.deactivateSaving(savingId);
        return ResponseEntity.ok("Saving deactivated");
    }

    /**
     * Edit saving
     * @param savingDto Updated saving
     * @return Http response
     */
    @PutMapping("/update")
    public ResponseEntity<String> updateSaving(@RequestBody SavingDto savingDto){
        savingService.updateSaving(savingDto);
        return ResponseEntity.ok("Saving updated");
    }
}
