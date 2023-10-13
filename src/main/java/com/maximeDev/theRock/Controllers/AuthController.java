package com.maximeDev.theRock.Controllers;

import com.maximeDev.theRock.Entity.User;
import com.maximeDev.theRock.Repository.UserRepository;
import com.maximeDev.theRock.Services.TokenService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import com.maximeDev.theRock.Entity.Enums.Role;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthController(UserRepository userRepository, AuthenticationManager authenticationManager, TokenService tokenService) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }


    private boolean checkCookieToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    return true;
                }
            }
        }
        return false;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User register(@Valid @RequestBody User user, HttpServletRequest request) {
        if (checkCookieToken(request)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User must logout before registering");
        }

        user.setRole(Role.ROLE_USER);

        PasswordEncoder passwordEncoder
                = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return this.userRepository.save(user);
    }

    @PostMapping("/login")
    public User login(@RequestBody User user, HttpServletResponse response, HttpServletRequest request) {
        if (checkCookieToken(request)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User must logout before registering");
        }
        UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
        Authentication authentication = this.authenticationManager.authenticate(userToken);
        var jwt = tokenService.generateToken(authentication);
        if (jwt == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username/password supplied");
        }
        Cookie cookie = new Cookie("token", jwt);
        cookie.setSecure(true);
        cookie.setHttpOnly(false);
        cookie.getValue();
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60);
        response.addCookie(cookie);
        return (User) authentication.getPrincipal();
    }
}
