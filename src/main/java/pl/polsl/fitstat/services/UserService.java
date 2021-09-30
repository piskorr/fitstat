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
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository ) {
        this.repository = repository;

    }

}
