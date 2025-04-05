package com.pharmeast.consent.repository;

import com.pharmeast.consent.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<Service, String> {
}
