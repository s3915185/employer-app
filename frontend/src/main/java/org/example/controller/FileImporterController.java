package org.example.controller;

import org.example.Salary;
import org.example.service.employerService.SalaryServiceInterface;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class FileImporterController {

    private final SalaryServiceInterface salaryServiceInterface;

    public FileImporterController(SalaryServiceInterface salaryServiceInterface) {     // CONSTRUCTOR
        this.salaryServiceInterface = salaryServiceInterface;
    }

    public List<Salary> readFile(String filePath, Locale locale) throws IOException {
        return salaryServiceInterface.readFile(filePath, locale);
    }

    public List<Salary> readFile(String filePath, int row, int pageIndex, Locale locale) throws IOException {
        return salaryServiceInterface.readFile(filePath, row, pageIndex, locale);
    }

    public Long readFileLinesIgnoreHeader(String filePath) throws IOException {
        return salaryServiceInterface.readFileLinesIgnoreHeader(filePath);
    }
}
