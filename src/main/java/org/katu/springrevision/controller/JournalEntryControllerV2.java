package org.katu.springrevision.controller;

import org.bson.types.ObjectId;
import org.katu.springrevision.entity.JournalEntity;
import org.katu.springrevision.entity.UserEntity;
import org.katu.springrevision.service.JournalEntryService;
import org.katu.springrevision.service.UserEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {
    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private UserEntryService userEntryService;

    @GetMapping
    public ResponseEntity<?> getJournalEntries() {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        assert authentication != null;
        String username =  authentication.getName();
        UserEntity user = userEntryService.findByUserName(username);
       List<JournalEntity> all = user.getJournals();
       if (all.isEmpty()) {
           return new ResponseEntity<>(HttpStatus.NO_CONTENT);
       }
       return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<JournalEntity> insertJournalEntry(@RequestBody JournalEntity journalEntity) {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        assert authentication != null;
        String username =  authentication.getName();
        try {
            journalEntity.setJournalDate(new Date());
            journalEntryService.saveEntry(journalEntity,username );
            return ResponseEntity.ok().body(journalEntity);
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping("id/{journalId}")
    public  ResponseEntity<?> getJournalEntry(@PathVariable ObjectId journalId) {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        assert authentication != null;
        String username =  authentication.getName();
        Optional<JournalEntity> journal = userEntryService.findJournalByUsername(username, journalId);
        if (journal.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(journal); // 200 OK
    }

    @DeleteMapping("id/{journalId}")
    public ResponseEntity<?> deleteJournalEntry(@PathVariable ObjectId journalId) {

        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        assert authentication != null;
        String username =  authentication.getName();
        boolean deleted = journalEntryService.deleteJournalEntryById(journalId, username);

        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.noContent().build(); // 204 No Content
    }

    @PutMapping("id/{journalId}")
    public   ResponseEntity<?> updateJournalEntry(@PathVariable ObjectId journalId,@RequestBody JournalEntity journalEntity) {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        assert authentication != null;
        String username =  authentication.getName();
        JournalEntity updatedJournal =
                journalEntryService.updateJournalEntry(journalId, journalEntity,username);

        if (updatedJournal == null) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "No journal found with the given ID");

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(response);
        }

        return ResponseEntity.ok(updatedJournal); // 200 OK
    }
}
