package com.digitalhouse.digitalexpirience.dto.response;

import com.digitalhouse.digitalexpirience.model.user.Role;
import com.digitalhouse.digitalexpirience.model.user.User;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
public class LoginResponse extends JwtTokenResponse {

    private Long userId;
    private String username;
    private String firstName;
    private String lastName;
    private Set<Role> roles;


    @Builder
    public LoginResponse(String token, String username, String firstName, String lastName, Long userId, Set<Role> roles) {
        super(token);
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userId = userId;
        this.roles = roles;

    }

    public static LoginResponse toLoginResponse(String token, User user) {
        return LoginResponse.builder()
                .token(token)
                .firstName(user.getFirstname())
                .lastName(user.getLastname())
                .username(user.getUsername())
                .userId(user.getId())
                .roles(user.getRoles())
                .build();

    }

}
