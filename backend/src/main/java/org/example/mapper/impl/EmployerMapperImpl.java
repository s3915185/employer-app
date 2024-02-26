package org.example.mapper.impl;


import org.example.Employer;
import org.example.EmployerNoSalaries;
import org.example.EmployerSearchCriteria;
import org.example.Salary;
import org.example.mapper.EmployerMapper;
import org.example.mapper.SalaryMapper;
import org.example.model.Type;
import org.example.model.entity.EmployerEntity;
import org.example.model.entity.SalaryEntity;
import org.example.util.Utils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class EmployerMapperImpl implements EmployerMapper {
    @Autowired
    private SalaryMapper salaryMapper;
    @Override
    public Employer toEmployerMessage(EmployerEntity entity) {
        Hibernate.initialize(entity.getSalaries());

        Employer.Builder builder = Employer.newBuilder();
        builder.setId(entity.getId());
        builder.setType(Type.getClientType(entity.getType()));
        builder.setNumber(entity.getNumber());
        builder.setName(entity.getName());
        builder.setNumberIDE(entity.getNumberIDE());
        builder.setDateAffiliation(entity.getDateAffiliation().toString());
        Optional.ofNullable(entity.getDateRadiation()).ifPresent(value -> builder.setDateRadiation(value.toString()));
        builder.addAllSalaries(convertSalaries(entity.getSalaries()));
        return builder.build();
    }

    @Override
    public EmployerNoSalaries toEmployerNoSalariesMessage(EmployerEntity entity) {

        EmployerNoSalaries.Builder builder = EmployerNoSalaries.newBuilder();
        builder.setId(entity.getId());
        builder.setType(Type.getClientType(entity.getType()));
        builder.setNumber(entity.getNumber());
        builder.setName(entity.getName());
        builder.setNumberIDE(entity.getNumberIDE());
        builder.setDateAffiliation(entity.getDateAffiliation().toString());
        Optional.ofNullable(entity.getDateRadiation()).ifPresent(value -> builder.setDateRadiation(value.toString()));
        return builder.build();
    }

    private List<Salary> convertSalaries(List<SalaryEntity> salaryEntities) {
        // Convert SalaryEntity instances to Salary messages
        return salaryEntities.stream()
                .map(salary -> salaryMapper.toSalaryMessage(salary))
                .collect(Collectors.toList());
    }
    @Override
    public List<EmployerNoSalaries> toEmployerNoSalariesMessage(List<EmployerEntity> entities) {
        return entities.stream()
                .map(this::toEmployerNoSalariesMessage).collect(Collectors.toList());
    }

    @Override
    public EmployerEntity toEmployerEntity(EmployerSearchCriteria entity) {
        EmployerEntity.EmployerEntityBuilder builder = EmployerEntity.builder();
        Optional.of(entity.getType()).ifPresent(type -> builder.type(Type.getDatabaseType(type)));
        Optional.of(entity.getNumber()).ifPresent(builder::number);
        Optional.of(entity.getName()).ifPresent(builder::name);
        Optional.of(entity.getNumberIDE()).ifPresent(builder::numberIDE);
        Optional.of(Utils.toLocalDate(entity.getDateAffiliation())).ifPresent(localDate -> builder.dateAffiliation(localDate.orElse(null)));
        Optional.of(Utils.toLocalDate(entity.getDateRadiation())).ifPresent(localDate -> builder.dateRadiation(localDate.orElse(null)));
        return builder.build();
    }

    @Override
    public EmployerEntity toEmployerEntity(Employer entity) {
        EmployerEntity.EmployerEntityBuilder builder = EmployerEntity.builder();
        builder.type(Type.getDatabaseType(entity.getType()));
        builder.number(entity.getNumber());
        builder.name(entity.getName());
        builder.numberIDE(entity.getNumberIDE());
        // Or Else Throw Error for this one
        Optional.of(Utils.toLocalDate(entity.getDateAffiliation())).ifPresent(localDate -> builder.dateAffiliation(localDate.orElse(null)));
        //
        Optional.of(Utils.toLocalDate(entity.getDateRadiation())).ifPresent(localDate -> builder.dateRadiation(localDate.orElse(null)));
        EmployerEntity employer = builder.build();
        Optional.of(entity.getSalariesList()).ifPresent(salaries -> {
            employer.addSalaries(salaryMapper.toSalaryEntity(salaries));
        });
        return employer;
    }


}
