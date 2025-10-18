package com.skill_mentor.root.service.impl;

import com.skill_mentor.root.dto.StudentDTO;
import com.skill_mentor.root.entity.StudentEntity;
import com.skill_mentor.root.mapper.StudentEntityDTOMapper;
import com.skill_mentor.root.repository.StudentRepository;
import com.skill_mentor.root.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Override
    public StudentDTO createStudent(StudentDTO studentDTO) {
        if (studentRepository.findByEmail(studentDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists: " + studentDTO.getEmail());
        }
        final StudentEntity studentEntity = StudentEntityDTOMapper.map(studentDTO);
        final StudentEntity saveEntity = studentRepository.save(studentEntity);
        return StudentEntityDTOMapper.map(saveEntity);
    }

    @Override
    public List<StudentDTO> getAllStudents() {
        final List<StudentEntity> studentEntities = studentRepository.findAll();
        return studentEntities.stream().map(StudentEntityDTOMapper::map).toList();
    }

    @Override
    public StudentDTO getStudentById(Integer id) {
        Optional <StudentEntity> studentEntity = studentRepository.findById(id);
        return studentEntity.map(StudentEntityDTOMapper::map).orElse(null);
    }

    @Override
    public StudentDTO updateStudentById(StudentDTO studentDTO) {
        StudentEntity studentEntity = studentRepository.findById(studentDTO.getStudentId()).orElse(null);
        if (studentEntity != null) {
            studentEntity.setFirstName(studentDTO.getFirstName());
            studentEntity.setLastName(studentDTO.getLastName());
            studentEntity.setEmail(studentDTO.getEmail());
            studentEntity.setPhoneNumber(studentDTO.getPhoneNumber());
            studentEntity.setAddress(studentDTO.getAddress());
            studentEntity.setAge(studentDTO.getAge());
            StudentEntity updatedEntity = studentRepository.save(studentEntity);
            return StudentEntityDTOMapper.map(updatedEntity);
        }
        return null;
    }

    @Override
    public StudentDTO deleteStudentById(Integer id) {
        final StudentEntity studentEntity = studentRepository.findById(id).orElse(null);
        studentRepository.deleteById(id);
        assert studentEntity != null;
        return StudentEntityDTOMapper.map(studentEntity);
    }
}
