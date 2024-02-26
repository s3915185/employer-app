package org.example.model.message;

import lombok.*;
import org.example.model.view.SearchViewObject;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomSearchViewObject {
    private SearchViewObject searchViewObject;
    private String method;
}
