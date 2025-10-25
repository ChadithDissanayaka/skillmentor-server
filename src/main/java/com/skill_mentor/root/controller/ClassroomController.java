package com.skill_mentor.root.controller;

import com.skill_mentor.root.dto.ClassRoomDTO;
import com.skill_mentor.root.service.ClassRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/classroom")
public class ClassroomController {

    @Autowired
    private ClassRoomService classRoomService;

    @PostMapping()
    public ResponseEntity<ClassRoomDTO> createClassroom(@RequestBody ClassRoomDTO classroomDTO) {
        ClassRoomDTO savedDTO = classRoomService.createClassRoom(classroomDTO);
        return new ResponseEntity<>(savedDTO, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<ClassRoomDTO>> getAllClassrooms() {
        List<ClassRoomDTO> classroomDTOS = classRoomService.getAllClassRooms();
        return new ResponseEntity<>(classroomDTOS, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassRoomDTO> findClassroomById(@PathVariable Integer id) {
        ClassRoomDTO classroom = classRoomService.findClassRoomById(id);
        return new ResponseEntity<>(classroom, HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<ClassRoomDTO> updateClassroom(@RequestBody ClassRoomDTO classroomDTO) {
        ClassRoomDTO classroom = classRoomService.updateClassRoom(classroomDTO);
        return new ResponseEntity<>(classroom, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ClassRoomDTO> deleteClassroom(@PathVariable Integer id) {
        ClassRoomDTO classroom = classRoomService.deleteClassRoomById(id);
        return new ResponseEntity<>(classroom, HttpStatus.OK);
    }
}

