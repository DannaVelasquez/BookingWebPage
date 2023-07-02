package com.digitalhouse.digitalexpirience.model.expirience;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Builder
@Data
@Table(name = "politics")
@AllArgsConstructor
@NoArgsConstructor
public class Politic {
    @Id
    private Long id;

    @Column(name = "house_rules")
    private String houseRules;

    @Column(name = "health_and_safety")
    private String healthAndSafety;

    @Column(name = "cancellation_policies")
    private String cancellationPolicies;
}
