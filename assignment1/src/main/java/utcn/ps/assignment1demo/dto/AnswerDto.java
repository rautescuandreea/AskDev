package utcn.ps.assignment1demo.dto;

import lombok.Data;
import utcn.ps.assignment1demo.entity.Answer;
import utcn.ps.assignment1demo.entity.vote.VoteAnswer;

import java.time.LocalDateTime;

@Data
public class AnswerDto {
    private Integer answerId;
    private String author;
    private String body;
    private LocalDateTime creationDate = LocalDateTime.now();;
    private Integer upVote = 0;
    private Integer downVote = 0;
    private Integer questionId;
    private Integer userId;
    private VoteAnswer voteAnswer;

    public static AnswerDto answerDtoFromAnswer(Answer answer) {
        AnswerDto answerDto = new AnswerDto();

        answerDto.setAnswerId(answer.getAnswerId());
        answerDto.setAuthor(answer.getAuthor());
        answerDto.setBody(answer.getBody());
        answerDto.setCreationDate(answer.getCreationDate());
        answerDto.setUpVote(answer.getUpVote());
        answerDto.setDownVote(answer.getDownVote());
        answerDto.setQuestionId(answer.getQuestionId());
        answerDto.setUserId(answer.getUserId());
        answerDto.setVoteAnswer(answer.getVoteAnswer());

        return answerDto;
    }
}
