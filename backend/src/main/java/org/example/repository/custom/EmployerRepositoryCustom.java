package org.example.repository.custom;

import com.querydsl.core.Tuple;
import org.example.Salary;
import org.example.model.entity.EmployerEntity;
import org.example.model.entity.SalaryEntity;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface EmployerRepositoryCustom {
    Optional<EmployerEntity> findById(Long id);
    List<SalaryEntity> findSalaryById(Long id, int row, int page);
    Long findByIdSalariesCount(Long id);

    List<SalaryEntity> findSalariesByNumberAVSList(List<String> numberAVS);

    Map<String, List<SalaryEntity>> findSalariesByNumberAVSListHashMap(List<String> numberAVS);
    List<SalaryEntity> findByNumeroAVSAndDateDebutNotAndDateFinishedNot(String numberAVS, LocalDate dateDebut, LocalDate dateFinished);

    List<Tuple> findSalaryById(Long id);

    List<EmployerEntity> findAll();

    List<EmployerEntity> findByCriteria(EmployerEntity entity, int row, int page);
    Long findByCriteriaCount(EmployerEntity entity);

    String findLastEmployerNumber(String number);
}
