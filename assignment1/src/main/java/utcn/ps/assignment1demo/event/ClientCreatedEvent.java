package utcn.ps.assignment1demo.event;

import lombok.Data;
import utcn.ps.assignment1demo.dto.ClientDto;

@Data
public class ClientCreatedEvent extends BaseEvent {
    private final ClientDto client;

    public ClientCreatedEvent(ClientDto client) {
        super(EventType.CLIENT_CREATED);
        this.client = client;
    }
}
