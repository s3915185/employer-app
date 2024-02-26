package org.example.mapper;

import org.example.Salary;
import org.example.model.entity.SalaryEntity;

import java.util.List;

public interface SalaryMapper {
    public SalaryEntity toSalaryEntity(Salary salary);
    public List<SalaryEntity> toSalaryEntity(List<Salary> salaries);
    public Salary toSalaryMessage(SalaryEntity salaryEntity);
    public List<Salary> toSalariesMessage(List<SalaryEntity> salaryEntities);
}
