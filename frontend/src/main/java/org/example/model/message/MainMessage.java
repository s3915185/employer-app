package org.example.model.message;

import lombok.*;
import org.example.model.enums.MessageView;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MainMessage {
    private String message;
    private MessageView view;
}
