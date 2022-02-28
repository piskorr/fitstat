package pl.polsl.fitstat.dtos;

import pl.polsl.fitstat.enums.RoleEnum;
import pl.polsl.fitstat.models.UserEntity;

import javax.validation.constraints.*;
import java.time.LocalDate;

public class UserDTO {

    private Long id;

    @NotEmpty
    @Size(min = 6, message = "username should have at least 6 characters")
    @Size(max = 15, message = "username cannot be longer than 15 characters")
    private String username;

    @Size(min = 6, message = "username should have at least 6 characters")
    @Size(max = 30, message = "username cannot be longer than 30 characters")
    private String password;

    @NotEmpty(message = "role cannot be empty")
    private String role;

    @NotEmpty(message = "email cannot be empty")
    @Email(message = "email has wrong format")
    private String email;

    private String firstName;

    private String lastName;

    private Boolean isDeleted;

    public UserDTO() {
    }

    public UserDTO(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public UserDTO(UserEntity userEntity) {
        this.id = userEntity.getId();
        this.username = userEntity.getUsername();
        this.role = userEntity.getRole().getRole();
        this.email = userEntity.getEmail();
        this.firstName = userEntity.getFirstName();
        this.lastName = userEntity.getLastName();
        this.isDeleted = userEntity.isDeleted();
    }

    public UserDTO(String username, String email, String firstName, String lastName) {
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = RoleEnum.USER.toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}

