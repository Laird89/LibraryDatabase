package ca.sheridancollege.Assignement3.beans;

import lombok.Data;

@Data
public class SiteUser {

    private Long id;
    private String userName;
    private String userPassword;
    private String userRole;
    private final String[] userRoles = {"ADMIN","USER"};
    
}
