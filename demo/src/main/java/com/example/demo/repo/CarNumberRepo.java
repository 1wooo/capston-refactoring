package com.example.demo.repo;

import com.example.demo.Entity.CarNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarNumberRepo extends JpaRepository<CarNumber, Long>, MonthChartRepoInterface {
    Optional<CarNumber> findByCarN(String CarNumber);
}
