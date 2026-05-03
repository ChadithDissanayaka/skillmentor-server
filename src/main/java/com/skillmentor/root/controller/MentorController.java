package com.skillmentor.root.controller;

import com.skillmentor.root.dto.MentorDTO;
import com.skillmentor.root.exception.MentorException;
import com.skillmentor.root.service.MentorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/mentor")
public class MentorController {

    @Autowired
    private MentorService mentorService;

    // CREATE mentor
    @PostMapping
    public ResponseEntity<MentorDTO> createMentor(@RequestBody MentorDTO mentorDTO) throws MentorException {
        MentorDTO savedMentor = mentorService.createMentor(mentorDTO);
        return new ResponseEntity<>(savedMentor, HttpStatus.CREATED);
    }

    // GET all mentors with optional filters
    @GetMapping
    public ResponseEntity<List<MentorDTO>> getAllMentors(
            @RequestParam(required = false) List<String> firstNames,
            @RequestParam(required = false) List<String> subjects
    ) {
        List<MentorDTO> mentors = mentorService.getAllMentors(firstNames, subjects);
        return new ResponseEntity<>(mentors, HttpStatus.OK);
    }

    // GET mentor by ID
    @GetMapping("/{id}")
    public ResponseEntity<MentorDTO> getMentorById(@PathVariable Integer id) throws MentorException {
        MentorDTO mentor = mentorService.findMentorById(id);
        return new ResponseEntity<>(mentor, HttpStatus.OK);
    }

    // UPDATE mentor
    @PutMapping
    public ResponseEntity<MentorDTO> updateMentor(@RequestBody MentorDTO mentorDTO) throws MentorException {
        MentorDTO updatedMentor = mentorService.updateMentorById(mentorDTO);
        return new ResponseEntity<>(updatedMentor, HttpStatus.OK);
    }

    // DELETE mentor
    @DeleteMapping("/{id}")
    public ResponseEntity<MentorDTO> deleteMentor(@PathVariable Integer id) throws MentorException {
        MentorDTO deletedMentor = mentorService.deleteMentorById(id);
        return new ResponseEntity<>(deletedMentor, HttpStatus.OK);
    }
}
