package org.example.model.enums;


import com.sun.org.apache.xerces.internal.util.Status;
import org.example.mapper.FileMapper;
import org.example.mapper.impl.CSVMapperImpl;
import org.example.mapper.impl.XLSXMapperImpl;
import org.example.mapper.impl.XMLMapperImpl;
import org.example.service.readerService.SalaryReaderInterface;
import org.example.service.readerService.impl.CSVSalaryReaderImpl;
import org.example.service.readerService.impl.XLSXSalaryReaderImpl;
import org.example.service.readerService.impl.XMLSalaryReaderImpl;

/*
    FILE PROCESSOR TYPE: THIS IS ENUM FOR THE FILE PROCESSOR IN SELECTING CORRECT FILE PROCESSOR TYPE
 */
public enum FileIndicatorType {
    CSV(".csv", new CSVSalaryReaderImpl(), new CSVMapperImpl()),
    XLSX(".xlsx", new XLSXSalaryReaderImpl(), new XLSXMapperImpl()),
    XML(".xml", new XMLSalaryReaderImpl(), new XMLMapperImpl());
    private final String extension;
    private final SalaryReaderInterface salaryReaderInterface;
    private final FileMapper mapper;

    FileIndicatorType(String extension, SalaryReaderInterface salaryReaderInterface, FileMapper mapper) {
        this.extension = extension;
        this.salaryReaderInterface = salaryReaderInterface;
        this.mapper = mapper;
    }
    public String getExtension() {
        return extension;
    }
    public SalaryReaderInterface getSalaryReaderInterface() {
        return salaryReaderInterface;
    }

    public FileMapper getMapper() {
        return mapper;
    }

    public static SalaryReaderInterface getByExtension(String extension) throws RuntimeException {
            for (FileIndicatorType fileIndicatorType : values()) {
                if (fileIndicatorType.getExtension().equals(extension)) {
                    return fileIndicatorType.getSalaryReaderInterface();
                }
            }
            throw new RuntimeException(String.valueOf(Status.NOT_SUPPORTED));
    }

    public static FileMapper getMapperByExtension(String extension) throws RuntimeException {
        for (FileIndicatorType mapperType : values()) {
            if (mapperType.getExtension().equals(extension)) {
                return mapperType.getMapper();
            }
        }
        throw new RuntimeException(String.valueOf(Status.NOT_SUPPORTED));
    }
}
