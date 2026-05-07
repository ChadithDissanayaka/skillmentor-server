package com.skillmentor.root.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    @Column(name = "session_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sessionId;
    @Column(name = "student_id")
    private Integer studentId;
    @Column(name = "class_room_id")
    private Integer classRoomId;
    @Column(name = "topic")
    private String topic;
    @Column(name = "mentor_id")
    private Integer mentorId;
    @Column(name = "start_time")
    private Instant startTime;
    @Column(name = "end_time")
    private Instant endTime;
}
