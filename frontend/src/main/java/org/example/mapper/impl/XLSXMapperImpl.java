package org.example.mapper.impl;

import com.sun.org.apache.xerces.internal.util.Status;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.example.Salary;
import org.example.constant.Constant;
import org.example.mapper.FileMapper;
import org.example.util.Utils;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

/*
    XLSX MAPPER: THIS IS RESPONSIBLE FOR COLLECTING DATA FROM ROW AND STORE THEM INTO OBJECT COMPANY
 */
public class XLSXMapperImpl implements FileMapper<Row> {
    @Override
    public Salary apply(Row row, List<String> columnNames, Locale locale) {
        try {
            Cell cell0 = row.getCell(0, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
            Cell cell1 = row.getCell(1, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
            Cell cell2 = row.getCell(2, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
            Cell cell3 = row.getCell(3, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
            Cell cell4 = row.getCell(4, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
            Cell cell5 = row.getCell(5, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
            Cell cell6 = row.getCell(6, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
            Cell cell7 = row.getCell(7, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);


            if (cell0 == null || cell1 == null || cell2 == null || cell3 == null || cell4 == null || cell5 == null || cell6 == null || cell7 == null) {
                throw new ArrayIndexOutOfBoundsException(Status.NOT_ALLOWED.toString());
            }
            if (!Utils.isValidFormat(cell0.getStringCellValue())) {
                throw new RuntimeException(Status.NOT_RECOGNIZED.toString());
            }
            Salary.Builder salaryBuilder = Salary.newBuilder();
            salaryBuilder.setNumberAVS(cell0.getStringCellValue());
            salaryBuilder.setName(cell1.getStringCellValue());
            salaryBuilder.setFirstName(cell2.getStringCellValue());
            if (locale.equals(Locale.ENGLISH)) {
                salaryBuilder.setDateDebut(Utils.toLocalDate(cell3.getDateCellValue().toString(), "EEE MMM dd HH:mm:ss zzz yyyy").get().toString());
                salaryBuilder.setDateFinished(Utils.toLocalDate(cell4.getDateCellValue().toString(), "EEE MMM dd HH:mm:ss zzz yyyy").get().toString());
            }
            else {
                String dateDebut = cell3.getLocalDateTimeCellValue().toString();
                String dateFinished = cell4.getLocalDateTimeCellValue().toString();
                Utils.validateDateFormat(dateDebut, "yyyy-MM-dd'T'HH:mm");
                Utils.validateDateFormat(dateFinished, "yyyy-MM-dd'T'HH:mm");
                salaryBuilder.setDateDebut(dateDebut);
                salaryBuilder.setDateFinished(dateFinished);
            }
            try {
                salaryBuilder.setNumberAvsAiApg((float) cell5.getNumericCellValue() );
                salaryBuilder.setNumberAc((float) cell6.getNumericCellValue());
                salaryBuilder.setNumberAf((float) cell7.getNumericCellValue());
            } catch (Exception e) {
                throw new RuntimeException(Constant.MESSAGE_NUMBER_FORMAT_ERROR);
            }
            return salaryBuilder.build();
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ArrayIndexOutOfBoundsException("Accidentally access out of index: " + e);
        }
    }
}
