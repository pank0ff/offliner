package com.example.offliner.repos;

import com.example.offliner.domain.Rate;
import com.example.offliner.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RateRepo extends JpaRepository<Rate, Long> {
    @EntityGraph(attributePaths = {"rates"})
    List<Rate> findAll();

    List<Rate> findByUserId(Long id);

    Rate findById(Integer id);


    List<Rate> findByMessageId(Integer id);

}
