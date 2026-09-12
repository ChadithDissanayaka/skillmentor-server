package com.skillmentor.root.service.impl;

import com.skillmentor.root.dto.ClassRoomDTO;
import com.skillmentor.root.dto.MentorDTO;
import com.skillmentor.root.entity.ClassRoomEntity;
import com.skillmentor.root.exception.ClassRoomException;
import com.skillmentor.root.mapper.ClassRoomEntityDTOMapper;
import com.skillmentor.root.mapper.MentorEntityDTOMapper;
import com.skillmentor.root.repository.ClassRoomRepository;
import com.skillmentor.root.service.ClassRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ClassRoomServiceImpl implements ClassRoomService {
    @Autowired
    private ClassRoomRepository classRoomRepository;

    @Override
    public List<ClassRoomDTO> getAllClassRooms() {
        final List<ClassRoomEntity> classRoomEntities = classRoomRepository.findAll();
        return classRoomEntities.stream().map(
                entity->{
                    final ClassRoomDTO classRoomDTO = ClassRoomEntityDTOMapper.map(entity);
                    if (entity.getMentor() != null) {
                        final MentorDTO mentorDTO = MentorEntityDTOMapper.map(entity.getMentor());
                        classRoomDTO.setMentorDTO(mentorDTO);
                    }
                    return classRoomDTO;
                }
        ).toList();
    }

    @Override
    public ClassRoomDTO findClassRoomById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ClassRoom ID must not be null.");
        }
        final Optional<ClassRoomEntity> classRoomEntity = classRoomRepository.findById(id);
        if (classRoomEntity.isEmpty()) {
            throw new ClassRoomException("ClassRoom not found");
        }
        final ClassRoomEntity entity = classRoomEntity.get();
        final ClassRoomDTO classRoomDTO = ClassRoomEntityDTOMapper.map(entity);
        if (entity.getMentor() != null) {
            final MentorDTO mentorDTO = MentorEntityDTOMapper.map(entity.getMentor());
            classRoomDTO.setMentorDTO(mentorDTO);
        }
        return classRoomDTO;
    }

    @Override
    public ClassRoomDTO deleteClassRoomById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("ClassRoom ID must not be null.");
        }
        final Optional<ClassRoomEntity> classRoomEntity = classRoomRepository.findById(id);
        if (classRoomEntity.isEmpty()) {
            throw new ClassRoomException("ClassRoom not found");
        }
        classRoomRepository.delete(Objects.requireNonNull(classRoomEntity.get()));
        return ClassRoomEntityDTOMapper.map(classRoomEntity.get());
    }

    @Override
    public ClassRoomDTO updateClassRoom(ClassRoomDTO classRoomDTO) {
        if (classRoomDTO == null) {
            throw new IllegalArgumentException("ClassRoom data must not be null.");
        }
        final Integer classRoomId = classRoomDTO.getClassRoomId();
        if (classRoomId == null) {
            throw new IllegalArgumentException("ClassRoom ID must not be null for update.");
        }
        Optional<ClassRoomEntity> classRoomEntity = classRoomRepository.findById(classRoomId);
        if (classRoomEntity.isEmpty()) {
            throw new ClassRoomException("ClassRoom not found");
        }
        final ClassRoomEntity updatedEntity = classRoomEntity.get();
        updatedEntity.setTitle(classRoomDTO.getTitle());
        updatedEntity.setEnrolledStudentCount(classRoomDTO.getEnrolledStudentCount());
        final ClassRoomEntity savedEntity = classRoomRepository.save(Objects.requireNonNull(updatedEntity));
        return ClassRoomEntityDTOMapper.map(savedEntity);
    }

    @Override
    public ClassRoomDTO createClassRoom(ClassRoomDTO classRoomDTO) {
        if (classRoomDTO == null) {
            throw new IllegalArgumentException("ClassRoom data must not be null.");
        }
        final ClassRoomEntity classRoomEntity = ClassRoomEntityDTOMapper.map(classRoomDTO);
        final ClassRoomEntity savedEntity = classRoomRepository.save(Objects.requireNonNull(classRoomEntity));
        return ClassRoomEntityDTOMapper.map(savedEntity);
    }
}