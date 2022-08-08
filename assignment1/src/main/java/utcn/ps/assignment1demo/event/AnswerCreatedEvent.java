package utcn.ps.assignment1demo.event;

import lombok.Data;
import utcn.ps.assignment1demo.dto.AnswerDto;

@Data
public class AnswerCreatedEvent extends BaseEvent {
    private final AnswerDto answer;

    public AnswerCreatedEvent(AnswerDto answer) {
        super(EventType.ANSWER_CREATED);
        this.answer = answer;
    }
}
