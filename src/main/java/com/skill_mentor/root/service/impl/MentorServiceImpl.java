package com.skill_mentor.root.service.impl;

import com.skill_mentor.root.dto.ClassRoomDTO;
import com.skill_mentor.root.dto.MentorDTO;
import com.skill_mentor.root.entity.ClassRoomEntity;
import com.skill_mentor.root.entity.MentorEntity;
import com.skill_mentor.root.mapper.ClassRoomEntityDTOMapper;
import com.skill_mentor.root.mapper.MentorEntityDTOMapper;
import com.skill_mentor.root.repository.ClassRoomRepository;
import com.skill_mentor.root.repository.MentorRepository;
import com.skill_mentor.root.service.MentorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MentorServiceImpl implements MentorService {

    @Autowired
    private MentorRepository mentorRepository;

    @Autowired
    private ClassRoomRepository classRoomRepository;

    @Override
    public MentorDTO createMentor(MentorDTO mentorDTO) {
        MentorEntity mentorEntity = MentorEntityDTOMapper.map(mentorDTO);
            if (mentorDTO.getClassroomId() != null) {
                Optional<ClassRoomEntity> optionalClassRoomEntity = classRoomRepository.findById(mentorDTO.getClassroomId());
                if (optionalClassRoomEntity.isPresent()) {
                    ClassRoomEntity classRoomEntity = optionalClassRoomEntity.get();
                    mentorEntity.setClassRoomEntity(classRoomEntity);
                    ClassRoomDTO classRoomDTO = ClassRoomEntityDTOMapper.map(classRoomEntity);
                    classRoomDTO.getMentorDTOList().add(mentorDTO);
                }
            }
            MentorEntity savedEntity = mentorRepository.save(mentorEntity);
            MentorDTO savedMentorDTO = MentorEntityDTOMapper.map(savedEntity);
            savedMentorDTO.setClassRoomDTO(mentorDTO.getClassRoomDTO());
            savedMentorDTO.setClassroomId(mentorDTO.getClassroomId());
            return savedMentorDTO;
        }

    @Override
    public List<MentorDTO> getAllMentors(List<String> firstNames, List<String> subjects) {
        List<MentorEntity> mentors = mentorRepository.findAll();

        // Filter by first names and subjects if provided
        if (firstNames != null && !firstNames.isEmpty()) {
            mentors = mentors.stream()
                    .filter(m -> firstNames.contains(m.getFirstName()))
                    .collect(Collectors.toList());
        }

        if (subjects != null && !subjects.isEmpty()) {
            mentors = mentors.stream()
                    .filter(m -> subjects.contains(m.getSubject()))
                    .collect(Collectors.toList());
        }

        return mentors.stream()
                .map(MentorEntityDTOMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public MentorDTO getMentorById(Integer id) {
        Optional<MentorEntity> mentorEntity = mentorRepository.findById(id);
        return mentorEntity.map(MentorEntityDTOMapper::map)
                .orElseThrow(() -> new RuntimeException("Mentor not found with ID: " + id));
    }

    @Override
    public MentorDTO updateMentorById(MentorDTO mentorDTO) {
            Optional<MentorEntity> mentorEntityOptional = mentorRepository.findById(mentorDTO.getMentorId());
            if (mentorEntityOptional.isPresent()) {
                MentorEntity mentorEntity = mentorEntityOptional.get();
                mentorEntity.setFirstName(mentorDTO.getFirstName());
                mentorEntity.setLastName(mentorDTO.getLastName());
                mentorEntity.setEmail(mentorDTO.getEmail());
                mentorEntity.setProfession(mentorDTO.getProfession());
                mentorEntity.setAddress(mentorDTO.getAddress());
                mentorEntity.setTitle(mentorDTO.getTitle());
                mentorEntity.setSubject(mentorDTO.getSubject());
                mentorEntity.setQualification(mentorDTO.getQualification());
                MentorEntity updatedEntity = mentorRepository.save(mentorEntity);

                ClassRoomEntity classRoomEntity = null;
                if (mentorDTO.getClassroomId() != null) {
                    Optional<ClassRoomEntity> optionalClassRoomEntity = classRoomRepository.findById(mentorDTO.getClassroomId());
                    if (optionalClassRoomEntity.isPresent()) {
                        classRoomEntity = optionalClassRoomEntity.get();
                    }
                }
                mentorEntity.setClassRoomEntity(classRoomEntity);
                MentorEntity updatedMentor = mentorRepository.save(mentorEntity);
                return MentorEntityDTOMapper.map(updatedMentor);
            }
            return null;
        }

    @Override
    public MentorDTO deleteMentorById(Integer id) {
        MentorEntity mentorEntity = mentorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mentor not found with ID: " + id));
        mentorRepository.deleteById(id);
        return MentorEntityDTOMapper.map(mentorEntity);
    }
}
