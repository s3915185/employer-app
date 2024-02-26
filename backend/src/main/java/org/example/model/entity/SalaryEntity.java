package org.example.model.entity;

import lombok.*;
import org.example.repository.SalaryRepository;
import org.example.repository.custom.EmployerRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "SALARY")
//@Table(name = "SALARY", uniqueConstraints = {
//        @UniqueConstraint(columnNames = {"NUMEROAVS", "DATE_DEBUT", "DATE_FINISHED"})
//})
public class SalaryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "NUMEROAVS")
    private String numberAVS;

    @Column(nullable = false, name = "NOM")
    private String name;

    @Column(nullable = false, name = "PRENOM")
    private String firstName;

    @Column(nullable = false, name = "DATE_DEBUT")
    private LocalDate dateDebut;

    @Column(nullable = false, name = "DATE_FIN")
    private LocalDate dateFinished;

    @Column(nullable = false, name = "AVS_AI_APG", precision = 17, scale = 2)
    private float numberAvsAiApg;

    @Column(nullable = false, name = "AC", precision = 17, scale = 2)
    private float numberAC;

    @Column(nullable = false, name = "AF", precision = 17, scale = 2)
    private float numberAF;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "EMPLOYEUR_ID")
    private EmployerEntity employer;
}