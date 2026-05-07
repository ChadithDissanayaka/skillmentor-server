package com.skillmentor.root.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SessionLiteDTO {
    private Integer sessionId;
    private Integer studentId;
    private Integer classRoomId;
    private Integer mentorId;
    private Instant startTime;
    private Instant endTime;

}