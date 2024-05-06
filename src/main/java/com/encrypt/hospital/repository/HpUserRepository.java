package com.encrypt.hospital.repository;

import com.encrypt.hospital.model.HpUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HpUserRepository extends JpaRepository<HpUser, Integer> {
    HpUser findByUserName(String userName);

    List<HpUser> findByType(String type);
}