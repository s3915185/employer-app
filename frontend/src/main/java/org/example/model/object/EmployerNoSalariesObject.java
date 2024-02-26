package org.example.model.object;

import lombok.*;
import org.example.EmployerNoSalaries;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployerNoSalariesObject {
    private List<EmployerNoSalaries> employerNoSalaries;
}
