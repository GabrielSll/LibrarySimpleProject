package com.example.neo.first_biblioteca_project.service;

import com.example.neo.first_biblioteca_project.dto.LoginRequestDTO;
import com.example.neo.first_biblioteca_project.dto.LoginResponseDTO;
import com.example.neo.first_biblioteca_project.exception.ResourceAlreadyExistsException;
import com.example.neo.first_biblioteca_project.model.UserModel;
import com.example.neo.first_biblioteca_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    public LoginResponseDTO login(LoginRequestDTO dto) {

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());

        Authentication authentication = authenticationManager.authenticate(authToken);

        UserModel user = (UserModel) authentication.getPrincipal();

        String token = tokenService.generateToken(user);

        return new LoginResponseDTO(token);
    }

    public void register(LoginRequestDTO dto) {

        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new ResourceAlreadyExistsException(
                    "There is already a user with this email: " + dto.getEmail());
        }

        UserModel user = new UserModel();
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole("ROLE_USER");
        userRepository.save(user);
    }
}
