package com.digitalhouse.digitalexpirience.dto.request;

import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpRequest {

    @NotEmpty
    @Length(min = 4,max = 50)
    private String username;

    @NotEmpty
    @Length(min = 4,max = 50)
    private String password;

}
