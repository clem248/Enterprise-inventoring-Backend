package kg.inai.inventoring.controller;

import kg.inai.inventoring.entity.Role;
import kg.inai.inventoring.entity.User;
import kg.inai.inventoring.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@CrossOrigin
@RestController
@RequestMapping("/api/admin/users")
@Validated
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User userData = userService.createUser(user);
        return new ResponseEntity<>(userData, HttpStatus.CREATED);
    }
}
