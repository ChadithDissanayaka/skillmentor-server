package com.skill_mentor.root.service.impl;

import com.skill_mentor.root.dto.ClassRoomDTO;
import com.skill_mentor.root.dto.MentorDTO;
import com.skill_mentor.root.entity.ClassRoomEntity;
import com.skill_mentor.root.mapper.ClassRoomEntityDTOMapper;
import com.skill_mentor.root.mapper.MentorEntityDTOMapper;
import com.skill_mentor.root.repository.ClassRoomRepository;
import com.skill_mentor.root.service.ClassRoomService;
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
        List<ClassRoomEntity> classRoomEntities = classRoomRepository.findAll();
        List<ClassRoomDTO> classRoomDTOS = classRoomEntities.stream().map(
                entity -> {
                    ClassRoomDTO classRoomDTO = ClassRoomEntityDTOMapper.map(entity);
                    if (!Objects.isNull(entity.getMentorEntity())) {
                        MentorDTO mentorDTO = MentorEntityDTOMapper.map(entity.getMentorEntity());
                        classRoomDTO.setMentorDTO(mentorDTO);
                    }
                    return classRoomDTO;
                }
        ).toList();
        return classRoomDTOS;
    }

    @Override
    public ClassRoomDTO findClassRoomById(Integer id) {
        Optional<ClassRoomEntity> classRoomEntity = classRoomRepository.findById(id);
        if (classRoomEntity.isEmpty()) {
            throw new RuntimeException("ClassRoom not found");
        }
        return ClassRoomEntityDTOMapper.map(classRoomEntity.get());
    }

    @Override
    public ClassRoomDTO deleteClassRoomById(Integer id) {
        Optional<ClassRoomEntity> classRoomEntity = classRoomRepository.findById(id);
        if (classRoomEntity.isEmpty()) {
            throw new RuntimeException("ClassRoom not found");
        }
        classRoomRepository.deleteById(id);
        return ClassRoomEntityDTOMapper.map(classRoomEntity.get());
    }

    @Override
    public ClassRoomDTO updateClassRoom(ClassRoomDTO classRoomDTO) {
        Optional<ClassRoomEntity> classRoomEntity = classRoomRepository.findById(classRoomDTO.getClassRoomId());
        if (classRoomEntity.isEmpty()) {
            throw new RuntimeException("ClassRoom not found");
        }
        ClassRoomEntity updatedEntity = classRoomEntity.get();
        updatedEntity.setTitle(classRoomDTO.getTitle());
        updatedEntity.setSessionFee(classRoomDTO.getSessionFee());
        updatedEntity.setEnrolledStudentCount(classRoomDTO.getEnrolledStudentCount());
        ClassRoomEntity savedEntity = classRoomRepository.save(updatedEntity);
        return ClassRoomEntityDTOMapper.map(savedEntity);
    }

    @Override
    public ClassRoomDTO createClassRoom(ClassRoomDTO classRoomDTO) {
       final  ClassRoomEntity classRoomEntity = ClassRoomEntityDTOMapper.map(classRoomDTO);
       return  ClassRoomEntityDTOMapper.map(classRoomRepository.save(classRoomEntity));
    }
}
