package org.example.service.readerService;

import org.example.Salary;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.function.BiPredicate;

public interface SalaryReaderInterface {
    BiPredicate<String, String> equalString = String::equals;
    List<Salary> readFile(String fileDirectory, Locale locale) throws IOException;
    List<Salary> readFile(String fileDirectory, int rowsPerPage, int pageIndex, Locale locale) throws IOException;
    Long readFileLinesIgnoreHeader(String filePath) throws IOException;
}
