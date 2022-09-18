package com.povorozniuk.backend.repository;

import com.povorozniuk.backend.entity.PracticeDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PracticeDayRepository extends JpaRepository<PracticeDay, LocalDate> {
    Optional<PracticeDay> findPracticeDayByDay(LocalDate date);

    List<PracticeDay> findAllByDayIsBetween(LocalDate start, LocalDate end);

}