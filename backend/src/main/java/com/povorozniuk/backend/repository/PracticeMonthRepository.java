package com.povorozniuk.backend.repository;

import com.povorozniuk.backend.entity.PracticeMonth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PracticeMonthRepository extends JpaRepository<PracticeMonth, LocalDate> {

    List<PracticeMonth> findAllByMonthBetween(LocalDate start, LocalDate end);

}
