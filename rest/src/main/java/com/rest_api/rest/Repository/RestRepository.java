package com.rest_api.rest.Repository;

import com.rest_api.rest.Entity.RestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestRepository extends JpaRepository<RestEntity, Long> {
}