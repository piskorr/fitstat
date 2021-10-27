package pl.polsl.fitstat.models;

import pl.polsl.fitstat.dtos.UserDTO;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "user_name")
    private String username;

    @Column(name = "user_password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "height")
    private Float height;

    @Column(name = "weight")
    private Float weight;

    @Column(name = "dob")
    private LocalDate dob;

    @Column(name = "sex")
    private boolean sex;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "role")
    private RoleEntity role;

    public UserEntity() {
    }

    public UserEntity(Long id, String email, String username, String password, String firstName, String lastName, Float height, Float weight, LocalDate dob, boolean sex) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.height = height;
        this.weight = weight;
        this.dob = dob;
        this.sex = sex;
        this.isDeleted = false;
    }

    public UserEntity(String email, String username, String password, String firstName, String lastName, Float height, Float weight, LocalDate dob, boolean sex) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.height = height;
        this.weight = weight;
        this.dob = dob;
        this.sex = sex;
        this.isDeleted = false;
    }

    public UserEntity(UserDTO userDTO) {

    }

    public LocalDate getDob() {
        return dob;
    }

    public String getSex() {
        return sex ? "female" : "male";
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
