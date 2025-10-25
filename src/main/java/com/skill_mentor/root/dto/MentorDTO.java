package com.skill_mentor.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class MentorDTO {
    private Integer mentorId;
    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private String title;
    private String profession;
    private String subject;
    private String qualification;
    private ClassRoomDTO classRoomDTO;
}
