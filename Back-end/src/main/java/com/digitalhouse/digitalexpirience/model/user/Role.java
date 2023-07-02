package com.digitalhouse.digitalexpirience.model.user;

import com.digitalhouse.digitalexpirience.model.enums.RolEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Setter
@Getter
@AllArgsConstructor
@Table(name = "roles")
public class Role implements Serializable {


    private static final long serialVersionUID = 1L;

    private Role() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private RolEnum name;

}