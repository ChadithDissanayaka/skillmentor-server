package com.skillmentor.root.controller;

import com.skillmentor.root.dto.ClassRoomDTO;
import com.skillmentor.root.exception.ClassRoomException;
import com.skillmentor.root.service.ClassRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/classroom")
public class ClassroomController {

    @Autowired
    private ClassRoomService classRoomService;

    // CREATE classroom
    @PostMapping
    public ResponseEntity<ClassRoomDTO> createClassRoom(
            @RequestBody ClassRoomDTO classRoomDTO
    ) {
        ClassRoomDTO savedClassRoom = classRoomService.createClassRoom(classRoomDTO);
        return new ResponseEntity<>(savedClassRoom, HttpStatus.CREATED);
    }

    // GET all classrooms
    @GetMapping
    public ResponseEntity<List<ClassRoomDTO>> getAllClassRooms() {
        List<ClassRoomDTO> classRooms = classRoomService.getAllClassRooms();
        return new ResponseEntity<>(classRooms, HttpStatus.OK);
    }

    // GET classroom by ID
    @GetMapping("/{id}")
    public ResponseEntity<ClassRoomDTO> getClassRoomById(
            @PathVariable Integer id
    ) throws ClassRoomException {
        ClassRoomDTO classRoom = classRoomService.findClassRoomById(id);
        return new ResponseEntity<>(classRoom, HttpStatus.OK);
    }

    // UPDATE classroom
    @PutMapping
    public ResponseEntity<ClassRoomDTO> updateClassRoom(
            @RequestBody ClassRoomDTO classRoomDTO
    ) throws ClassRoomException {
        ClassRoomDTO updatedClassRoom = classRoomService.updateClassRoom(classRoomDTO);
        return new ResponseEntity<>(updatedClassRoom, HttpStatus.OK);
    }

    // DELETE classroom
    @DeleteMapping("/{id}")
    public ResponseEntity<ClassRoomDTO> deleteClassRoom(
            @PathVariable Integer id
    ) throws ClassRoomException {
        ClassRoomDTO deletedClassRoom = classRoomService.deleteClassRoomById(id);
        return new ResponseEntity<>(deletedClassRoom, HttpStatus.OK);
    }
}
