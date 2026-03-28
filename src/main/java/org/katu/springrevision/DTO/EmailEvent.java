package org.katu.springrevision.DTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailEvent {
    private String to;
    private String subject;
    private String body;
}
