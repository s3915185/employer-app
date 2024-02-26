package org.example.service.readerService.impl;

import org.example.Salary;
import org.example.exception.FileNotIdentifiableException;
import org.example.mapper.FileMapperFactory;
import org.example.service.readerService.SalaryReaderInterface;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CSVSalaryReaderImpl implements SalaryReaderInterface {
    public List<Salary> readFile(String fileDirectory, Locale locale) throws IOException, ArrayIndexOutOfBoundsException {
        List<String> allLines;
        try (Stream<String> lines = Files.lines(Paths.get(fileDirectory))) {
            allLines = lines.collect(Collectors.toList());

            if (allLines.isEmpty()) {
                return Collections.emptyList();
            }

            List<String> columnNames = Arrays.stream(allLines.get(0).split(","))
                    .map(String::toLowerCase)
                    .collect(Collectors.toList());

            return allLines.stream()
                    .skip(1)
                    .map(line -> FileMapperFactory.getMapper(fileDirectory).apply(line, columnNames, locale))
                    .collect(Collectors.toList());
        }
        catch (FileNotFoundException e) {
            throw new FileNotIdentifiableException("Unable to read this file: " + Paths.get(fileDirectory));
        } catch (IOException e) {
            throw new IOException("Unexpected error found: " + e);
        }
    }

    public List<Salary> readFile(String fileDirectory, int rowsPerPage, int pageIndex, Locale locale) throws IOException {
        List<String> allLines;
        try (Stream<String> lines = Files.lines(Paths.get(fileDirectory))) {
            allLines = lines.collect(Collectors.toList());

            if (allLines.isEmpty()) {
                return Collections.emptyList();
            }

            List<String> columnNames = Arrays.stream(allLines.get(0).split(","))
                    .map(String::toLowerCase)
                    .collect(Collectors.toList());

            int startIndex = pageIndex * rowsPerPage + 1;
            int endIndex = Math.min(startIndex + rowsPerPage, allLines.size());

            if (startIndex >= allLines.size()) {
                return Collections.emptyList(); // No more rows to read
            }

            return allLines.subList(startIndex, endIndex).stream()
                    .map(line -> FileMapperFactory.getMapper(fileDirectory).apply(line, columnNames, locale))
                    .collect(Collectors.toList());
        }
        catch (FileNotFoundException e) {
            throw new FileNotIdentifiableException("Unable to read this file: " + Paths.get(fileDirectory));
        } catch (IOException e) {
            throw new IOException("Unexpected error found: " + e);
        }
    }

    @Override
    public Long readFileLinesIgnoreHeader(String filePath) throws IOException {
        try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
            return lines.skip(1).count();
        } catch (IOException e) {
            throw new IOException("Unexpected error found: " + e);
        }
    }
}
