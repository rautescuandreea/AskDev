package utcn.ps.assignment1demo.event;

import lombok.Data;
import utcn.ps.assignment1demo.dto.TagDto;

@Data
public class TagCreatedEvent extends BaseEvent {
    private final TagDto tag;

    public TagCreatedEvent(TagDto tag) {
        super(EventType.TAG_CREATED);
        this.tag = tag;
    }
}
