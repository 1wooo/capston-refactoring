package com.example.demo.repo;

import com.example.demo.DTO.NotificationCarNumberDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotificationCarNumberRepo extends JpaRepository<NotificationCarNumberDTO, Long> {
    Optional<NotificationCarNumberDTO> findBycarN(String carNumber);
}
