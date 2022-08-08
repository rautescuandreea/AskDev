package utcn.ps.assignment1demo.seed;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import utcn.ps.assignment1demo.entity.user.Client;
import utcn.ps.assignment1demo.persistance.api.RepositoryFactory;

@Component
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApplicationSeed implements CommandLineRunner {
    private final RepositoryFactory factory;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (factory.createClientRepository().findAll().isEmpty()) {
            Client client1 = new Client("andreea", passwordEncoder.encode("aki"));
            factory.createClientRepository().save(client1);
            Client client2 = new Client("aki", passwordEncoder.encode("andreea"));
            factory.createClientRepository().save(client2);
        }
    }
}
