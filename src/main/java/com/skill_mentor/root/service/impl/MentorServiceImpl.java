package com.skill_mentor.root.service.impl;

import com.skill_mentor.root.dto.MentorDTO;
import com.skill_mentor.root.entity.ClassRoomEntity;
import com.skill_mentor.root.entity.MentorEntity;
import com.skill_mentor.root.mapper.MentorEntityDTOMapper;
import com.skill_mentor.root.repository.ClassRoomRepository;
import com.skill_mentor.root.repository.MentorRepository;
import com.skill_mentor.root.service.MentorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
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
        MentorEntity savedEntity = null;
        MentorEntity mentorEntity = MentorEntityDTOMapper.map(mentorDTO);
        if(!Objects.isNull(mentorDTO.getClassRoomId())){
            Optional <ClassRoomEntity> optionalClassRoomEntity = classRoomRepository.findById(mentorDTO.getClassRoomId());
            if(optionalClassRoomEntity.isPresent()){
                ClassRoomEntity classRoomEntity = optionalClassRoomEntity.get();
                classRoomEntity.setMentorEntity(mentorEntity);
                savedEntity = mentorRepository.save(mentorEntity);
                classRoomRepository.save(classRoomEntity);
            }
        }
        return MentorEntityDTOMapper.map(savedEntity);
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
        Optional<MentorEntity> mentorEntityOpt = mentorRepository.findById(mentorDTO.getMentorId());
        if (mentorEntityOpt.isEmpty()) {
            throw new RuntimeException("Mentor not found with ID: " + mentorDTO.getMentorId());
        }

        MentorEntity mentorEntity = mentorEntityOpt.get();
        mentorEntity.setFirstName(mentorDTO.getFirstName());
        mentorEntity.setLastName(mentorDTO.getLastName());
        mentorEntity.setEmail(mentorDTO.getEmail());
        mentorEntity.setAddress(mentorDTO.getAddress());
        mentorEntity.setTitle(mentorDTO.getTitle());
        mentorEntity.setProfession(mentorDTO.getProfession());
        mentorEntity.setSubject(mentorDTO.getSubject());
        mentorEntity.setQualification(mentorDTO.getQualification());

        MentorEntity updatedEntity = mentorRepository.save(mentorEntity);
        return MentorEntityDTOMapper.map(updatedEntity);
    }

    @Override
    public MentorDTO deleteMentorById(Integer id) {
        MentorEntity mentorEntity = mentorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mentor not found with ID: " + id));
        mentorRepository.deleteById(id);
        return MentorEntityDTOMapper.map(mentorEntity);
    }
}
