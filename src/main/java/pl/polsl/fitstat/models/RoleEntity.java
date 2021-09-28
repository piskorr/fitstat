package pl.polsl.fitstat.models;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "role")
    private String role;

    public RoleEntity(String role) {
        this.role = role;
    }

    public RoleEntity() {
    }

    public String getRole() {
        return role;
    }

    public Long getId() {
        return id;
    }

}
