package utcn.ps.assignment1demo.dto;

import lombok.Data;
import org.springframework.util.CollectionUtils;
import utcn.ps.assignment1demo.entity.Answer;
import utcn.ps.assignment1demo.entity.Question;
import utcn.ps.assignment1demo.entity.Tag;
import utcn.ps.assignment1demo.entity.user.Client;
import utcn.ps.assignment1demo.entity.vote.VoteQuestion;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class QuestionDto {
    private Integer questionId;
    private String author;
    private String title;
    private String body;
    private LocalDateTime creationDate = LocalDateTime.now();
    private Integer upVote = 0;
    private Integer downVote = 0;
    private Integer userId = 0;
    private List<TagDto> tags = new ArrayList<>();
    private List<AnswerDto> answers = new ArrayList<>();
    private VoteQuestion voteQuestion;

    public static QuestionDto questionDtoFromQuestion(Question question) {
        QuestionDto questionDto = new QuestionDto();

        questionDto.setQuestionId(question.getQuestionId());
        questionDto.setAuthor(question.getAuthor());
        questionDto.setTitle(question.getTitle());
        questionDto.setBody(question.getBody());
        questionDto.setCreationDate(question.getCreationDate());
        questionDto.setUpVote(question.getUpVote());
        questionDto.setDownVote(question.getDownVote());
        questionDto.setUserId(question.getUserId());

        if (!CollectionUtils.isEmpty(question.getTags())) {
            questionDto.setTags(question.getTags().stream().map(TagDto::tagDtoFromTag).collect(Collectors.toList()));
        } else {
            questionDto.setTags(new ArrayList<>());
        }

        if (!CollectionUtils.isEmpty(question.getAnswers())) {
            questionDto.setAnswers(question.getAnswers().stream().map(AnswerDto::answerDtoFromAnswer).collect(Collectors.toList()));
        } else {
            questionDto.setAnswers(new ArrayList<>());
        }

        return questionDto;
    }
}
