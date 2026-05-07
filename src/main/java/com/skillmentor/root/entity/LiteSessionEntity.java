package com.skillmentor.root.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "session")
public class LiteSessionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "session_id")
    private Integer sessionId;

    @NotNull(message = "Student ID must not be null")
    @Column(name = "student_id", nullable = false)
    private Integer studentId;

    @NotNull(message = "Classroom ID must not be null")
    @Column(name = "class_room_id", nullable = false)
    private Integer classRoomId;

    @NotNull(message = "Mentor ID must not be null")
    @Column(name = "mentor_id", nullable = false)
    private Integer mentorId;

    @NotBlank(message = "Topic must not be blank")
    @Column(name = "topic", nullable = false)
    private String topic;

    @NotNull(message = "Start time must not be null")
    @Column(name = "start_time", nullable = false)
    private Instant startTime;

    @NotNull(message = "End time must not be null")
    @Column(name = "end_time", nullable = false)
    private Instant endTime;
}
