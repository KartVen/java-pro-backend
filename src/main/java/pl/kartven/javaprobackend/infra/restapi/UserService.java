package pl.kartven.javaprobackend.infra.restapi;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.kartven.javaprobackend.exception.structure.NotFoundException;
import pl.kartven.javaprobackend.exception.structure.ServerProcessingException;
import pl.kartven.javaprobackend.infra.model.entity.User;
import pl.kartven.javaprobackend.infra.model.repository.UserRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(NotFoundException::new);
    }

    public User getContenxtUser() {
        return Optional.of(getContextUserDetails())
                .map(User.class::cast)
                .map(User::getId)
                .map(id -> userRepository.findById(id).orElseThrow(NotFoundException::new))
                .orElseThrow(ServerProcessingException::new);
    }

    private UserDetails getContextUserDetails() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(auth -> (UserDetails) auth.getPrincipal())
                .orElseThrow(ServerProcessingException::new);
    }
}
