package ca.sheridancollege.Assignement3.beans;

import lombok.*;

@Data
@AllArgsConstructor
public class ErrorMessage {
    
    private final String STATUS = "error";

    private String message;

}
