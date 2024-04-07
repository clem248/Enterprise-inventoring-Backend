package kg.inai.inventoring.controller;


import kg.inai.inventoring.entity.ERole;
import kg.inai.inventoring.entity.Role;
import kg.inai.inventoring.entity.User;
import kg.inai.inventoring.payload.request.LoginRequest;
import kg.inai.inventoring.payload.request.SignupRequest;
import kg.inai.inventoring.payload.response.JwtResponse;
import kg.inai.inventoring.payload.response.MessageResponse;
import kg.inai.inventoring.repository.RoleRepository;
import kg.inai.inventoring.repository.UserRepository;
import kg.inai.inventoring.security.jwt.JwtUtils;
import kg.inai.inventoring.security.services.UserDetailsImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/api/admin/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        List<String> roles = userDetails.getRoles().stream().map(item -> item.getName().name())
                .collect(Collectors.toList());

        return ResponseEntity
                .ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
    }
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        // Convert role strings to Role entities
        Set<Role> roles = new HashSet<>();
        if (signUpRequest.getRole() == null || signUpRequest.getRole().isEmpty()) {
            Role userRole = roleRepository.findByName(ERole.ROLE_MANAGER)
                    .orElseThrow(() -> new RuntimeException("Error: Default role is not found."));
            roles.add(userRole);
        } else {
            signUpRequest.getRole().forEach(role -> {
                Role userRole = roleRepository.findByName(ERole.valueOf("ROLE_" + role.toUpperCase()))
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(userRole);
            });
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()), roles);

        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }



}
