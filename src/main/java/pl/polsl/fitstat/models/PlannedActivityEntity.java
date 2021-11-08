package pl.polsl.fitstat.models;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "planned_activities")
public class PlannedActivityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "activity_date")
    private LocalDate activityDate;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "activity_id")
    private ActivityEntity activityEntity;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
