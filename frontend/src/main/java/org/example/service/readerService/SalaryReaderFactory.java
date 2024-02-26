package org.example.service.readerService;

import org.example.model.enums.FileIndicatorType;
import org.example.util.Utils;

public class SalaryReaderFactory {
    public static SalaryReaderInterface getSalaryReaderInterface(String filePath) {
        return FileIndicatorType.getByExtension(Utils.getFileExtension(filePath));
    }
}
