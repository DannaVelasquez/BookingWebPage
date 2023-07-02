package com.digitalhouse.digitalexpirience.dto.request;

import com.digitalhouse.digitalexpirience.model.user.User;
import com.digitalhouse.digitalexpirience.model.enums.RolEnum;
import com.digitalhouse.digitalexpirience.model.enums.UserStatus;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Builder
@Data
@Getter
@Setter
public class UserRegisterDTO {
    //    todo - creamos validaciones con exprexiones regulares

    @NotNull(message = "El campo requerido no puede ser nulo")
    @NotBlank(message = "El campo requerido no puede quedar en blanco")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "Correo electrónico inválido")
    private String username;

    @NotNull(message = "El campo requerido no puede ser nulo")
    @NotBlank(message = "El campo requerido no puede quedar en blanco")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$", message = "Contraseña inválida")
    private String password;

    @NotNull(message = "El campo requerido no puede ser nulo")
    @NotBlank(message = "El campo requerido no puede quedar en blanco")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$", message = "Contraseña inválida")
    private String repeatedPassword;

    @NotNull(message = "El campo requerido no puede ser nulo")
    @NotBlank(message = "El campo requerido no puede quedar en blanco")
    @Pattern(regexp = "[a-zA-Z\\s]*", message = "Solo esta permitido caracteres Alfabeticos")
    private String firstname;

    @NotNull(message = "El campo requerido no puede ser nulo")
    @NotBlank(message = "El campo requerido no puede quedar en blanco")
    @Pattern(regexp = "[a-zA-Z\\s]*", message = "Solo esta permitido caracteres Alfabeticos")
    private String lastname;

    @AssertTrue(message = "Las contraseñas no coinciden")
    public boolean isPasswordMatching() {
        if (password == null) {
            return repeatedPassword == null;
        } else {
            return password.equals(repeatedPassword);
        }
    }

    public static User userRegistrationDtoToUserModel(UserRegisterDTO userRegisterDTODto){

        return User.builder()
                .username(userRegisterDTODto.getUsername())
                .password(userRegisterDTODto.getPassword())
                .firstname(userRegisterDTODto.getFirstname())
                .lastname(userRegisterDTODto.getLastname())
                .registrationDate(LocalDateTime.now())
                .status(UserStatus.PENDING)
                .build();
    }
}
