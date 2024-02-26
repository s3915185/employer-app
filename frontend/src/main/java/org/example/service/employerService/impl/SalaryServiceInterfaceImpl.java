package org.example.service.employerService.impl;

import org.example.EmployerServiceGrpc;
import org.example.Salary;
import org.example.service.readerService.SalaryReaderInterface;
import org.example.service.employerService.SalaryServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class SalaryServiceInterfaceImpl implements SalaryServiceInterface {

    private final SalaryReaderInterface salaryReaderInterface;
    @Autowired
    EmployerServiceGrpc.EmployerServiceBlockingStub employerServiceBlockingStub;

    public SalaryServiceInterfaceImpl(SalaryReaderInterface salaryReaderInterface) {
        this.salaryReaderInterface = salaryReaderInterface;
    }
    @Override
    public List<Salary> readFile(String fileDirectory, Locale locale) throws IOException {
        return salaryReaderInterface.readFile(fileDirectory, locale);
    }

    @Override
    public List<Salary> readFile(String fileDirectory, int row, int pageIndex, Locale locale) throws IOException {
        return salaryReaderInterface.readFile(fileDirectory, row, pageIndex, locale);
    }

    @Override
    public Long readFileLinesIgnoreHeader(String filePath) throws IOException {
        return salaryReaderInterface.readFileLinesIgnoreHeader(filePath);
    }

}
