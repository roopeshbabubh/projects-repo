package com.roopesh.SpringWithRedis.repository;

import com.roopesh.SpringWithRedis.entity.ALangMVData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ALangMVDataRepository extends JpaRepository<ALangMVData, Long> {

    ALangMVData findByRegister(String register);
}
