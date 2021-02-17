package com.example.demo.repository;

import com.example.demo.model.AppRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppRoleRepository extends CrudRepository<AppRole, Long> {
}
