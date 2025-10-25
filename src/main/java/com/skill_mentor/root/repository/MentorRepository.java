package com.skill_mentor.root.repository;

import com.skill_mentor.root.entity.MentorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MentorRepository extends JpaRepository<MentorEntity, Integer> {
}
