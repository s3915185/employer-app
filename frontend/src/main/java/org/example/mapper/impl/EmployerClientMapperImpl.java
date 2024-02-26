package org.example.mapper.impl;

import org.example.Employer;
import org.example.EmployerNoSalaries;
import org.example.EmployerSearchCriteria;
import org.example.Salary;
import org.example.mapper.EmployerClientMapper;
import org.example.model.enums.PensionFund;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class EmployerClientMapperImpl implements EmployerClientMapper {
    @Override
    public EmployerSearchCriteria toEmployerSearchCriteria(String type, String number, String name, String numberIDE, LocalDate dateAffiliation, LocalDate dateRadiation) {
        return EmployerSearchCriteria.newBuilder()
                .setType(getOptionalString(type).orElse(""))
                .setNumber(getOptionalString(number).orElse(""))
                .setName(getOptionalString(name).orElse(""))
                .setNumberIDE(getOptionalString(numberIDE).orElse(""))
                .setDateAffiliation(getOptionalLocalDate(dateAffiliation))
                .setDateRadiation(getOptionalLocalDate(dateRadiation))
                .build();
    }

    @Override
    public EmployerSearchCriteria toEmployerSearchCriteria(String type, String number, String name, String numberIDE, LocalDate dateAffiliation, LocalDate dateRadiation, int row, int page) {
        return EmployerSearchCriteria.newBuilder()
                .setType(getOptionalString(type).orElse(""))
                .setNumber(getOptionalString(number).orElse(""))
                .setName(getOptionalString(name).orElse(""))
                .setNumberIDE(getOptionalString(numberIDE).orElse(""))
                .setDateAffiliation(getOptionalLocalDate(dateAffiliation))
                .setDateRadiation(getOptionalLocalDate(dateRadiation))
                .setMaxRow(row)
                .setPageIndex(page)
                .build();
    }

    @Override
    public Employer toEmployer(String type, String number, String name, String numberIDE, LocalDate dateAffiliation, LocalDate dateRadiation, List<Salary> salaries) {
        return Employer.newBuilder()
                .setType(type)
                .setNumber(number)
                .setName(name)
                .setNumberIDE(numberIDE)
                .setDateAffiliation(getOptionalLocalDate(dateAffiliation))
                .setDateRadiation(getOptionalLocalDate(dateRadiation))
                .addAllSalaries(Optional.ofNullable(salaries).orElse(Collections.emptyList()))
                .build();
    }

    @Override
    public EmployerNoSalaries toEmployerNoSalaries(Employer employer) {
        return EmployerNoSalaries.newBuilder()
                .setId(employer.getId())
                .setName(employer.getName())
                .setType(employer.getType())
                .setNumber(employer.getNumber())
                .setNumberIDE(employer.getNumberIDE())
                .setDateAffiliation(employer.getDateAffiliation())
                .setDateRadiation(employer.getDateRadiation())
                .build();
    }

    @Override
    public EmployerNoSalaries toEmployerNoSalariesDisplayType(EmployerNoSalaries employer, Locale locale) {
        return EmployerNoSalaries.newBuilder()
                .setId(employer.getId())
                .setName(employer.getName())
                .setType(PensionFund.getOtherLanguageDisplayVersionResourceBundle(employer.getType(), locale))
                .setNumber(employer.getNumber())
                .setNumberIDE(employer.getNumberIDE())
                .setDateAffiliation(employer.getDateAffiliation())
                .setDateRadiation(employer.getDateRadiation())
                .build();
    }

    @Override
    public List<EmployerNoSalaries> toEmployersNoSalariesDisplayType(List<EmployerNoSalaries> employerNoSalaries, Locale locale) {
        return employerNoSalaries.stream().map(employer -> toEmployerNoSalariesDisplayType(employer, locale)).collect(Collectors.toList());
    }

    @Override
    public EmployerNoSalaries toEmployerNoSalariesDatabaseDefined(EmployerNoSalaries employer, Locale locale) {
        EmployerNoSalaries.Builder anotherEmployer = EmployerNoSalaries.newBuilder();
        anotherEmployer.setId(employer.getId());
        anotherEmployer.setType(PensionFund.getDatabaseVersion(employer.getType(), locale));
        anotherEmployer.setNumber(employer.getNumber());
        anotherEmployer.setName(employer.getName());
        anotherEmployer.setNumberIDE(employer.getNumberIDE());
        anotherEmployer.setDateAffiliation(employer.getDateAffiliation());
        Optional.of(employer.getDateRadiation()).ifPresent(anotherEmployer::setDateRadiation);
        return anotherEmployer.build();
    }

    @Override
    public Employer toEmployer(Long id, String type, String number, String name, String numberIDE, LocalDate dateAffiliation, LocalDate dateRadiation, List<Salary> salaries) {
        return Employer.newBuilder()
                .setId(id)
                .setType(type)
                .setNumber(number)
                .setName(name)
                .setNumberIDE(numberIDE)
                .setDateAffiliation(getOptionalLocalDate(dateAffiliation))
                .setDateRadiation(getOptionalLocalDate(dateRadiation))
                .addAllSalaries(Optional.ofNullable(salaries).orElse(Collections.emptyList()))
                .build();
    }

    private Optional<String> getOptionalString(String value) {
        return Optional.ofNullable(value).filter(str -> !str.isEmpty());
    }
    private String getOptionalLocalDate(LocalDate value) {
        if (value == null) {
            return "";
        }else {
            return String.valueOf(value);
        }
    }
}
