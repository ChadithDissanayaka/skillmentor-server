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
public class SessionDTO {
    private Integer sessionId;
    private StudentDTO studentDTO;
    private ClassRoomDTO classRoomDTO;
    private MentorDTO mentorDTO;
    private Instant startTime;
    private Instant endTime;
}
