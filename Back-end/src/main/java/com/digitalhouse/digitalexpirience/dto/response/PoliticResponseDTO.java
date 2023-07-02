package com.digitalhouse.digitalexpirience.dto.response;

import com.digitalhouse.digitalexpirience.model.expirience.Politic;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PoliticResponseDTO {
    private Long id;

    private String houseRules;

    private String healthAndSafety;

    private String cancellationPolicies;

    public static PoliticResponseDTO politicModelToPoliticResponseDTO(Politic politic){
        return PoliticResponseDTO.builder()
                .id(politic.getId())
                .houseRules(politic.getHouseRules())
                .healthAndSafety(politic.getHealthAndSafety())
                .cancellationPolicies(politic.getCancellationPolicies())
                .build();
    }
}
