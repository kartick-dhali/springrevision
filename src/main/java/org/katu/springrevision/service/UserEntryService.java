package org.katu.springrevision.service;

import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.katu.springrevision.entity.JournalEntity;
import org.katu.springrevision.entity.UserEntity;
import org.katu.springrevision.repository.UserEntryRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class UserEntryService {
    private final UserEntryRepository userEntryRepository;
    private final PasswordEncoder passwordEncoder;

    public UserEntryService(UserEntryRepository userEntryRepository, PasswordEncoder passwordEncoder) {
        this.userEntryRepository = userEntryRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void saveEntry(UserEntity userEntity) {
        try{
            userEntity.setPassword(Objects.requireNonNull(passwordEncoder.encode(userEntity.getPassword())));
            if (userEntity.getRoles() == null || userEntity.getRoles().isEmpty()) {
                userEntity.setRoles(List.of("USER"));
            }
            userEntryRepository.save(userEntity);
        }catch(Exception e){
            log.error("Error while saving User: {}", userEntity.getUserName(), e);
        }
    }

    public void saveEntryWithoutPass(UserEntity userEntity) {
        userEntryRepository.save(userEntity);
    }

    public Optional<JournalEntity> findJournalByUsername(String username, ObjectId journalId) {
        UserEntity user = findByUserName(username);
        return user.getJournals()
                .stream()
                .filter(j -> j.getId().equals(journalId))
                .findFirst();
    }
    public UserEntity findByUserName(String userName) {
        return userEntryRepository.findByUserName(userName);
    }
    public  List<UserEntity> findAllUsers() {
        return userEntryRepository.findAll();
    }
}
