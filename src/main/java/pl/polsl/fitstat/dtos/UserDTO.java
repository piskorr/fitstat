package pl.polsl.fitstat.dtos;

import pl.polsl.fitstat.models.UserEntity;

import java.time.LocalDate;

public class UserDTO {

    private Long id;
    private String username;
    private String password;
    private String role;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate dob;
    private String sex;
    private Float weight;
    private Float height;

    public UserDTO() {
    }

    public UserDTO(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public UserDTO(UserEntity userEntity) {//todo null check
        this.username = userEntity.getUsername();
        this.password = userEntity.getPassword();
//        this.role = userEntity.getRole();
        this.email = userEntity.getEmail();
        this.firstName = userEntity.getFirstName();
        this.lastName = userEntity.getLastName();
    }

    public Long getId() {
        return id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getEmail() {
        return email;
    }

    public String getLastName() {
        return lastName;
    }

}
