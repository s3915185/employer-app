package org.example.service.impl;

import com.querydsl.core.Tuple;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.example.*;
import org.example.mapper.EmployerMapper;
import org.example.mapper.SalaryMapper;
import org.example.model.Type;
import org.example.model.entity.EmployerEntity;
import org.example.model.entity.SalaryEntity;
import org.example.repository.EmployerRepository;
import org.example.repository.SalaryRepository;
import org.example.repository.custom.EmployerRepositoryCustom;
import org.example.service.EmployerGrpcServiceInterface;
import org.example.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Transactional
public class EmployerGrpcServiceInterfaceImpl implements EmployerGrpcServiceInterface {
    @Autowired
    private EmployerRepositoryCustom employerRepositoryCustom;

    @Autowired
    private EmployerRepository employerRepository;

    @Autowired
    private EmployerMapper employerMapper;

    @Autowired
    private SalaryMapper salaryMapper;

    @Autowired
    private SalaryRepository salaryRepository;


    public Employer getEmployerById(EmployerRequest request) {
        EmployerEntity employerEntity = employerRepositoryCustom.findById(Long.valueOf(request.getRequestId()))
                .orElseThrow(Status.NOT_FOUND::asRuntimeException);
        return employerMapper.toEmployerMessage(employerEntity);
    }


    public List<Salary> getSalaryById(EmployerRequest request) {
        List<SalaryEntity> salaryEntities = employerRepositoryCustom.findSalaryById(Long.valueOf(request.getRequestId()), request.getMaxRow(), request.getPageIndex());
        return salaryMapper.toSalariesMessage(salaryEntities);
    }


    public List<EmployerNoSalaries> getEmployers(EmployersPagination request) {
        List<EmployerEntity> employerEntities = employerRepositoryCustom.findAll();
        return employerMapper.toEmployerNoSalariesMessage(employerEntities);
    }

    public List<EmployerNoSalaries> getEmployersByCriteria(EmployerSearchCriteria request) {
        EmployerEntity employerCriteria = employerMapper.toEmployerEntity(request);
        List<EmployerEntity> employerEntities = employerRepositoryCustom.findByCriteria(employerCriteria, request.getMaxRow(), request.getPageIndex());
        return employerMapper.toEmployerNoSalariesMessage(employerEntities);
//        employerNoSalaries.forEach(employer -> employerMapper.mappingIDEFormatForDisplay(employer));
    }


    public DeleteEmployerResponse deleteEmployerById(EmployerRequest request) {
        try {
            employerRepository.deleteById(Long.valueOf(request.getRequestId()));
            DeleteEmployerResponse response = DeleteEmployerResponse.newBuilder()
                    .setSuccess(true)
                    .setMessage("Employer deleted successfully")
                    .build();
            return response;
        } catch (Exception e) {
            DeleteEmployerResponse response = DeleteEmployerResponse.newBuilder()
                    .setSuccess(false)
                    .setMessage("Error deleting employer")
                    .build();
            return response;
        }
    }


    public Employer addEmployer(Employer request) {
        EmployerEntity employerEntity = employerMapper.toEmployerEntity(request);
        Utils.validateEmployerEntity(employerEntity);
        List<SalaryEntity> salaryEntities = employerEntity.getSalaries();
        if (!salaryEntities.isEmpty()) {
            IntStream.range(0, salaryEntities.size())
                    .forEach(index -> {
                        validateUniqueness(salaryEntities.get(index), salaryEntities, index, employerEntity.getDateAffiliation(), employerEntity.getDateRadiation());
                    });
            List<String> numberAVSList = salaryEntities.stream().map(SalaryEntity::getNumberAVS).collect(Collectors.toList());
            Map<String, List<SalaryEntity>> mapSalariesDatabase = employerRepositoryCustom.findSalariesByNumberAVSListHashMap(numberAVSList);
            if (!mapSalariesDatabase.isEmpty()) {
                salaryEntities.forEach(salary -> validateUniqueness(salary, mapSalariesDatabase, employerEntity));
            }
        }
        EmployerEntity savedEmployerEntity = employerRepository.save(employerEntity);
        return employerMapper.toEmployerMessage(savedEmployerEntity);
    }


    public Employer updateEmployer(Employer request) {
        EmployerEntity employerEntity = employerRepositoryCustom.findById(Long.valueOf(request.getId()))
                .orElseThrow(Status.NOT_FOUND::asRuntimeException);
        EmployerEntity employerEntity1 = employerMapper.toEmployerEntity(request);
        Utils.validateEmployerEntity(employerEntity1);
        List<SalaryEntity> salaryEntities = employerEntity1.getSalaries();
        if (!salaryEntities.isEmpty()) {
            IntStream.range(0, salaryEntities.size())
                    .forEach(index -> {
                        validateUniqueness(salaryEntities.get(index), salaryEntities, index, employerEntity1.getDateAffiliation(), employerEntity1.getDateRadiation());
                    });
            List<String> numberAVSList = salaryEntities.stream().map(SalaryEntity::getNumberAVS).collect(Collectors.toList());
            Map<String, List<SalaryEntity>> mapSalariesDatabase = employerRepositoryCustom.findSalariesByNumberAVSListHashMap(numberAVSList);
            if (!mapSalariesDatabase.isEmpty()) {
                salaryEntities.forEach(salary -> validateUniqueness(salary, mapSalariesDatabase, employerEntity1));
            }
        }
        else {
            List<Tuple> salariesNumberDateStartAndDateFinished = employerRepositoryCustom.findSalaryById(employerEntity.getId());
            salariesNumberDateStartAndDateFinished.forEach(tuple -> {
                String numberAVS = tuple.get(0, String.class);
                LocalDate dateDebut = tuple.get(1, LocalDate.class);
                LocalDate dateFinished = tuple.get(2, LocalDate.class);
                if (dateDebut != null && dateDebut.isBefore(employerEntity1.getDateAffiliation())) {
                    throw new StatusRuntimeException(Status.ABORTED);
                }
                Optional.ofNullable(employerEntity1.getDateRadiation()).ifPresent(value -> {
                    if (dateFinished != null && dateFinished.isAfter(value)){
                        throw new StatusRuntimeException(Status.ABORTED);
                    }
                });
            });
        }
        employerEntity.setType(Type.getDatabaseType(request.getType()));
        employerEntity.setName(request.getName());
        employerEntity.setNumberIDE(request.getNumberIDE());
        Optional.of(Utils.toLocalDate(request.getDateAffiliation())).ifPresent(localDate -> employerEntity.setDateAffiliation(localDate.orElse(null)));
        Optional.of(Utils.toLocalDate(request.getDateRadiation())).ifPresent(localDate -> employerEntity.setDateRadiation(localDate.orElse(null)));
        Optional.of(request.getSalariesList()).ifPresent(salaries -> {
            employerEntity.addSalaries(salaryMapper.toSalaryEntity(salaries));
        });

        EmployerEntity savedEmployerEntity = employerRepository.save(employerEntity);
        return employerMapper.toEmployerMessage(savedEmployerEntity);
    }


    public LastEmployerNumber getLastEmployerNumber(LastEmployerNumber request) {
        String clonedNumber = employerRepositoryCustom.findLastEmployerNumber(request.getNumber());
        LastEmployerNumber lastEmployerNumber = LastEmployerNumber.newBuilder().setNumber(clonedNumber).build();
        return lastEmployerNumber;
    }

    public Count getEmployersByCriteriaCount(EmployerSearchCriteria request) {
        EmployerEntity employerCriteria = employerMapper.toEmployerEntity(request);
        Long count = employerRepositoryCustom.findByCriteriaCount(employerCriteria);
        return Count.newBuilder().setNumber(count).build();
    }

    public Count getEmployersSalariesCount(EmployerRequest request) {
        Long count = employerRepositoryCustom.findByIdSalariesCount(Long.valueOf(request.getRequestId()));
        return Count.newBuilder().setNumber(count).build();
    }

    private void validateUniqueness(SalaryEntity salaryEntity) {// Inject the repository or obtain it through another means
        if (salaryEntity.getDateDebut() != null && salaryEntity.getDateFinished() != null &&
                !(salaryEntity.getDateDebut().getYear() == salaryEntity.getDateFinished().getYear())) {
            throw new IllegalArgumentException("Start and finish dates must be in the same year.");
        }

        List<SalaryEntity> conflictingRecords = employerRepositoryCustom.findByNumeroAVSAndDateDebutNotAndDateFinishedNot(
                salaryEntity.getNumberAVS(), salaryEntity.getDateDebut(), salaryEntity.getDateFinished());
        if (!conflictingRecords.isEmpty()) {
            throw new IllegalArgumentException("A record with the same NUMEROAVS already exists with different dates.");
        }
        // Additional validation logic if needed
    }

    private void validateUniqueness(SalaryEntity salaryEntity, List<SalaryEntity> salariesDatabase, int index, LocalDate dateAffiliation, LocalDate dateRadiation) {// Inject the repository or obtain it through another means
        if (salaryEntity.getDateDebut().isBefore(dateAffiliation)) {
            throw new StatusRuntimeException(Status.ABORTED);
        }
        Optional.ofNullable(dateRadiation).ifPresent(value -> {
            if (salaryEntity.getDateFinished().isAfter(value)) {
                throw new StatusRuntimeException(Status.ABORTED);
            }
        });
        if (!(salaryEntity.getDateDebut().getYear() == salaryEntity.getDateFinished().getYear())) {
            throw new StatusRuntimeException(Status.INVALID_ARGUMENT);
        }
        for (int i = 0; i < salariesDatabase.size(); i++) {
            SalaryEntity existingSalary = salariesDatabase.get(i);
            if (index == i) {
                continue;
            }
            if (salaryEntity.getNumberAVS().equals(existingSalary.getNumberAVS())) {
                if (isDateRangeOverlap(salaryEntity, existingSalary)) {
                    throw new StatusRuntimeException(Status.ALREADY_EXISTS);
                }
            }
        }
    }

    private void validateUniqueness(SalaryEntity salaryEntity, Map<String, List<SalaryEntity>> salariesDatabase, EmployerEntity entity) {// Inject the repository or obtain it through another means
        List<SalaryEntity> existingSalaries = salariesDatabase.get(salaryEntity.getNumberAVS());
        for (SalaryEntity existingSalary : existingSalaries) {
            if (salaryEntity.getNumberAVS().equals(existingSalary.getNumberAVS())) {
                if (isDateRangeOverlap(salaryEntity, existingSalary)) {
                    throw new StatusRuntimeException(Status.ALREADY_EXISTS);
                }
                else if (salaryEntity.getEmployer().getNumber().equals(entity.getNumber())) {
                    if (existingSalary.getDateDebut().isBefore(entity.getDateAffiliation())) {
                        throw new StatusRuntimeException(Status.ABORTED);
                    }
                    Optional.ofNullable(entity.getDateRadiation()).ifPresent(value -> {
                        if (existingSalary.getDateFinished().isAfter(value)){
                            throw new StatusRuntimeException(Status.ABORTED);
                        }
                    });
                }
            }
        }
    }

    private void validateDateInSameYear(SalaryEntity salaryEntity) {
        if (salaryEntity.getDateDebut() != null && salaryEntity.getDateFinished() != null &&
                !(salaryEntity.getDateDebut().getYear() == salaryEntity.getDateFinished().getYear())) {
            throw new StatusRuntimeException(Status.INVALID_ARGUMENT);
        }
    }

    private boolean isDateRangeOverlap(SalaryEntity salaryEntity1, SalaryEntity salaryEntity2) {
        LocalDate startDate1 = salaryEntity1.getDateDebut();
        LocalDate endDate1 = salaryEntity1.getDateFinished();
        LocalDate startDate2 = salaryEntity2.getDateDebut();
        LocalDate endDate2 = salaryEntity2.getDateFinished();
        // Check for date range overlap
        if (startDate2.isEqual(startDate1) || startDate2.isEqual(endDate1)) {
            return true;
        }
        if (endDate2.isEqual(startDate1) || endDate2.isEqual(endDate1)) {
            return true;
        }

        if (startDate2.isAfter(startDate1) && startDate2.isBefore(endDate1)) {
            return true;
        }
        if (endDate2.isAfter(startDate1) && endDate2.isBefore(endDate1)) {
            return true;
        }
        if (startDate1.isAfter(startDate2) && startDate1.isBefore(endDate2)) {
            return true;
        }
        if (endDate1.isAfter(startDate2) && endDate1.isBefore(endDate2)) {
            return true;
        }
        return false;
    }
}
