package com.rizky.secondhand.service;

import com.rizky.secondhand.dao.UserDao;
import com.rizky.secondhand.entity.JwtRequest;
import com.rizky.secondhand.entity.JwtResponse;
import com.rizky.secondhand.entity.User;
import com.rizky.secondhand.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Service
public class JwtService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private JwtUtil jwtUtil;


    @Autowired
    private AuthenticationManager authenticationManager;


    public JwtResponse createJwtToken(JwtRequest jwtRequest) throws Exception {
        String username = jwtRequest.getUsername();
        String userPassword = jwtRequest.getUserPassword();
        authenticate(username, userPassword);

       final UserDetails userDetails = loadUserByUsername(username);

        String token = jwtUtil.generateToken(userDetails);

        User user = userDao.findById(username).get();

        return new JwtResponse(user, token);

    }
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
     User user = userDao.findById(s).get();
        if (user != null) {
            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    user.getUserPassword(),
                    getAuthority(user));
        }else {
            throw new UsernameNotFoundException("Username is not found");
        }
    }

    private void authenticate(String username, String userPassword) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, userPassword));
        } catch (DisabledException de) {
            throw new DisabledException("USER_DISABLED");
        }catch (BadCredentialsException bce) {
            throw new BadCredentialsException("INVALID_CREDENTIALS");
        }
    }

    private Set getAuthority(User user){
        Set authorities = new HashSet();

        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
        });
        return authorities;
    }
}

