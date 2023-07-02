package com.digitalhouse.digitalexpirience.model.place;

import lombok.*;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
@Table(name = "cities")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "names")
    private String name;

    @Column(name = "latitudes")
    private Float latitude;

    @Column(name = "longitudes")
    private Float longitude;

    @ManyToMany
    @JoinTable(name = "cities_places",
            joinColumns = @JoinColumn(name = "cities_id"),
            inverseJoinColumns = @JoinColumn(name = "places_id"))
    private Set<Place> places = new HashSet<>();

    @Column(name = "deleted")
    private boolean deleted = Boolean.FALSE;

    public void delete() {
        this.setDeleted(Boolean.TRUE);
    }
}
