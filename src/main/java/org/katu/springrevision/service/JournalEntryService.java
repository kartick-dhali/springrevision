package org.katu.springrevision.service;

import org.bson.types.ObjectId;
import org.katu.springrevision.entity.JournalEntity;
import org.katu.springrevision.entity.UserEntity;
import org.katu.springrevision.repository.JournalEntryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {
    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private UserEntryService userEntryService;

    private static final Logger logger = LoggerFactory.getLogger(JournalEntryService.class);

    @Transactional
    public void saveEntry(JournalEntity journalEntity, String username) {
        try {
            UserEntity user = userEntryService.findByUserName(username);

            if (user == null) {
                throw new RuntimeException("User not found with username: " + username);
            }

            JournalEntity saved = journalEntryRepository.save(journalEntity);

            user.getJournals().add(saved);
            userEntryService.saveEntryWithoutPass(user);

        } catch (Exception e) {
            // Log the exception (recommended if you have logger)
            logger.error("Error while saving journal entry", e);
            System.err.println("Error while saving journal entry: " + e.getMessage());
            throw new RuntimeException("Failed to save journal entry", e);
        }
    }

    public List<JournalEntity> findAll() {
        return journalEntryRepository.findAll();
    }

    public JournalEntity findJournalEntryById(ObjectId journalId) {
        return journalEntryRepository.findById(journalId).orElse(null);
    }
    @Transactional
    public boolean deleteJournalEntryById(ObjectId journalId, String username) {
        UserEntity user = userEntryService.findByUserName(username);

        if (user == null) {
            return false;
        }

        boolean removed = user.getJournals()
                .removeIf(j -> j.getId().equals(journalId));

        if (!removed) {
            return false; // journal not found for this user
        }

        userEntryService.saveEntryWithoutPass(user);
        journalEntryRepository.deleteById(journalId);

        return true;
    }
    public JournalEntity updateJournalEntry(ObjectId journalId, JournalEntity journalEntity, String username) {
        UserEntity user = userEntryService.findByUserName(username);
        Optional<JournalEntity> updatedJournalEntity = user.getJournals().stream().filter(j -> j.getId().equals(journalId)).findFirst();
        if (updatedJournalEntity.isEmpty()) {
            return null;
        }
        JournalEntity existingEntry = journalEntryRepository.findById(journalId)
                .orElseThrow(() -> new RuntimeException("Journal not found with id: " + journalId));

        // Update only allowed fields
        if (journalEntity.getJournalName() != null) {
            existingEntry.setJournalName(journalEntity.getJournalName());
        }

        if (journalEntity.getJournalContent() != null) {
            existingEntry.setJournalContent(journalEntity.getJournalContent());
        }

        return journalEntryRepository.save(existingEntry);
    }
}
