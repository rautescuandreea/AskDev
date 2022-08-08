package utcn.ps.assignment1demo.dto;

import lombok.Data;
import utcn.ps.assignment1demo.entity.Answer;
import utcn.ps.assignment1demo.entity.vote.VoteAnswer;

import javax.persistence.Column;

@Data
public class VoteAnswerDto {
    private Integer voteId;
    private Integer userId;
    private Integer answerId;

    public static VoteAnswerDto voteAnswerDtoFromVoteAnswer(VoteAnswer voteAnswer) {
        VoteAnswerDto voteAnswerDto = new VoteAnswerDto();

        voteAnswerDto.setVoteId(voteAnswer.getVoteId());
        voteAnswerDto.setUserId(voteAnswer.getUserId());
        voteAnswerDto.setAnswerId(voteAnswer.getAnswerId());

        return voteAnswerDto;
    }
}
