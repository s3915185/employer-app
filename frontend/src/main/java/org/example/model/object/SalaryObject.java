package org.example.model.object;

import lombok.*;
import org.example.Salary;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalaryObject {
    private List<Salary> salaryList;
    private String type;
}
