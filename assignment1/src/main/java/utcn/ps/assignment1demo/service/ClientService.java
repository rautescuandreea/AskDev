package utcn.ps.assignment1demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utcn.ps.assignment1demo.dto.ClientDto;
import utcn.ps.assignment1demo.entity.Answer;
import utcn.ps.assignment1demo.entity.user.Client;
import utcn.ps.assignment1demo.event.ClientCreatedEvent;
import utcn.ps.assignment1demo.exception.ClientNotFoundException;
import utcn.ps.assignment1demo.persistance.api.RepositoryFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// The @Service is a specialized @Component (https://www.baeldung.com/spring-component-repository-service)
@Service
@RequiredArgsConstructor
public class ClientService {
    private final RepositoryFactory factory;
    private final ApplicationEventPublisher eventPublisher;
    private final PasswordEncoder passwordEncoder;

    /*
        Transactional methods ensure that the code contained inside is run in a transaction,
        which is committed when the methods returns and is rolled-back when an exception is thrown
        see http://www.codingpedia.org/jhadesdev/how-does-spring-transactional-really-work/
    */
    @Transactional
    public ClientDto findById(Integer id) {
        return ClientDto.clientDtoFromClient(factory.createClientRepository().findById(id).orElseThrow(ClientNotFoundException::new));
    }

    @Transactional
    public List<ClientDto> findAll() {
        return factory.createClientRepository().findAll().stream().map(ClientDto::clientDtoFromClient).collect(Collectors.toList());
    }

    @Transactional
    public ClientDto insert(ClientDto clientDto) {
        return getClientDto(clientDto);
    }

    @Transactional
    public ClientDto update(ClientDto clientDto) {
        return getClientDto(clientDto);
    }

    private ClientDto getClientDto(ClientDto clientDto) {
        Client client = new Client(clientDto.getUserId(), clientDto.getUsername(), passwordEncoder.encode(clientDto.getPassword()), clientDto.getBanned(), clientDto.getScore(), new ArrayList<>(), new ArrayList<>());
        ClientDto output =  ClientDto.clientDtoFromClient(factory.createClientRepository().save(client));
        eventPublisher.publishEvent(new ClientCreatedEvent(output));
        return output;
    }

    @Transactional
    public void remove(int id) {
        Client Client = factory.createClientRepository().findById(id).orElseThrow(ClientNotFoundException::new);
        factory.createClientRepository().remove(Client);
    }
}