package org.example.mapper;

import com.sun.org.apache.xerces.internal.util.Status;
import org.example.model.enums.FileIndicatorType;
import org.example.util.Utils;

public class FileMapperFactory {
    public static <T> FileMapper<T> getMapper(String fileDirectory) throws RuntimeException {
        try {
            return FileIndicatorType.getMapperByExtension(Utils.getFileExtension(fileDirectory));
        } catch (Exception e) {
            throw new RuntimeException(String.valueOf(Status.NOT_SUPPORTED));
        }
    }
}
