package com.digitalhouse.digitalexpirience.dto.response;

import com.digitalhouse.digitalexpirience.model.user.Role;
import com.digitalhouse.digitalexpirience.model.user.User;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Builder
@Data
public class UserResponseDTO {
    private Long id;
    private String username;
    private String firstname;
    private String lastname;
    private String birthDate;
    private String document;
    private String cell;
    private Set<Role> roles;

    public static UserResponseDTO userModelToResponseDto(User userModel) {
        return UserResponseDTO.builder()
                .id(userModel.getId())
                .username(userModel.getUsername())
                .firstname(userModel.getFirstname())
                .lastname(userModel.getLastname())
                .birthDate(userModel.getBirthDate())
                .cell(userModel.getCell())
                .document(userModel.getDocument())
                .roles(userModel.getRoles())
                .build();
    }


    public static UserResponseDTO userModelToResponseDtoForBooking(User userModel) {
        return UserResponseDTO.builder()
                .username(userModel.getUsername())
                .firstname(userModel.getFirstname())
                .lastname(userModel.getLastname())
                .birthDate(userModel.getBirthDate())
                .cell(userModel.getCell())
                .document(userModel.getDocument())
                .build();
    }

}
