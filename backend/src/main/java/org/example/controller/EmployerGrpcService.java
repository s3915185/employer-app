package org.example.controller;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.example.*;
import org.example.service.EmployerGrpcServiceInterface;
import org.example.service.impl.EmployerGrpcServiceInterfaceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.logging.Logger;

@GrpcService
public class EmployerGrpcService extends EmployerServiceGrpc.EmployerServiceImplBase {
    private final Logger log = Logger.getLogger(EmployerGrpcService.class.getName());

//    @Autowired
//    private EmployerGrpcServiceInterfaceImpl employerGrpcServiceInterface;
    @Autowired
    private EmployerGrpcServiceInterface employerGrpcServiceInterface;

    @Override
    public void getEmployerById(EmployerRequest request, StreamObserver<Employer> responseObserver) {
        Employer employer = employerGrpcServiceInterface.getEmployerById(request);
        responseObserver.onNext(employer);
        responseObserver.onCompleted();
    }

    @Override
    public void getSalaryById(EmployerRequest request, StreamObserver<Salary> responseObserver) {
        List<Salary> salaries = employerGrpcServiceInterface.getSalaryById(request);
        salaries.forEach(responseObserver::onNext);
        responseObserver.onCompleted();
    }

    @Override
    public void getEmployers(EmployersPagination request, StreamObserver<EmployerNoSalaries> responseObserver) {
        List<EmployerNoSalaries> employers = employerGrpcServiceInterface.getEmployers(request);
        employers.forEach(responseObserver::onNext);
        responseObserver.onCompleted();
    }
    @Override
    public void getEmployersByCriteria(EmployerSearchCriteria request, StreamObserver<EmployerNoSalaries> responseObserver) {
        List<EmployerNoSalaries> employers = employerGrpcServiceInterface.getEmployersByCriteria(request);
        employers.forEach(responseObserver::onNext);
        responseObserver.onCompleted();
    }

    @Override
    public void deleteEmployerById(EmployerRequest request, StreamObserver<DeleteEmployerResponse> responseObserver) {
        DeleteEmployerResponse response = employerGrpcServiceInterface.deleteEmployerById(request);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void addEmployer(Employer request, StreamObserver<Employer> responseObserver) {
        Employer savedEmployer = employerGrpcServiceInterface.addEmployer(request);
        responseObserver.onNext(savedEmployer);
        responseObserver.onCompleted();
    }

    @Override
    public void updateEmployer(Employer request, StreamObserver<Employer> responseObserver) {
        Employer savedEmployer = employerGrpcServiceInterface.updateEmployer(request);
        responseObserver.onNext(savedEmployer);
        responseObserver.onCompleted();
    }


    @Override
    public void getLastEmployerNumber(LastEmployerNumber request, StreamObserver<LastEmployerNumber> responseObserver) {
        LastEmployerNumber lastEmployerNumber = employerGrpcServiceInterface.getLastEmployerNumber(request);
        responseObserver.onNext(lastEmployerNumber);
        responseObserver.onCompleted();
    }
    @Override
    public void getEmployersByCriteriaCount(EmployerSearchCriteria request, StreamObserver<Count> responseObserver) {
        Count count = employerGrpcServiceInterface.getEmployersByCriteriaCount(request);
        responseObserver.onNext(count);
        responseObserver.onCompleted();
    }
    @Override
    public void getEmployersSalariesCount(EmployerRequest request, StreamObserver<Count> responseObserver) {
        Count count = employerGrpcServiceInterface.getEmployersSalariesCount(request);
        responseObserver.onNext(count);
        responseObserver.onCompleted();
    }
}
