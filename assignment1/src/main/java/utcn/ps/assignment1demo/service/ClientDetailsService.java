package utcn.ps.assignment1demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import utcn.ps.assignment1demo.entity.user.Client;
import utcn.ps.assignment1demo.exception.NotFoundException;
import utcn.ps.assignment1demo.persistance.api.RepositoryFactory;

import javax.transaction.Transactional;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class ClientDetailsService implements UserDetailsService {
    private final RepositoryFactory factory;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Client client = factory.createClientRepository().findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Unknown Client!"));
        return new User(client.getUsername(), client.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority("ROLE_CLIENT")));
    }

    @Transactional
    public Client loadCurrentClient() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return factory.createClientRepository().findByUsername(username).orElseThrow(NotFoundException::new);
    }
}
