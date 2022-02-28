package pl.polsl.fitstat.models;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "roles")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Short id;

    @Column(name = "role")
    private String role;

    @Fetch(FetchMode.JOIN)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "role")
    Set<UserEntity> usersRoles;

    public RoleEntity(String role) {
        this.role = role;
    }

    public RoleEntity() {
    }

    public String getRole() {
        return role;
    }

    public Short getId() {
        return id;
    }

}
