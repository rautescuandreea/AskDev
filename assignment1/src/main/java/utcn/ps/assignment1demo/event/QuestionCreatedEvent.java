package utcn.ps.assignment1demo.event;

import lombok.Data;
import utcn.ps.assignment1demo.dto.QuestionDto;

@Data
public class QuestionCreatedEvent extends BaseEvent {
    private final QuestionDto question;

    public QuestionCreatedEvent(QuestionDto question) {
        super(EventType.QUESTION_CREATED);
        this.question = question;
    }
}
