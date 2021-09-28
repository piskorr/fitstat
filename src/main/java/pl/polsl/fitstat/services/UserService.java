package pl.polsl.fitstat.services;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.polsl.fitstat.models.UserEntity;
import pl.polsl.fitstat.repositories.UserRepository;

import java.util.Arrays;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = repository.findByUsername(username)
                .orElseThrow(RuntimeException::new);

        GrantedAuthority authority = new SimpleGrantedAuthority("USER");
        User u = new User(user.getEmail(), user.getPassword(), Arrays.asList(authority));
        return u;

    }

    public UserEntity register(String email, String password) {
        UserEntity userEntity = new UserEntity(email, "username", passwordEncoder.encode(password), "firstName", "lastName", null, null);
        return repository.save(userEntity);
    }
}
