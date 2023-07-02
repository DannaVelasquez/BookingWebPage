package com.digitalhouse.digitalexpirience.model.expirience;

import com.digitalhouse.digitalexpirience.model.place.Place;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;
import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "experiences")
@SQLDelete(sql = "UPDATE experiences SET deleted = true WHERE id=?")
@Where(clause = "DELETED=false")
public class Experience implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titles")
    protected String title;

    @Column(name = "descriptions", columnDefinition = "TEXT")
    protected String description;

    @ElementCollection
    @CollectionTable(name = "experience_images", joinColumns = @JoinColumn(name = "experience_id"))
    @Column(name = "image_url", nullable = false)
    protected Collection<String> images;

    @Column(name = "duration_in_days", nullable = false)
    protected int durationInDays;

    @ManyToOne
    @JoinColumn(name = "category_id")
    protected Category category;

    @ManyToMany
    @JoinTable(name = "experiences_attributes",
            joinColumns =  @JoinColumn(name = "experiences_id"),
            inverseJoinColumns = @JoinColumn(name = "attributes_id"))
    protected Set<Attribute> attributes = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "place_id")
    private Place place;

    @Column(name = "amount_for_person")
    private Double amountForPerson;

    @ManyToOne
    @JoinColumn(name = "pilitic_id")
    private Politic politics;

    @Column(name = "deleted")
    protected boolean deleted = Boolean.FALSE;

    @JsonManagedReference
    @OneToMany(mappedBy = "experience")
    private List<Booking> bookings;

    public void delete() {
        this.setDeleted(Boolean.TRUE);
    }


}
