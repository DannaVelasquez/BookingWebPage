package com.digitalhouse.digitalexpirience.model.user;

import com.digitalhouse.digitalexpirience.model.enums.UserStatus;
import com.digitalhouse.digitalexpirience.model.expirience.Booking;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "users")
@SQLDelete(sql = "UPDATE users SET deleted = true WHERE id=?")
@Where(clause = "DELETED=false")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "user_names")
    protected String username;

    @Column(name = "nick_names")
    protected String nickname;

    @Column(name = "passwords")
    protected String password;

    @Column(name = "first_names")
    protected String firstname;

    @Column(name = "last_names")
    protected String lastname;

    @Column(name = "registration_dates")
    protected LocalDateTime registrationDate;

    @Column(name = "birth_dates")
    protected String birthDate;

    @Column(name = "documents")
    protected String document;

    @Column(name = "cells")
    protected String cell;

    @Column(name = "activates")
    protected Boolean activate;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "USERS_ROLES", joinColumns = {@JoinColumn(name = "USER_ID" )}, inverseJoinColumns = {
            @JoinColumn(name = "ROLE_ID")})
    private Set<Role> roles;

    @Column(name = "deleted")
    protected boolean deleted = Boolean.FALSE;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_status", nullable = true)
    private UserStatus status;

    @JsonManagedReference
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Booking> bookings;

    public void delete() {
        this.setDeleted(Boolean.TRUE);
    }


}



