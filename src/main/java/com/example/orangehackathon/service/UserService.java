package com.example.orangehackathon.service;

import com.example.orangehackathon.dto.UserAuth;
import com.example.orangehackathon.dto.UserDTO;
import com.example.orangehackathon.entity.User;
import com.example.orangehackathon.exceptions.ErrorResponse;
import com.example.orangehackathon.exceptions.Errors;
import com.example.orangehackathon.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private BCryptPasswordEncoder bcryptPasswordEncoder;
    @Autowired
    UserRepository userRepository;

    public ResponseEntity<?> registerUser(User user) {
        if (user == null){
            return new ResponseEntity<>(new ErrorResponse(Errors.USER_IS_MISSING.getCode(),
                    Errors.USER_IS_MISSING.getMessage()), HttpStatus.BAD_REQUEST);
        }
        if (!userRepository.existsByEmail(user.getEmail())){
            UserDTO userDto = new UserDTO();
            user.setPassword(bcryptPasswordEncoder.encode(user.getPassword()));
            user = userRepository.save(user);
            BeanUtils.copyProperties(user, userDto);
            ResponseEntity <UserDTO> ret = new ResponseEntity<>(userDto, HttpStatus.OK);
            return ret;
        }
        return new ResponseEntity<>(new ErrorResponse(Errors.USER_EMAIL_ALREADY_EXIST.getCode(),
                Errors.USER_EMAIL_ALREADY_EXIST.getMessage()), HttpStatus.BAD_REQUEST);
    }


    public void login(UserAuth userAuth){
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(email);

        if (user == null) throw new UsernameNotFoundException("Email " + email + " Not found");
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),  mapToGrantedAuthorities());
    }

    private static Collection<? extends GrantedAuthority> mapToGrantedAuthorities() {
        return new ArrayList<>();
    }
}
