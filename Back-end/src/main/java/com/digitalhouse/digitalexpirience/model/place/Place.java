package com.digitalhouse.digitalexpirience.model.place;

import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Places")
@SQLDelete(sql = "UPDATE Places SET DELETED = true WHERE id=?")
@Where(clause = "DELETED=false")

public class Place {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "names")
    private String name;

    @Column(name = "latitudes")
    protected Float latitude;

    @Column(name = "longitudes")
    protected Float longitude;

    @Column(name = "countries")
    private String country;

    @Column(name = "cities")
    private String city;

    @Column(name = "deleted")
    private boolean deleted = Boolean.FALSE;

    public void delete() {
        this.setDeleted(Boolean.TRUE);
    }

}
