package utcn.ps.assignment1demo.entity.vote;

import lombok.*;

import javax.persistence.Entity;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "vote_question")
public class VoteQuestion extends Vote {
    private Integer questionId;

    public VoteQuestion(Integer userId, Integer questionId) {
        super(userId);
        this.questionId = questionId;
    }

    public VoteQuestion(Integer voteId, Integer userId, Integer questionId) {
        super(voteId, userId);
        this.questionId = questionId;
    }
}
