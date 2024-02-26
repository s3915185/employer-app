package org.example.repository;

import org.example.model.entity.EmployerEntity;
import org.example.repository.custom.EmployerRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployerRepository extends JpaRepository<EmployerEntity, Long>, QuerydslPredicateExecutor<EmployerEntity>{

}
