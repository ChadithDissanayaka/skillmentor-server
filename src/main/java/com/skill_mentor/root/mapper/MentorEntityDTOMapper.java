package com.skill_mentor.root.mapper;

import com.skill_mentor.root.dto.ClassRoomDTO;
import com.skill_mentor.root.dto.MentorDTO;
import com.skill_mentor.root.entity.ClassRoomEntity;
import com.skill_mentor.root.entity.MentorEntity;

public class MentorEntityDTOMapper {

    // ✅ Entity → DTO
    public static MentorDTO map(MentorEntity mentorEntity) {
        if (mentorEntity == null) return null;

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

        // ✅ Map ClassRoom without re-mapping Mentor (to avoid recursion)
        if (mentorEntity.getClassRoom() != null) {
            ClassRoomEntity classRoomEntity = mentorEntity.getClassRoom();
            ClassRoomDTO classRoomDTO = new ClassRoomDTO();
            classRoomDTO.setClassRoomId(classRoomEntity.getClassRoomId());
            classRoomDTO.setTitle(classRoomEntity.getTitle());
            classRoomDTO.setSessionFee(classRoomEntity.getSessionFee());
            classRoomDTO.setEnrolledStudentCount(classRoomEntity.getEnrolledStudentCount());

            // Only set minimal mentor info (avoid full mapping loop)
            ClassRoomDTO shallowClassRoom = new ClassRoomDTO();
            classRoomDTO.setMentor(null); // 🚫 avoid recursive mentor mapping

            mentorDTO.setClassRoomDTO(classRoomDTO);
        }

        return mentorDTO;
    }

    // ✅ DTO → Entity
    public static MentorEntity map(MentorDTO mentorDTO) {
        if (mentorDTO == null) return null;

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

        // ✅ Avoid recursion: don't map Mentor again in the ClassRoom
        if (mentorDTO.getClassRoomDTO() != null) {
            ClassRoomDTO dto = mentorDTO.getClassRoomDTO();
            ClassRoomEntity classRoomEntity = new ClassRoomEntity();
            classRoomEntity.setClassRoomId(dto.getClassRoomId());
            classRoomEntity.setTitle(dto.getTitle());
            classRoomEntity.setSessionFee(dto.getSessionFee());
            classRoomEntity.setEnrolledStudentCount(dto.getEnrolledStudentCount());
            mentorEntity.setClassRoom(classRoomEntity);
        }

        return mentorEntity;
    }
}
