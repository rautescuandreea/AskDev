package utcn.ps.assignment1demo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import utcn.ps.assignment1demo.dto.ClientDto;
import utcn.ps.assignment1demo.entity.user.Client;
import utcn.ps.assignment1demo.event.BaseEvent;
import utcn.ps.assignment1demo.service.ClientDetailsService;
import utcn.ps.assignment1demo.service.ClientService;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping(value = "/users")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;
    private final ClientDetailsService clientDetailsService;
    private final SimpMessagingTemplate messagingTemplate;

    @GetMapping()
    public List<ClientDto> findClients() {
        return clientService.findAll();
    }

    @GetMapping(value = "/{id}")
    public ClientDto findClientById(@PathVariable("id") Integer id){
        return clientService.findById(id);
    }

    @PostMapping()
    public ClientDto insert(@RequestBody ClientDto clientDto){
        return clientService.insert(clientDto);
    }

    @PutMapping()
    public ClientDto update(@RequestBody ClientDto clientDto) {
        return clientService.update(clientDto);
    }

    @DeleteMapping(value="/{id}")
    public void remove(@PathVariable("id") Integer id){
        clientService.remove(id);
    }

    @GetMapping("/me")
    public Client loadCurrentClient() {return clientDetailsService.loadCurrentClient();}

    @EventListener(BaseEvent.class)
    public void handleEvent(BaseEvent event) {
        log.info("Got an event: {}.", event);
        messagingTemplate.convertAndSend("/topic/events", event);
    }
}
