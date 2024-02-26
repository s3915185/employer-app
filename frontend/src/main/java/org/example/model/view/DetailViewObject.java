package org.example.model.view;

import lombok.*;
import org.example.EmployerNoSalaries;
import org.example.Salary;
import org.example.model.pagination.PaginationObject;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetailViewObject {
    private EmployerNoSalaries employerNoSalaries;
    private List<Salary> salaries;
    private PaginationObject paginationObject;
}
