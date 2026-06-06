package com.skillmentor.root.service.impl;

import com.skillmentor.root.dto.MentorDTO;
import com.skillmentor.root.entity.ClassRoomEntity;
import com.skillmentor.root.entity.MentorEntity;
import com.skillmentor.root.exception.MentorException;
import com.skillmentor.root.mapper.MentorEntityDTOMapper;
import com.skillmentor.root.repository.ClassRoomRepository;
import com.skillmentor.root.repository.MentorRepository;
import com.skillmentor.root.service.MentorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class MentorServiceImpl implements MentorService {

    @Autowired
    private MentorRepository mentorRepository;
    @Autowired
    private ClassRoomRepository classRoomRepository;

    @Override
    public MentorDTO createMentor(MentorDTO mentorDTO) throws MentorException {
        if (mentorDTO == null) {
            throw new IllegalArgumentException("Mentor data must not be null.");
        }

        final MentorEntity mentorEntity = MentorEntityDTOMapper.map(mentorDTO);
        final Integer classRoomId = mentorDTO.getClassRoomId();
        if (classRoomId != null) {
            final ClassRoomEntity classRoomEntity = classRoomRepository.findById(classRoomId)
                    .orElseThrow(() -> new MentorException("Classroom not found with ID: " + classRoomId));
            classRoomEntity.setMentor(mentorEntity);
            final MentorEntity savedMentor = mentorRepository.save(Objects.requireNonNull(mentorEntity));
            classRoomRepository.save(classRoomEntity);
            return MentorEntityDTOMapper.map(savedMentor);
        }
        final MentorEntity savedEntity = mentorRepository.save(Objects.requireNonNull(mentorEntity));
        return MentorEntityDTOMapper.map(savedEntity);
    }

    @Override
    public List<MentorDTO> getAllMentors(List<String> firstNames, List<String> subjects) {
        return mentorRepository.findAll().stream()
                .filter(mentor -> firstNames == null || firstNames.isEmpty() || firstNames.contains(mentor.getFirstName()))
                .filter(mentor -> subjects == null || subjects.isEmpty() || subjects.contains(mentor.getSubject()))
                .map(MentorEntityDTOMapper::map)
                .toList();
    }

    @Override
    public MentorDTO findMentorById(Integer id) throws MentorException {
        if (id == null) {
            throw new IllegalArgumentException("Mentor ID must not be null.");
        }
        return mentorRepository.findById(id)
                .map(MentorEntityDTOMapper::map)
                .orElseThrow(() -> new MentorException("Mentor not found with ID: " + id));
    }

    @Override
    public MentorDTO updateMentorById(MentorDTO mentorDTO) throws MentorException {
        if (mentorDTO == null) {
            throw new IllegalArgumentException("Mentor data must not be null for update.");
        }

        final Integer mentorId = mentorDTO.getMentorId();
        if (mentorId == null) {
            throw new IllegalArgumentException("Mentor ID must not be null for update.");
        }
        final MentorEntity mentorEntity = mentorRepository.findById(mentorId)
                .orElseThrow(() -> new MentorException("Cannot update. Mentor not found with ID: " + mentorId));
        mentorEntity.setFirstName(mentorDTO.getFirstName());
        mentorEntity.setLastName(mentorDTO.getLastName());
        mentorEntity.setEmail(mentorDTO.getEmail());
        mentorEntity.setPhoneNumber(mentorDTO.getPhoneNumber());
        mentorEntity.setTitle(mentorDTO.getTitle());
        mentorEntity.setProfession(mentorDTO.getProfession());
        mentorEntity.setSubject(mentorDTO.getSubject());
        mentorEntity.setAddress(mentorDTO.getAddress());
        mentorEntity.setSessionFee(mentorDTO.getSessionFee());
        mentorEntity.setQualification(mentorDTO.getQualification());
        final MentorEntity updatedEntity = mentorRepository.save(mentorEntity);
        return MentorEntityDTOMapper.map(updatedEntity);
    }

    @Override
    public MentorDTO deleteMentorById(Integer id) throws MentorException {
        if (id == null) {
            throw new IllegalArgumentException("Mentor ID must not be null.");
        }
        final MentorEntity mentorEntity = mentorRepository.findById(id)
                .orElseThrow(() -> new MentorException("Cannot delete. Mentor not found with ID: " + id));
        mentorRepository.delete(Objects.requireNonNull(mentorEntity));
        return MentorEntityDTOMapper.map(mentorEntity);
    }
}
