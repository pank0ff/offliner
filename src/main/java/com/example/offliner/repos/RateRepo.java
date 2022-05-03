package com.example.offliner.repos;

import com.example.offliner.domain.Rate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RateRepo extends JpaRepository<Rate, Long> {

    List<Rate> findAll();

    List<Rate> findByUserId(Long id);

    List<Rate> findByMessageId(Integer id);

}
