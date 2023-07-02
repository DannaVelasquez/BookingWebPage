package com.digitalhouse.digitalexpirience.controller;

import com.digitalhouse.digitalexpirience.dto.request.LoginRequest;
import com.digitalhouse.digitalexpirience.dto.request.UserDTO;
import com.digitalhouse.digitalexpirience.dto.request.UserRegisterDTO;
import com.digitalhouse.digitalexpirience.dto.request.UserUpdateDTO;
import com.digitalhouse.digitalexpirience.dto.response.ApiResponseDTO;
import com.digitalhouse.digitalexpirience.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Locale;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Object> userControllerSave(@Valid @RequestBody UserRegisterDTO userRegisterDTODTO) {
        var userResponseDTO = userService.userSave(userRegisterDTODTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponseDTO.created(userResponseDTO, "Success", HttpStatus.CREATED));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> userControllerDelete(@PathVariable Long id){
        userService.userDelete(id);
        return ResponseEntity.ok().body(ApiResponseDTO.created(null, "Success", HttpStatus.OK));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Object> userControllerRead(@PathVariable Long id){
        var userResponseDTO = userService.getUserById(id);
        return ResponseEntity.ok().body(ApiResponseDTO.created(userResponseDTO, "Success", HttpStatus.OK));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> userControllerUpdate(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO) {
        var userResponseDTO = userService.updateUser(id, userDTO);
        return ResponseEntity.ok().body(ApiResponseDTO.created(userResponseDTO, "Success", HttpStatus.OK));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @PutMapping("/user-to-admin/{id}")
    public ResponseEntity<Object> userControllerUserToAdmin(@PathVariable Long id){
        var userResponseDTO = userService.userToAdmin(id);
        return ResponseEntity.ok().body(ApiResponseDTO.created(userResponseDTO, "Success", HttpStatus.OK));
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @PutMapping("/admin-to-user/{id}")
    public ResponseEntity<Object> userControllerAdminToUser(@PathVariable Long id){
        var userResponseDTO = userService.adminToUser(id);
        return ResponseEntity.ok().body(ApiResponseDTO.created(userResponseDTO, "Success", HttpStatus.OK));
    }

    @GetMapping("/login")
    public ResponseEntity<Object> userLogin(@RequestBody LoginRequest loginRequest){
        var userResponseDTO = userService.loginUser(loginRequest);
        return ResponseEntity.ok().body(ApiResponseDTO.created(userResponseDTO, "Success", HttpStatus.OK));
    }


    @PostMapping(path = "/register/confirm/{code}")
    public ResponseEntity<?> registerConfirm(@PathVariable String code, Locale locale) {
        userService.registerUserConfirm(code, locale);
        return ResponseEntity.ok().body(ApiResponseDTO.created(null, "Success", HttpStatus.OK));
    }

    @PutMapping(path = "/actualize/{id}")
    public ResponseEntity<?> actualizeUser(@PathVariable Long id, @Valid @RequestBody UserUpdateDTO userUpdateDTO) {
        var userResponseDTO = userService.userActualice(id, userUpdateDTO);
        return ResponseEntity.ok().body(ApiResponseDTO.created(userResponseDTO, "Success", HttpStatus.OK));
    }

}
