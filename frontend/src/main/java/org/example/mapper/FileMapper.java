package org.example.mapper;

import org.example.Salary;

import java.util.List;
import java.util.Locale;


/*
    MAPPER INTERFACE: RESPONSIBLE FOR CSV AND XLSX TO IMPLEMENT FROM
 */
public interface FileMapper<T> {
    Salary apply(T input, List<String> columnNames, Locale locale);
}
