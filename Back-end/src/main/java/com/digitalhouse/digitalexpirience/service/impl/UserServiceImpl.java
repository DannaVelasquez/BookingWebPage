package com.digitalhouse.digitalexpirience.service.impl;


import com.digitalhouse.digitalexpirience.dto.request.LoginRequest;
import com.digitalhouse.digitalexpirience.dto.request.UserDTO;
import com.digitalhouse.digitalexpirience.dto.request.UserRegisterDTO;
import com.digitalhouse.digitalexpirience.dto.request.UserUpdateDTO;
import com.digitalhouse.digitalexpirience.dto.response.UserResponseDTO;
import com.digitalhouse.digitalexpirience.exception.BusinessException;
import com.digitalhouse.digitalexpirience.mail.MailRegisterUser;
import com.digitalhouse.digitalexpirience.model.CodeConfirm;
import com.digitalhouse.digitalexpirience.model.enums.RolEnum;
import com.digitalhouse.digitalexpirience.model.enums.UserStatus;
import com.digitalhouse.digitalexpirience.model.user.Role;
import com.digitalhouse.digitalexpirience.model.user.User;
import com.digitalhouse.digitalexpirience.repository.CodeConfirmRepository;
import com.digitalhouse.digitalexpirience.repository.RoleRepository;
import com.digitalhouse.digitalexpirience.repository.UserRepository;
import com.digitalhouse.digitalexpirience.service.UserService;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.digitalhouse.digitalexpirience.model.enums.RolEnum.ROLE_ADMIN;
import static com.digitalhouse.digitalexpirience.model.enums.RolEnum.ROLE_USER;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    private MailServiceImpl mailService;

    @Autowired
    private Environment env;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    CodeConfirmRepository codeConfirmRepository;

    @Autowired
    RoleRepository roleRepository;

    @Override
    @SneakyThrows
    public UserResponseDTO userSave(UserRegisterDTO userRegisterDTO) {
        try{
            if (userRegisterDTO.isPasswordMatching()){
                var isUserExist = userRepository.findByUserName(userRegisterDTO.getUsername());
                if (isUserExist.isEmpty()){
                    var userModel = UserRegisterDTO.userRegistrationDtoToUserModel(userRegisterDTO);
                    userModel.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
                    var codeString = UUID.randomUUID().toString();
                    var code = CodeConfirm.builder().code(codeString)
                            .registerDate(Calendar.getInstance())
                            .status(true)
                            .email(userModel.getUsername()).build();
                    codeConfirmRepository.save(code);


                    var checkRoleName = RolEnum.valueOf(ROLE_USER.toString());
                    var role = roleRepository.findByName(checkRoleName);

                    Set<Role> roles = new HashSet<>();
                    roles.add(role.get());
                    userModel.setRoles(roles);

                    userRepository.save(userModel);
                    UserResponseDTO userResponseDTO = UserResponseDTO.userModelToResponseDto(userModel);

                    try {
                        this.sendMailUser(userRegisterDTO, code);
                    }catch (RuntimeException e){
                        logger.error("Fallo el servio de email para confirmar registro");
                    }

                    return userResponseDTO;
                } else {
                    throw new BusinessException("El correo electronico ya esta registrado");
                }
            } else {
                throw new BusinessException("Las contrasenias no coinciden");
            }
        } catch (RuntimeException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    @SneakyThrows
    public void userDelete(Long id) {
        try {
            var isUserExist = userRepository.findById(id);
            if (isUserExist.isPresent()){
                var userModel = isUserExist.get();
                userModel.delete();
                userRepository.save(userModel);
            } else {
                throw new BusinessException("El id del usuario no existe");
            }
        } catch (RuntimeException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    @SneakyThrows
    public UserResponseDTO getUserById(Long id) {
        try {
            var isUserExist = userRepository.findById(id);
            if (isUserExist.isPresent()){
                if (!isUserExist.get().isDeleted()){
                    UserResponseDTO userResponseDTO = UserResponseDTO.userModelToResponseDto(isUserExist.get());
                    return userResponseDTO;
                } else {
                    throw new BusinessException("El id del usuario no existe(borrado logico)");
                }
            } else {
                throw new BusinessException("El id del usuario no existe");
            }
        } catch (RuntimeException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    @SneakyThrows
    public UserResponseDTO updateUser(Long id, UserDTO userDTO) {
        try {
            var isUserExist = userRepository.findById(id);
            if (isUserExist.isPresent()) {
                var userModel = isUserExist.get();
                userModel.setFirstname(userDTO.getFirstname());
                userModel.setLastname(userDTO.getLastname());
                userModel.setUsername(userDTO.getUsername());
                userModel.setPassword(userDTO.getPassword());
                userRepository.save(userModel);
                UserResponseDTO userResponseDTO = UserResponseDTO.userModelToResponseDto(userModel);
                return userResponseDTO;
            } else {
                throw new BusinessException("El id del usuario no existe");
            }
        } catch (RuntimeException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    @SneakyThrows
    public UserResponseDTO userToAdmin(Long id) {
        try {
            var isUserExist = userRepository.findById(id);
            if (isUserExist.isPresent()) {
                var userModel = isUserExist.get();

                var checkRoleName = RolEnum.valueOf(ROLE_ADMIN.toString());
                var role = roleRepository.findByName(checkRoleName);

                Set<Role> roles = new HashSet<>();
                roles.add(role.get());
                userModel.setRoles(roles);

                userRepository.save(userModel);
                return UserResponseDTO.userModelToResponseDto(userModel);

            } else {
                throw new BusinessException("El id del usuario no existe");
            }
        } catch (RuntimeException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    @SneakyThrows
    public UserResponseDTO adminToUser(Long id) {
        try {
            var isUserExist = userRepository.findById(id);
            if (isUserExist.isPresent()) {
                var userModel = isUserExist.get();

                var checkRoleName = RolEnum.valueOf(ROLE_USER.toString());
                var role = roleRepository.findByName(checkRoleName);

                Set<Role> roles = new HashSet<>();
                roles.add(role.get());
                userModel.setRoles(roles);
                userRepository.save(userModel);

                return UserResponseDTO.userModelToResponseDto(userModel);
            } else {
                throw new BusinessException("El id del usuario no existe");
            }
        } catch (RuntimeException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    @SneakyThrows
    public UserResponseDTO loginUser(LoginRequest loginRequest) {
        try {
            var isUserExist = userRepository.findByUserName(loginRequest.getUsername(), loginRequest.getPassword());
            if (isUserExist.isPresent()) {
                var userModel = isUserExist.get();
                UserResponseDTO userResponseDTO = UserResponseDTO.userModelToResponseDto(userModel);
                return userResponseDTO;
            } else {
                throw new BusinessException("O el correo o la contraseña no coinciden");
            }
        } catch (RuntimeException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    public void sendMailUser(UserRegisterDTO userRegisterDTO, CodeConfirm code) {

        String link = env.getProperty("url.front.local") + "/?token=" + code.getCode() + "\n";

        var fullName = userRegisterDTO.getFirstname() + " " + userRegisterDTO.getLastname();

        Map<String, Object> model = new HashMap<>();

        model.put("fullName",fullName);
        model.put("link", link);

        MailRegisterUser mailType = mailService.createMailRegister();
        mailService.sendMail(mailType, model, userRegisterDTO.getUsername());

    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).get();
    }


    @SneakyThrows
    @Transactional
    @Override
    public void registerUserConfirm(String code, Locale locale) {

        var codeEntity = codeConfirmRepository.findByCode(code);

        if (codeEntity.isPresent()) {

            var codeUser = codeEntity.get();

            var userOptional = userRepository.findByUsername(codeUser.getEmail());

            if (userOptional.isPresent()) {
                var user = userOptional.get();
                user.setStatus(UserStatus.CONFIRM_EMAIL);
                userRepository.save(user);
            }
            logger.info("Se confirmo el registro con el usuario'{}'", codeUser.getEmail());
        } else {
            logger.error("fallo la confirmacion del registro de la cuenta.");
            throw new BusinessException("fallo la confirmacion del registro de la cuenta");
        }
    }

    @Override
    @SneakyThrows
    public UserResponseDTO userActualice(Long id,UserUpdateDTO userUpdateDTO) {
        try {
            var isUserExist = userRepository.findById(id);
            if (isUserExist.isPresent()) {
                var userModel = isUserExist.get();
                userModel.setCell(userUpdateDTO.getCell());
                userModel.setDocument(userUpdateDTO.getDocument());
                userModel.setBirthDate(userUpdateDTO.getBirthDate());
                userRepository.save(userModel);
                return UserResponseDTO.userModelToResponseDto(userModel);
            } else  {
                throw new BusinessException("El id del usuario no existe");
            }
        } catch (RuntimeException e) {
            throw new BusinessException(e.getMessage());
        }
    }
}
