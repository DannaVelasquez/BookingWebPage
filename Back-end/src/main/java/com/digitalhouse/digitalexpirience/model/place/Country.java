package com.digitalhouse.digitalexpirience.model.place;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "countries")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "names")
    protected String name;

    @Column(name = "latitudes")
    protected Float latitude;

    @Column(name = "longitudes")
    protected Float longitude;

    @ManyToMany
    @JoinTable(name = "countries_cities",
            joinColumns = @JoinColumn(name = "countries_id"),
            inverseJoinColumns = @JoinColumn(name = "cities_id"))
    private Set<City> cities = new HashSet<>();

    @Column(name = "deleted")
    private boolean deleted = Boolean.FALSE;

    public void delete() {
        this.setDeleted(Boolean.TRUE);
    }
}
