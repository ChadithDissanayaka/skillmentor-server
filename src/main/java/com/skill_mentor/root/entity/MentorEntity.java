package com.skill_mentor.root.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "mentor")

public class MentorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mentor_id")
    private Integer mentorId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "address")
    private String address;

    @Column(name = "email")
    private String email;

    @Column(name = "title")
    private String title;

    @Column(name = "profession")
    private String profession;

    @Column(name = "subject")
    private String subject;

    @Column(name = "qualification")
    private String qualification;

    @OneToOne(mappedBy = "mentor", cascade = CascadeType.ALL)
    private ClassRoomEntity classRoom;


}
