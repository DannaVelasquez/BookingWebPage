package com.digitalhouse.digitalexpirience.model.expirience;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "attributes")
@SQLDelete(sql = "UPDATE attributes SET deleted = true WHERE id=?")
@Where(clause = "DELETED=false")
public class Attribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "names")
    private String name;

    @Column(name = "images")
    private String image;

    @Column(name = "deleted")
    protected boolean deleted = Boolean.FALSE;

    public void delete() {
        this.setDeleted(Boolean.TRUE);
    }
}
