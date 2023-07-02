package com.digitalhouse.digitalexpirience.model.expirience;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "names")
    private String name;

    @Column(name = "descriptions", columnDefinition = "TEXT")
    protected String description;

    @Column(name = "images")
    private String image;

    @Column(name = "deleted")
    protected boolean deleted = Boolean.FALSE;

    public void delete() {
        this.setDeleted(Boolean.TRUE);
    }
}
