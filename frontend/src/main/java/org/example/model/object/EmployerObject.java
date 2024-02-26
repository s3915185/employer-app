package org.example.model.object;

import lombok.*;
import org.example.Employer;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployerObject {
    private Employer employer;
    private String method;
}
