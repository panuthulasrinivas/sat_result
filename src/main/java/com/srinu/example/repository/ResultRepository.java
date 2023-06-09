package com.srinu.example.repository;

import com.srinu.example.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ResultRepository extends JpaRepository<Result,String> {

    @Query(value = "select count(r) from Result r where r.satScore >= :satScore")
    int getRank(@Param("satScore") Integer satScore);
}
