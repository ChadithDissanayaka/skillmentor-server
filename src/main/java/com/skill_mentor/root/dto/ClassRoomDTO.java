package com.skill_mentor.root.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ClassRoomDTO {
    private Integer classRoomId;

    private String title;

    private Double sessionFee;

    private Integer enrolledStudentCount;

    private List<MentorDTO> mentorDTOList = new ArrayList<>();

}
