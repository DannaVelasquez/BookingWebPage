package com.digitalhouse.digitalexpirience.dto.request;

import com.digitalhouse.digitalexpirience.model.expirience.Politic;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;

@Data
@Builder
public class PoliticDTO {
    private Long id;

    private String houseRules;

    private String healthAndSafety;

    private String cancellationPolicies;

    public static Politic politicDtoToPoliticModel(PoliticDTO politicDTO){
        return Politic.builder()
                .id(politicDTO.getId())
                .houseRules(politicDTO.getHouseRules())
                .healthAndSafety(politicDTO.getHealthAndSafety())
                .cancellationPolicies(politicDTO.getCancellationPolicies())
                .build();
    }
}
