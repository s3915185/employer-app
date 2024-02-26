package org.example.setup;

import org.example.controller.FileImporterController;
import org.example.service.readerService.SalaryReaderFactory;
import org.example.service.readerService.SalaryReaderInterface;
import org.example.service.employerService.SalaryServiceInterface;
import org.example.service.employerService.impl.SalaryServiceInterfaceImpl;

public class FileImporterSetup {

    public static FileImporterController setupModel(String filePath) {
        SalaryReaderInterface salaryReaderInterface = SalaryReaderFactory.getSalaryReaderInterface(filePath);
        SalaryServiceInterface salaryServiceInterface = new SalaryServiceInterfaceImpl(salaryReaderInterface);
        return new FileImporterController(salaryServiceInterface);
    }
}
