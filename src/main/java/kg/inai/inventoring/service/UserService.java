package kg.inai.inventoring.service;

import kg.inai.inventoring.entity.User;
import kg.inai.inventoring.repository.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;

    public UserService(PasswordEncoder encoder, UserRepository userRepository) {
        this.encoder = encoder;
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        boolean existsByUsername = userRepository.existsByUsername(user.getUsername());
        boolean existsByEmail = userRepository.existsByEmail(user.getEmail());
        if(existsByUsername || existsByEmail){
            throw new DataIntegrityViolationException("username - " + user.getUsername()  + " or email -" +  user.getEmail() + " already exists.");
        }
        return userRepository.save(user);
    }
}
