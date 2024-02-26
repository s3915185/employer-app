package org.example.service.employerService;

import org.example.Salary;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public interface SalaryServiceInterface {
    List<Salary> readFile(String fileDirectory, Locale locale) throws IOException;
    List<Salary> readFile(String fileDirectory, int row, int pageIndex, Locale locale) throws IOException;
    Long readFileLinesIgnoreHeader(String filePath) throws IOException;
}
