package org.example.mapper.impl;

import com.sun.org.apache.xerces.internal.util.Status;
import org.example.Salary;
import org.example.constant.Constant;
import org.example.mapper.FileMapper;
import org.example.util.Utils;

import java.lang.reflect.Method;
import java.util.*;

/*
    CSV MAPPER: THIS IS RESPONSIBLE FOR SELECTING DATA BY EACH LINE AND PUT THEM INTO OBJECT COMPANY
 */
public class CSVMapperImpl implements FileMapper<String> {
    @Override
    public Salary apply(String line, List<String> columnNames, Locale locale) {
        try {
            List<String> fields = Arrays.asList(line.split(","));
            try {
                if (fields.get(0).isEmpty() || fields.get(1).isEmpty() || fields.get(2).isEmpty() ||
                        fields.get(3).isEmpty() || fields.get(4).isEmpty() || fields.get(5).isEmpty() ||
                        fields.get(6).isEmpty() || fields.get(7).isEmpty()) {
                    throw new RuntimeException(Status.NOT_ALLOWED.toString());
                }
            } catch (Exception e) {
                throw new RuntimeException(Status.NOT_ALLOWED.toString());
            }
            if (!Utils.isValidFormat(fields.get(0))) {
                throw new RuntimeException(Status.NOT_RECOGNIZED.toString());
            }
            Salary.Builder salaryBuilder = Salary.newBuilder();
            salaryBuilder.setNumberAVS(fields.get(0));
            salaryBuilder.setName(fields.get(1));
            salaryBuilder.setFirstName(fields.get(2));
            salaryBuilder.setDateDebut(Utils.formatDateString(fields.get(3)));
            salaryBuilder.setDateFinished(Utils.formatDateString(fields.get(4)));
            salaryBuilder.setNumberAvsAiApg(Float.parseFloat(fields.get(5)));
            salaryBuilder.setNumberAc(Float.parseFloat(fields.get(6)));
            salaryBuilder.setNumberAf(Float.parseFloat(fields.get(7)));
            return salaryBuilder.build();
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ArrayIndexOutOfBoundsException("Accidentally access out of index: " + e);
        } catch (NumberFormatException e) {
            throw new RuntimeException(Constant.MESSAGE_NUMBER_FORMAT_ERROR);
        }
    }
}
