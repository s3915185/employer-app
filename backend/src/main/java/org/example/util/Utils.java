package org.example.util;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.example.model.Type;
import org.example.model.entity.EmployerEntity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;

public class Utils {

    public static void validateEmployerEntity(EmployerEntity employerEntity) {
        if (employerEntity.getType().isEmpty()) {
            throw new StatusRuntimeException(Status.ABORTED);
        }
        if (!employerEntity.getNumber().matches("\\d+")) {
//            throw new RuntimeException("Number must be a string of digits");
            throw new StatusRuntimeException(Status.ABORTED);
        }
        if (employerEntity.getName() == null || employerEntity.getName().isEmpty()) {
//            throw new RuntimeException("Name cannot be null or empty");
            throw new StatusRuntimeException(Status.ABORTED);
        }
        if (!employerEntity.getNumberIDE().matches("(CHE|ADM)-\\d{3}\\.\\d{3}\\.\\d{3}")) {
//            throw new RuntimeException("Invalid numberIDE format. Must be CHE/ADM-xxx.xxx.xxx");
            throw new StatusRuntimeException(Status.ABORTED);
        }
        LocalDate dateCreation = employerEntity.getDateAffiliation();
//        if (dateCreation.isAfter(LocalDate.now())) {
//            throw new StatusRuntimeException(Status.ABORTED);
//        }
        Optional.ofNullable(employerEntity.getDateRadiation()).ifPresent(dateExpiration -> {
            if (dateCreation.isAfter(dateExpiration) || dateCreation.isEqual(dateExpiration)) {
//            throw new RuntimeException("dateAffiliation must be before dateRadiation");
                throw new StatusRuntimeException(Status.ABORTED);
            }
        });
    }
    public static Optional<LocalDate> toLocalDate(String date) {
        if (date == null) {
            return Optional.empty();
        }
        if (date.equals("")) {
            return Optional.empty();
        }
        final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return Optional.of(LocalDate.parse(date, dtf));
    }

    public static Optional<LocalDate> toLocalDateFromFile(String date) {
        String dateSplit = date;
        if (date == null) {
            return Optional.empty();
        }
        if (date.contains("T")) {
            dateSplit = date.split("T")[0];

        }
        String[] dateParts = dateSplit.split("-");
        if (dateParts.length != 3) {
            return Optional.empty();
        }

        // Pad month and day with leading zeros if needed
        String paddedMonth = String.format("%02d", Integer.parseInt(dateParts[1]));
        String paddedDay = String.format("%02d", Integer.parseInt(dateParts[2]));

        // Reconstruct the date string with padded values
        String paddedDate = paddedMonth + "/" + paddedDay + "/" + dateParts[0];


        final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        return Optional.of(LocalDate.parse(paddedDate, dtf));
    }
}
