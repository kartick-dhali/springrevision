package org.katu.springrevision.controller;

import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.katu.springrevision.entity.JournalEntity;
import org.katu.springrevision.entity.UserEntity;
import org.katu.springrevision.service.JournalEntryService;
import org.katu.springrevision.service.UserEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/journal")
@Slf4j
public class JournalEntryControllerV2 {
    private final JournalEntryService journalEntryService;
    private final UserEntryService userEntryService;

    public JournalEntryControllerV2(JournalEntryService journalEntryService, UserEntryService userEntryService) {
        this.journalEntryService = journalEntryService;
        this.userEntryService = userEntryService;
    }

    @GetMapping
    public ResponseEntity<?> getJournalEntries(@AuthenticationPrincipal User userdetail) {
        String username =  userdetail.getUsername();
        log.info("username: {}", userdetail);
        UserEntity user = userEntryService.findByUserName(username);
       List<JournalEntity> all = user.getJournals();
       if (all.isEmpty()) {
           return new ResponseEntity<>(HttpStatus.NO_CONTENT);
       }
       return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<JournalEntity> insertJournalEntry(@RequestBody JournalEntity journalEntity,
                                                            @AuthenticationPrincipal User userdetail) {
        String username =  userdetail.getUsername();
        try {
            journalEntity.setJournalDate(LocalDateTime.now());
            journalEntryService.saveEntry(journalEntity,username );
            return ResponseEntity.ok().body(journalEntity);
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping("id/{journalId}")
    public  ResponseEntity<?> getJournalEntry(@PathVariable ObjectId journalId,
                                              @AuthenticationPrincipal User userdetail) {
        String username =  userdetail.getUsername();
        Optional<JournalEntity> journal = userEntryService.findJournalByUsername(username, journalId);
        if (journal.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(journal); // 200 OK
    }

    @DeleteMapping("id/{journalId}")
    public ResponseEntity<?> deleteJournalEntry(@PathVariable ObjectId journalId,
                                                @AuthenticationPrincipal User userdetail) {

        String username =  userdetail.getUsername();

        boolean deleted = journalEntryService.deleteJournalEntryById(journalId, username);

        if (!deleted) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.noContent().build(); // 204 No Content
    }

    @PutMapping("id/{journalId}")
    public   ResponseEntity<?> updateJournalEntry(@PathVariable ObjectId journalId,
                                                  @RequestBody JournalEntity journalEntity,
                                                  @AuthenticationPrincipal User userdetail) {
        String username =  userdetail.getUsername();
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
