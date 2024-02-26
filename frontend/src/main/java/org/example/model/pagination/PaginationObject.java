package org.example.model.pagination;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaginationObject {
    private Number page;
    private Number pageLength;
    private String type;
}
