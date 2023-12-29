package ca.sheridancollege.Assignement3.beans;

import lombok.*;
import java.util.*;

@Data   
public class Book {

    private Long id;
    private String title;
    private String author;

    private List<Review> reviews;
    
}
