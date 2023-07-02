package com.digitalhouse.digitalexpirience.service;

import com.digitalhouse.digitalexpirience.dto.request.LoginRequest;
import com.digitalhouse.digitalexpirience.dto.request.UserDTO;
import com.digitalhouse.digitalexpirience.dto.request.UserRegisterDTO;
import com.digitalhouse.digitalexpirience.dto.request.UserUpdateDTO;
import com.digitalhouse.digitalexpirience.dto.response.UserResponseDTO;
import com.digitalhouse.digitalexpirience.model.user.User;

import java.util.Locale;

public interface UserService {
    UserResponseDTO userSave(UserRegisterDTO userRegisterDTODTO);

    void userDelete(Long id);

    UserResponseDTO getUserById(Long id);

    UserResponseDTO updateUser(Long id, UserDTO userDTO);

    UserResponseDTO userToAdmin(Long id);

    UserResponseDTO adminToUser(Long id);

    UserResponseDTO loginUser(LoginRequest loginRequest);

    User getUserByUsername(String username);

    void registerUserConfirm(String code, Locale locale);

    UserResponseDTO userActualice(Long id, UserUpdateDTO userUpdateDTO);
}
