package org.example.model.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "EMPLOYEUR")
public class EmployerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "CAISSE")
    private String type;

    @Column(nullable = false, unique = true, name = "NUMERO")
    private String number;

    @Column(nullable = false, name = "NOM")
    private String name;

    @Column(nullable = false, unique = true, name = "NUMEROIDE")
    private String numberIDE;

    @Column(nullable = false, name = "DATE_AFFILIATION")
    private LocalDate dateAffiliation;

    @Column(name = "DATE_RADIATION")
    private LocalDate dateRadiation;

    @OneToMany(mappedBy = "employer", cascade = CascadeType.ALL)
    private List<SalaryEntity> salaries = new ArrayList<>();

    public void addSalaries(List<SalaryEntity> salaries) {
        setSalaries(salaries);
        salaries.forEach(salary -> salary.setEmployer(this));
    }


}
