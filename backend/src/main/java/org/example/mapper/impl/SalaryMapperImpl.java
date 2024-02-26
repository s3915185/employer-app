package org.example.mapper.impl;

import org.example.Salary;
import org.example.mapper.SalaryMapper;
import org.example.model.entity.SalaryEntity;
import org.example.util.Utils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class SalaryMapperImpl implements SalaryMapper {
    @Override
    public SalaryEntity toSalaryEntity(Salary salary) {
        SalaryEntity.SalaryEntityBuilder builder = SalaryEntity.builder();
        builder.numberAVS(salary.getNumberAVS());
        builder.name(salary.getName());
        builder.firstName(salary.getFirstName());
        Optional.of(Utils.toLocalDateFromFile(salary.getDateDebut())).ifPresent(localDate -> builder.dateDebut(localDate.orElse(null)));
        Optional.of(Utils.toLocalDateFromFile(salary.getDateFinished())).ifPresent(localDate -> builder.dateFinished(localDate.orElse(null)));
        builder.numberAvsAiApg(salary.getNumberAvsAiApg());
        builder.numberAC(salary.getNumberAc());
        builder.numberAF(salary.getNumberAf());
        return builder.build();
    }

    @Override
    public List<SalaryEntity> toSalaryEntity(List<Salary> salaries) {
        return salaries.stream()
                .map(this::toSalaryEntity).collect(Collectors.toList());
    }

    @Override
    public Salary toSalaryMessage(SalaryEntity salaryEntity) {
        return Salary.newBuilder()
                .setId(salaryEntity.getId())
                .setNumberAVS(salaryEntity.getNumberAVS())
                .setName(salaryEntity.getName())
                .setFirstName(salaryEntity.getFirstName())
                .setDateDebut(salaryEntity.getDateDebut().toString())
                .setDateFinished(salaryEntity.getDateFinished() != null ? salaryEntity.getDateFinished().toString() : "")  // Handling nullable date
                .setNumberAvsAiApg(salaryEntity.getNumberAvsAiApg())
                .setNumberAc(salaryEntity.getNumberAC())  // Adjusted field name to match the entity
                .setNumberAf(salaryEntity.getNumberAF())  // Adjusted field name to match the entity
                .build();
    }

    @Override
    public List<Salary> toSalariesMessage(List<SalaryEntity> salaryEntities) {
        return salaryEntities.stream()
                .map(this::toSalaryMessage).collect(Collectors.toList());
    }
}
