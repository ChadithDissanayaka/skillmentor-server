package com.skill_mentor.root.service;

import com.skill_mentor.root.dto.ClassRoomDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service interface for managing classroom operations such as
 * fetching, creating, updating, and deleting classrooms.
 */
@Service
public interface ClassRoomService {

    /**
     * Retrieves all classrooms filtered by optional parameters.
     * @return a list of {@link ClassRoomDTO} that match the filters
     */
    List<ClassRoomDTO> getAllClassRooms();

    /**
     * Finds a classroom by its ID.
     *
     * @param id the unique ID of the classroom
     * @return the matching {@link ClassRoomDTO}, or {@code null} if not found
     */
    ClassRoomDTO findClassRoomById(Integer id);

    /**
     * Deletes a classroom by its ID.
     *
     * @param id the ID of the classroom to delete
     * @return the deleted {@link ClassRoomDTO}, or {@code null} if not found
     */
    ClassRoomDTO deleteClassRoomById(Integer id);

    /**
     * Updates an existing classroom.
     *
     * @param classRoomDTO the updated classroom details
     * @return the updated {@link ClassRoomDTO}, or {@code null} if the classroom does not exist
     */
    ClassRoomDTO updateClassRoom(ClassRoomDTO classRoomDTO);

    /**
     * Creates a new classroom with the provided details.
     *
     * @param classRoomDTO the new classroom data
     * @return the created {@link ClassRoomDTO} with generated ID
     */
    ClassRoomDTO createClassRoom(ClassRoomDTO classRoomDTO);
}