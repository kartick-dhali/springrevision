package org.katu.springrevision.controller;

import org.katu.springrevision.entity.JournalEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/_journal")
public class JournalEntryController {
//    private Map<Long, JournalEntity> journalEntities = new HashMap<>();
//
//    @GetMapping
//    public List<JournalEntity> getJournalEntries() {
//        return new ArrayList<>(journalEntities.values());
//    }
//
//    @PostMapping
//    public boolean insertJournalEntry(@RequestBody JournalEntity journalEntity) {
//        journalEntities.put(journalEntity.getJournalId(), journalEntity);
//        return true;
//    }
//
//    @GetMapping("id/{journalId}")
//    public JournalEntity getJournalEntry(@PathVariable long journalId) {
//        return journalEntities.get(journalId);
//    }
//
//    @DeleteMapping("id/{journalId}")
//    public JournalEntity deleteJournalEntry(@PathVariable long journalId) {
//        return journalEntities.remove(journalId);
//    }
//
//    @PutMapping("id/{journalId}")
//    public  JournalEntity updateJournalEntry(@PathVariable Long journalId,@RequestBody JournalEntity journalEntity) {
//        return journalEntities.put(journalId, journalEntity);
//    }
}
