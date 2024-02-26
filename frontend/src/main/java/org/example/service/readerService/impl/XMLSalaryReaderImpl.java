package org.example.service.readerService.impl;

import org.example.Salary;
import org.example.mapper.FileMapperFactory;
import org.example.service.readerService.SalaryReaderInterface;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class XMLSalaryReaderImpl implements SalaryReaderInterface {
    @Override
    public List<Salary> readFile(String fileDirectory, Locale locale) {
        try {
            File xmlFile = new File(fileDirectory);
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFile);
            document.getDocumentElement().normalize();

            List<String> columnNames = new ArrayList<>();

            NodeList rowList = document.getElementsByTagName("Row");
            return IntStream.range(1, rowList.getLength())
                    .mapToObj(i -> (Element) rowList.item(i))
                    .map(line -> FileMapperFactory.getMapper(fileDirectory).apply(line, columnNames, locale))
                    .collect(Collectors.toList());
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Salary> readFile(String fileDirectory, int rowsPerPage, int pageIndex, Locale locale) throws IOException {
        try {
            File xmlFile = new File(fileDirectory);
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFile);
            document.getDocumentElement().normalize();

            List<String> columnNames = new ArrayList<>();

            NodeList rowList = document.getElementsByTagName("Row");

            int startRowIndex = pageIndex * rowsPerPage + 1;
            int endRowIndex = Math.min(startRowIndex + rowsPerPage, rowList.getLength());

            if (startRowIndex >= rowList.getLength()) {
                return Collections.emptyList(); // No more rows to read
            }
            return IntStream.range(startRowIndex, endRowIndex)
                    .mapToObj(i -> (Element) rowList.item(i))
                    .map(line -> FileMapperFactory.getMapper(fileDirectory).apply(line, columnNames, locale))
                    .collect(Collectors.toList());
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Long readFileLinesIgnoreHeader(String filePath) throws IOException {
        try {
            File xmlFile = new File(filePath);
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlFile);
            document.getDocumentElement().normalize();

            NodeList rowList = document.getElementsByTagName("Row");
            // Skip the header row and count the remaining rows
            return IntStream.range(1, rowList.getLength())
                    .count();
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new IOException("Unexpected error found: " + e);
        }
    }

}
