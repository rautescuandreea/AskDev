package utcn.ps.assignment1demo.event;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class BaseEvent {
    private final EventType type;
}
