package com.povorozniuk.backend.repository;

import com.povorozniuk.backend.entity.ActionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ActionHistoryRepository extends JpaRepository<ActionHistory, Integer> {

    @Query(nativeQuery = true, value = "select count(*) from action_history where date(time) = current_date")
    Integer getCountForToday();

}
