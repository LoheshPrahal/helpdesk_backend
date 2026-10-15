package com.springboot.helpdesk.repository;

import com.springboot.helpdesk.model.Executive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExecutiveRepository extends JpaRepository<Executive, Long> {
}
