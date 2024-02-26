package org.example.model.view;

import lombok.*;
import org.example.EmployerNoSalaries;
import org.example.EmployerSearchCriteria;
import org.example.model.pagination.PaginationObject;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchViewObject {
    private EmployerSearchCriteria employerSearchCriteria;
    private List<EmployerNoSalaries> employerNoSalariesList;
    private PaginationObject paginationObject;
    private String method;
}
