package org.example.repository.custom.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Ops;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQuery;
import org.example.Salary;
import org.example.model.entity.EmployerEntity;
import org.example.model.entity.QEmployerEntity;
import org.example.model.entity.QSalaryEntity;
import org.example.model.entity.SalaryEntity;
import org.example.repository.custom.EmployerRepositoryCustom;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
public class EmployerRepositoryCustomImpl implements EmployerRepositoryCustom {
    @PersistenceContext
    private EntityManager em;
    @Override
    public Optional<EmployerEntity> findById(Long id) {
        return Optional.ofNullable(new JPAQuery<EmployerEntity>(em)
                .from(QEmployerEntity.employerEntity)
                .where(QEmployerEntity.employerEntity.id.eq(id))
                .leftJoin(QEmployerEntity.employerEntity.salaries, QSalaryEntity.salaryEntity).fetchJoin()
                .fetchFirst());
    }
    @Override
    public List<SalaryEntity> findSalaryById(Long id, int row, int page) {
        return new JPAQuery<SalaryEntity>(em)
                .from(QSalaryEntity.salaryEntity)
                .where(QSalaryEntity.salaryEntity.employer.id.eq(id))
                .limit(row)
                .offset(page)
                .fetch();
    }
    @Override
    public List<Tuple> findSalaryById(Long id) {
        return new JPAQuery<SalaryEntity>(em)
                .select(QSalaryEntity.salaryEntity.numberAVS, QSalaryEntity.salaryEntity.dateDebut, QSalaryEntity.salaryEntity.dateFinished)
                .from(QSalaryEntity.salaryEntity)
                .where(QSalaryEntity.salaryEntity.employer.id.eq(id))
                .fetch();
    }
    @Override
    public Long findByIdSalariesCount(Long id) {
        return new JPAQuery<SalaryEntity>(em)
                .from(QSalaryEntity.salaryEntity)
                .where(QSalaryEntity.salaryEntity.employer.id.eq(id))
                .fetchCount();
    }
    @Override
    public List<SalaryEntity> findSalariesByNumberAVSList(List<String> numberAVS) {
        return new JPAQuery<SalaryEntity>(em)
                .from(QSalaryEntity.salaryEntity)
                .where(QSalaryEntity.salaryEntity.numberAVS.in(numberAVS))
                .fetch();
    }
    @Override
    public Map<String, List<SalaryEntity>> findSalariesByNumberAVSListHashMap(List<String> numberAVS) {
        List<SalaryEntity> salaryEntities = new JPAQuery<SalaryEntity>(em)
                .from(QSalaryEntity.salaryEntity)
                .where(QSalaryEntity.salaryEntity.numberAVS.in(numberAVS))
                .fetch();
        return salaryEntities.stream()
                .collect(Collectors.groupingBy(SalaryEntity::getNumberAVS));
    }

    @Override
    public List<SalaryEntity> findByNumeroAVSAndDateDebutNotAndDateFinishedNot(String numeroAVS, LocalDate dateDebut, LocalDate dateFinished) {
        return new JPAQuery<SalaryEntity>(em)
                .from(QSalaryEntity.salaryEntity)
                .where(QSalaryEntity.salaryEntity.numberAVS.eq(numeroAVS)
                        .and((QSalaryEntity.salaryEntity.dateDebut.between(dateDebut, dateFinished))
                                .or(QSalaryEntity.salaryEntity.dateFinished.between(dateDebut, dateFinished))
                                .or((QSalaryEntity.salaryEntity.dateDebut.before(dateDebut).and(QSalaryEntity.salaryEntity.dateFinished.between(dateDebut, dateFinished))))
                                .or((QSalaryEntity.salaryEntity.dateDebut.between(dateDebut, dateFinished).and(QSalaryEntity.salaryEntity.dateFinished.after(dateFinished))))
                                .or(QSalaryEntity.salaryEntity.dateDebut.before(dateDebut).and(QSalaryEntity.salaryEntity.dateFinished.after(dateFinished)))))
                .fetch();
    }


    @Override
    public List<EmployerEntity> findAll() {
        return new JPAQuery<EmployerEntity>(em)
                .from(QEmployerEntity.employerEntity)
                .fetch();
    }

    @Override
    public List<EmployerEntity> findByCriteria(EmployerEntity entity, int row, int page) {
        return new JPAQuery<EmployerEntity>(em)
                .from(QEmployerEntity.employerEntity)
                .where(buildPredicate(entity))
                .orderBy(QEmployerEntity.employerEntity.id.asc())
                .limit(row)
                .offset(page)
                .fetch();
    }

    @Override
    public Long findByCriteriaCount(EmployerEntity entity) {
        return new JPAQuery<EmployerEntity>(em)
                .from(QEmployerEntity.employerEntity)
                .where(buildPredicate(entity))
                .fetchCount();
    }

    @Override
    public String findLastEmployerNumber(String number) {
        return new JPAQuery<EmployerEntity>(em)
                .select(QEmployerEntity.employerEntity.number)
                .from(QEmployerEntity.employerEntity)
                .orderBy(QEmployerEntity.employerEntity.id.desc())
                .limit(1)
                .fetchFirst();
    }

    private static BooleanBuilder buildPredicate(EmployerEntity entity) {
        BooleanBuilder predicate = new BooleanBuilder();
        Optional.ofNullable(entity.getType())
                .ifPresent(type -> addConditionEqual(Optional.of(type), predicate, QEmployerEntity.employerEntity.type));
        Optional.ofNullable(entity.getNumber())
                .ifPresent(number -> addConditionLike(Optional.of(number), predicate, QEmployerEntity.employerEntity.number));
        Optional.ofNullable(entity.getName())
                .ifPresent(name -> addConditionLikeForName(Optional.of(name.toLowerCase()), predicate, QEmployerEntity.employerEntity.name));
        Optional.ofNullable(entity.getNumberIDE())
                .ifPresent(numberIDE -> addConditionLike(Optional.of(numberIDE), predicate, QEmployerEntity.employerEntity.numberIDE));
        Optional.ofNullable(entity.getDateAffiliation())
                .ifPresent(dateAffiliation -> predicate.and(QEmployerEntity.employerEntity.dateAffiliation.eq(dateAffiliation)));
        Optional.ofNullable(entity.getDateRadiation())
                .ifPresent(dateRadiation -> predicate.and(QEmployerEntity.employerEntity.dateRadiation.eq(dateRadiation)));
        return predicate;
    }
    private static void addConditionEqual(Optional<String> value, BooleanBuilder predicate, StringPath path) {
        value.filter(str -> !str.isEmpty()).ifPresent(str -> predicate.and(path.eq(str)));
    }
    private static void addConditionLike(Optional<String> value, BooleanBuilder predicate, StringPath path) {
        value.filter(str -> !str.isEmpty()).ifPresent(str -> predicate.and(path.like("%" + str + "%")));
    }
    private static void addConditionLikeForName(Optional<String> value, BooleanBuilder predicate, StringPath path) {
        value.filter(str -> !str.isEmpty()).ifPresent(str -> predicate.and(path.toLowerCase().like("%" + str.toLowerCase() + "%")));
    }

}
