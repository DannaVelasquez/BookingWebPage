package com.digitalhouse.digitalexpirience.jwt;

import com.digitalhouse.digitalexpirience.model.enums.UserStatus;
import com.digitalhouse.digitalexpirience.model.security.UserDetailsImpl;
import com.digitalhouse.digitalexpirience.model.user.User;
import com.digitalhouse.digitalexpirience.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        if (user.getStatus() == UserStatus.CONFIRM_EMAIL) {
            user.setStatus(UserStatus.ACTIVE);
            userRepository.save(user);
        }
        if (user.getStatus() == UserStatus.PENDING) {
            throw new UsernameNotFoundException("Usuario no confirmado: " + username);
        }

        return UserDetailsImpl.build(user);
    }

}
