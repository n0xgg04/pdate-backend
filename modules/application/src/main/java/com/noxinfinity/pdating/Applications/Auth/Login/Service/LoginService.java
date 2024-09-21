package com.noxinfinity.pdating.Applications.Auth.Login.Service;

import com.noxinfinity.pdating.Applications.Auth.Login.Dto.LoginRequest;
import com.noxinfinity.pdating.Applications.Auth.Login.Dto.LoginResponse;
import com.noxinfinity.pdating.Domains.AuthManagement.Auth;
import com.noxinfinity.pdating.Domains.AuthManagement.AuthRepo;
import com.noxinfinity.pdating.Domains.JwtManagement.CustomUserDetailsService;
import com.noxinfinity.pdating.Domains.JwtManagement.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoginService {
    private final JwtProvider jwtProvider;

    private final AuthenticationManager authenticationManager;

    private final CustomUserDetailsService customUserDetailsService;
    @Autowired
    public LoginService(JwtProvider jwtTokenProvider, AuthenticationManager authenticationManager, UserDetailsService userDetailsService, CustomUserDetailsService customUserDetailsService) {
        this.jwtProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
        this.customUserDetailsService = customUserDetailsService;
    }

    public LoginResponse login(LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.username(), request.password())
            );
            String token = jwtProvider.createToken(request.username(), customUserDetailsService.loadUserByUsername(request.username())
                    .getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());

            return new LoginResponse(HttpStatus.OK.value(), "Login successful", token);

        } catch (BadCredentialsException e) {
            return new LoginResponse(HttpStatus.UNAUTHORIZED.value(), "Invalid username or password", null);
        } catch (Exception e) {
            return new LoginResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occurred", null);
        }
    }
}
