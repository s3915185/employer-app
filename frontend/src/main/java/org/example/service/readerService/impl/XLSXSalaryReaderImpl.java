package org.example.service.readerService.impl;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.Salary;
import org.example.exception.FileNotIdentifiableException;
import org.example.mapper.FileMapperFactory;
import org.example.service.readerService.SalaryReaderInterface;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class XLSXSalaryReaderImpl implements SalaryReaderInterface {
    @Override
    public List<Salary> readFile(String fileDirectory, Locale locale) throws IOException, ArrayIndexOutOfBoundsException {
        File file = new File(fileDirectory);
        try (FileInputStream fileInputStream = new FileInputStream(file.getAbsolutePath());
             XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);) {

            List<String> columnNames = new ArrayList<>();
            Sheet sheet = workbook.getSheetAt(0);
            // Convert the rows iterator to a Stream
            return StreamSupport.stream(sheet.spliterator(), false)
                    .skip(1)
                    .map(line -> FileMapperFactory.getMapper(fileDirectory).apply(line, columnNames, locale))
                    .collect(Collectors.toList());
        } catch (FileNotFoundException e) {
            throw new FileNotIdentifiableException("Unable to read this file: " + Paths.get(fileDirectory));
        } catch (IOException e) {
            throw new IOException("Unexpected error found: " + e);
        }
    }

    @Override
    public List<Salary> readFile(String fileDirectory, int rowsPerPage, int pageIndex, Locale locale) throws IOException {
        File file = new File(fileDirectory);
        try (FileInputStream fileInputStream = new FileInputStream(file.getAbsolutePath());
             XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);) {

            List<String> columnNames = new ArrayList<>();
            Sheet sheet = workbook.getSheetAt(0);

            int startRowIndex = pageIndex * rowsPerPage + 1;
            int endRowIndex = Math.min(startRowIndex + rowsPerPage, sheet.getPhysicalNumberOfRows());

            if (startRowIndex >= sheet.getPhysicalNumberOfRows()) {
                return Collections.emptyList(); // No more rows to read
            }
            // Convert the rows iterator to a Stream
            return StreamSupport.stream(sheet.spliterator(), false)
                    .skip(startRowIndex)
                    .limit(endRowIndex - startRowIndex)
                    .map(line -> FileMapperFactory.getMapper(fileDirectory).apply(line, columnNames, locale))
                    .collect(Collectors.toList());
        } catch (FileNotFoundException e) {
            throw new FileNotIdentifiableException("Unable to read this file: " + Paths.get(fileDirectory));
        } catch (IOException e) {
            throw new IOException("Unexpected error found: " + e);
        }
    }

    @Override
    public Long readFileLinesIgnoreHeader(String filePath) throws IOException {
        File file = new File(filePath);
        try (FileInputStream fileInputStream = new FileInputStream(file);
             XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream)) {

            Sheet sheet = workbook.getSheetAt(0);
            // Skip the header row and count the remaining rows
            return StreamSupport.stream(sheet.spliterator(), false)
                    .skip(1)
                    .count();
        } catch (FileNotFoundException e) {
            throw new FileNotIdentifiableException("Unable to read this file: " + Paths.get(filePath));
        } catch (IOException e) {
            throw new IOException("Unexpected error found: " + e);
        }
    }
}
