package com.example.demo.repo;

import com.example.demo.DTO.NotificationCarNumberDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NotificationCarNumberRepoInterface extends JpaRepository<NotificationCarNumberDTO, Long> {
    Optional<NotificationCarNumberDTO> findBycarN(String carNumber);

//    @Query("SELECT toPhoneNumber from NotificationCarNumberDTO n where n.carN =: carNumber")
}
