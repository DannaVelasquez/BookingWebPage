package com.digitalhouse.digitalexpirience.model.expirience;

import com.digitalhouse.digitalexpirience.model.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Bookings")
@SQLDelete(sql = "UPDATE Bookings SET DELETED = true WHERE id=?")
@Where(clause = "DELETED=false")
public class Booking implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "people")
    private int people;

    @Column(name = "amounts")
    private Double amount;

    @Column(name = "registration_dates")
    private LocalDateTime registrationDate;

    @Column(name = "start_dates")
    private String startDate;

    @Column(name = "end_dates")
    private String endDate;

    @Column(name = "comments")
    private String comment;

    @Column(name = "deleted")
    protected boolean deleted = Boolean.FALSE;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "experience_id")
    private Experience experience;


    public void deleted() {
        this.setDeleted(Boolean.TRUE);
    }
}