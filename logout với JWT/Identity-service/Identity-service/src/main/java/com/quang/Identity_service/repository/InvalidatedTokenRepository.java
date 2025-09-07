package com.quang.Identity_service.repository;

import com.quang.Identity_service.entity.InvalidatedToken;
import com.quang.Identity_service.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvalidatedTokenRepository extends JpaRepository<InvalidatedToken, String> {



}
