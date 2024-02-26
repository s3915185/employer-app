package org.example.mapper;

import org.example.Employer;
import org.example.EmployerNoSalaries;
import org.example.EmployerSearchCriteria;
import org.example.model.entity.EmployerEntity;

import java.util.List;

public interface EmployerMapper {
    public EmployerEntity toEmployerEntity(EmployerSearchCriteria entity);
    public EmployerEntity toEmployerEntity(Employer entity);
    public Employer toEmployerMessage(EmployerEntity entity);

    public EmployerNoSalaries toEmployerNoSalariesMessage(EmployerEntity entity);
    public List<EmployerNoSalaries> toEmployerNoSalariesMessage(List<EmployerEntity> entities);

}
