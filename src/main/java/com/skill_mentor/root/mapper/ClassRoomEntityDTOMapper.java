package com.skill_mentor.root.mapper;

import com.skill_mentor.root.dto.ClassRoomDTO;
import com.skill_mentor.root.dto.MentorDTO;
import com.skill_mentor.root.entity.ClassRoomEntity;
import com.skill_mentor.root.entity.MentorEntity;

public class ClassRoomEntityDTOMapper {

    // ✅ Entity → DTO
    public static ClassRoomDTO map(ClassRoomEntity classroomEntity) {
        if (classroomEntity == null) return null;

        ClassRoomDTO classroomDTO = new ClassRoomDTO();
        classroomDTO.setClassRoomId(classroomEntity.getClassRoomId());
        classroomDTO.setTitle(classroomEntity.getTitle());
        classroomDTO.setSessionFee(classroomEntity.getSessionFee());
        classroomDTO.setEnrolledStudentCount(classroomEntity.getEnrolledStudentCount());

        // ✅ Map Mentor (shallow, no classroom mapping)
        if (classroomEntity.getMentor() != null) {
            MentorEntity mentorEntity = classroomEntity.getMentor();
            MentorDTO mentorDTO = new MentorDTO();
            mentorDTO.setMentorId(mentorEntity.getMentorId());
            mentorDTO.setFirstName(mentorEntity.getFirstName());
            mentorDTO.setLastName(mentorEntity.getLastName());
            mentorDTO.setEmail(mentorEntity.getEmail());
            mentorDTO.setProfession(mentorEntity.getProfession());
            mentorDTO.setAddress(mentorEntity.getAddress());
            mentorDTO.setTitle(mentorEntity.getTitle());
            mentorDTO.setSubject(mentorEntity.getSubject());
            mentorDTO.setQualification(mentorEntity.getQualification());
            mentorDTO.setClassRoomDTO(null); // 🚫 prevent recursion

            classroomDTO.setMentor(mentorDTO);
        }

        return classroomDTO;
    }

    // ✅ DTO → Entity
    public static ClassRoomEntity map(ClassRoomDTO classroomDTO) {
        if (classroomDTO == null) return null;

        ClassRoomEntity classroomEntity = new ClassRoomEntity();
        classroomEntity.setClassRoomId(classroomDTO.getClassRoomId());
        classroomEntity.setTitle(classroomDTO.getTitle());
        classroomEntity.setSessionFee(classroomDTO.getSessionFee());
        classroomEntity.setEnrolledStudentCount(classroomDTO.getEnrolledStudentCount());

        // ✅ Map Mentor shallowly (avoid recursive loop)
        if (classroomDTO.getMentor() != null) {
            MentorDTO mentorDTO = classroomDTO.getMentor();
            MentorEntity mentorEntity = new MentorEntity();
            mentorEntity.setMentorId(mentorDTO.getMentorId());
            mentorEntity.setFirstName(mentorDTO.getFirstName());
            mentorEntity.setLastName(mentorDTO.getLastName());
            mentorEntity.setEmail(mentorDTO.getEmail());
            mentorEntity.setProfession(mentorDTO.getProfession());
            mentorEntity.setAddress(mentorDTO.getAddress());
            mentorEntity.setTitle(mentorDTO.getTitle());
            mentorEntity.setSubject(mentorDTO.getSubject());
            mentorEntity.setQualification(mentorDTO.getQualification());
            mentorEntity.setClassRoom(null); // 🚫 prevent recursion

            classroomEntity.setMentor(mentorEntity);
        }

        return classroomEntity;
    }
}
