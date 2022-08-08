package utcn.ps.assignment1demo.dto;

import lombok.Data;
import utcn.ps.assignment1demo.entity.Question;
import utcn.ps.assignment1demo.entity.vote.VoteQuestion;

import javax.persistence.Column;

@Data
public class VoteQuestionDto {
    private Integer voteId;
    private Integer userId;
    private Integer questionId;

    public static VoteQuestionDto voteQuestionDtoFromVoteQuestion(VoteQuestion voteQuestion) {
        VoteQuestionDto voteQuestionDto = new VoteQuestionDto();

        voteQuestionDto.setVoteId(voteQuestion.getVoteId());
        voteQuestionDto.setUserId(voteQuestion.getUserId());
        voteQuestionDto.setQuestionId(voteQuestion.getQuestionId());

        return voteQuestionDto;
    }
}
