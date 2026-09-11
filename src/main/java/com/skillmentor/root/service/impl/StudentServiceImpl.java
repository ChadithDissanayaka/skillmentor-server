package com.skillmentor.root.service.impl;

import com.skillmentor.root.dto.StudentDTO;
import com.skillmentor.root.entity.StudentEntity;
import com.skillmentor.root.exception.StudentException;
import com.skillmentor.root.mapper.StudentEntityDTOMapper;
import com.skillmentor.root.repository.StudentRepository;
import com.skillmentor.root.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentRepository studentRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = { "studentCache", "allStudentsCache" }, allEntries = true)
    public StudentDTO createStudent(final StudentDTO studentDTO) {
        log.info("Creating new student...");
        log.debug("StudentDTO received: {}", studentDTO);

        if (studentDTO == null) {
            log.error("Failed to create student: input DTO is null.");
            throw new IllegalArgumentException("Student data must not be null.");
        }

        // First check if student already exists by clerk ID
        try {
            Optional<StudentEntity> existingStudent = studentRepository
                    .findByClerkStudentId(studentDTO.getClerkStudentId());
            if (existingStudent.isPresent()) {
                log.info("Student already exists with clerk ID: {}", studentDTO.getClerkStudentId());
                return StudentEntityDTOMapper.map(existingStudent.get());
            }

            final StudentEntity studentEntity = StudentEntityDTOMapper.map(studentDTO);
            log.debug("Mapped StudentEntity: {}", studentEntity);

            final StudentEntity savedEntity = studentRepository.save(Objects.requireNonNull(studentEntity));
            log.info("Student created with ID: {}", savedEntity.getStudentId());

            return StudentEntityDTOMapper.map(savedEntity);
        } catch (DataIntegrityViolationException e) {
            log.error("Data integrity violation while creating student: {}", e.getMessage());
            // Retry finding the student in case it was created concurrently
            return studentRepository.findByClerkStudentId(studentDTO.getClerkStudentId())
                    .map(StudentEntityDTOMapper::map)
                    .orElseThrow(
                            () -> new StudentException("Failed to create student due to data integrity violation"));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Cacheable(value = "allStudentsCache", key = "'allStudents'")
    public List<StudentDTO> getAllStudents(final List<String> addresses, final List<Integer> ages,
            final List<String> firstNames) {
        log.info("Fetching all students with filters — addresses: {}, ages: {}, firstNames: {}", addresses, ages,
                firstNames);

        final List<StudentEntity> studentEntities = studentRepository.findAll();
        log.debug("Total students fetched from DB: {}", studentEntities.size());

        final List<StudentDTO> result = studentEntities
                .stream()
                .filter(student -> addresses == null || addresses.contains(student.getAddress()))
                .filter(student -> ages == null || ages.contains(student.getAge()))
                .filter(student -> firstNames == null || firstNames.contains(student.getFirstName()))
                .map(StudentEntityDTOMapper::map)
                .toList();

        log.info("Found {} students after filtering", result.size());
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Cacheable(value = "studentCache", key = "#id")
    public StudentDTO findStudentById(final Integer id) {
        log.info("Fetching student with ID: {}", id);

        if (id == null) {
            log.error("Failed to fetch student: ID is null.");
            throw new IllegalArgumentException("Student ID must not be null.");
        }

        return studentRepository.findById(id)
                .map(entity -> {
                    log.debug("Student found: {}", entity);
                    return StudentEntityDTOMapper.map(entity);
                })
                .orElseThrow(() -> {
                    log.error("Student not found with ID: {}", id);
                    return new StudentException("Student not found with ID: " + id);
                });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CachePut(value = "studentCache", key = "#studentDTO.studentId")
    @CacheEvict(value = "allStudentsCache", allEntries = true)
    public StudentDTO updateStudentById(final StudentDTO studentDTO) {
        log.info("Updating student...");
        log.debug("StudentDTO received for update: {}", studentDTO);

        if (studentDTO == null) {
            log.error("Failed to update student: DTO is null.");
            throw new IllegalArgumentException("Student data must not be null for update.");
        }

        final Integer studentId = studentDTO.getStudentId();
        if (studentId == null) {
            log.error("Failed to update student: student ID is null.");
            throw new IllegalArgumentException("Student ID must not be null for update.");
        }

        log.debug("Looking up student with ID: {}", studentId);
        final StudentEntity studentEntity = studentRepository.findById(studentId)
                .orElseThrow(() -> {
                    log.error("Cannot update. Student not found with ID: {}", studentId);
                    return new StudentException("Cannot update. Student not found with ID: " + studentId);
                });

        studentEntity.setFirstName(studentDTO.getFirstName());
        studentEntity.setLastName(studentDTO.getLastName());
        studentEntity.setEmail(studentDTO.getEmail());
        studentEntity.setPhoneNumber(studentDTO.getPhoneNumber());
        studentEntity.setAddress(studentDTO.getAddress());
        studentEntity.setAge(studentDTO.getAge());
        log.debug("Student fields updated in memory: {}", studentEntity);

        final StudentDTO updatedDTO = StudentEntityDTOMapper
                .map(studentRepository.save(Objects.requireNonNull(studentEntity)));
        log.info("Student updated successfully with ID: {}", updatedDTO.getStudentId());

        return updatedDTO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = { "studentCache", "allStudentsCache" }, key = "#id")
    public StudentDTO deleteStudentById(final Integer id) {
        log.info("Deleting student with ID: {}", id);

        if (id == null) {
            log.error("Failed to delete student: ID is null.");
            throw new IllegalArgumentException("Student ID must not be null.");
        }

        final StudentEntity studentEntity = studentRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Cannot delete. Student not found with ID: {}", id);
                    return new StudentException("Cannot delete. Student not found with ID: " + id);
                });

        studentRepository.delete(Objects.requireNonNull(studentEntity));
        log.info("Student deleted successfully with ID: {}", id);
        log.debug("Deleted student details: {}", studentEntity);

        return StudentEntityDTOMapper.map(studentEntity);
    }

    @Override
    public StudentDTO findStudentByClerkId(String clerkId) {
        log.info("Fetching student by clerk ID: {}", clerkId);
        return studentRepository.findByClerkStudentId(clerkId)
                .map(StudentEntityDTOMapper::map)
                .orElseThrow(() -> {
                    log.error("Student not found with clerk ID: {}", clerkId);
                    return new StudentException("Student not found with clerk ID: " + clerkId);
                });
    }

    @Override
    public StudentDTO deleteStudentByClerkId(String clerkId) throws StudentException {
        log.info("Deleting student with clerk ID: {}", clerkId);
        final StudentEntity studentEntity = studentRepository.findByClerkStudentId(clerkId)
                .orElseThrow(() -> {
                    log.error("Cannot delete. Student not found with clerk ID: {}", clerkId);
                    return new StudentException("Cannot delete. Student not found with clerk ID: " + clerkId);
                });
        studentRepository.delete(studentEntity);
        log.info("Student with clerk ID {} deleted successfully", clerkId);
        return StudentEntityDTOMapper.map(studentEntity);
    }
}