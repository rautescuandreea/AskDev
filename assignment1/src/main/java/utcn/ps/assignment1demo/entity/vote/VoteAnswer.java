package utcn.ps.assignment1demo.entity.vote;

import lombok.*;

import javax.persistence.Entity;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "vote_answer")
public class VoteAnswer extends Vote {
    private Integer answerId;

    public VoteAnswer(Integer userId, Integer answerId) {
        super(userId);
        this.answerId = answerId;
    }

    public VoteAnswer(Integer voteId, Integer userId, Integer answerId) {
        super(voteId, userId);
        this.answerId = answerId;
    }
}
