package com.skillmentor.root.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

import java.time.Instant;
@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "session")
public class SessionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "session_id")
    private Integer sessionId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id", referencedColumnName = "student_id")
    private StudentEntity studentEntity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "class_room_id", referencedColumnName = "class_room_id")
    private ClassRoomEntity classRoomEntity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "mentor_id", referencedColumnName = "mentor_id")
    private MentorEntity mentorEntity;

    @Column(name ="start_time")
    private Instant startTime;

    @Column(name = "end_time")
    private Instant endTime;

}
