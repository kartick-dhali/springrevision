package org.katu.springrevision.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.katu.springrevision.constant.Sentiment;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "journal_entries")
@Data
@NoArgsConstructor
@Getter
@Setter
public class JournalEntity {
    @Id
    private ObjectId id;
    private String journalName;
    private String journalContent;
    private LocalDateTime journalDate;
    private Sentiment sentiment;

}
