package utcn.ps.assignment1demo.dto;

import lombok.Data;
import org.springframework.util.CollectionUtils;
import utcn.ps.assignment1demo.entity.Answer;
import utcn.ps.assignment1demo.entity.Question;
import utcn.ps.assignment1demo.entity.user.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ClientDto {
    private Integer userId;
    private String username;
    private String password;
    private Boolean banned = false;
    private Integer score = 0;
    private List<QuestionDto> questions = new ArrayList<>();
    private List<AnswerDto> answers = new ArrayList<>();

    public static ClientDto clientDtoFromClient(Client client) {
        ClientDto clientDto = new ClientDto();

        clientDto.setUserId(client.getUserId());
        clientDto.setUsername(client.getUsername());
        clientDto.setPassword(client.getPassword());
        clientDto.setBanned(client.getBanned());
        clientDto.setScore(client.getScore());

        if (!CollectionUtils.isEmpty(client.getQuestions())) {
            clientDto.setQuestions(client.getQuestions().stream().map(QuestionDto::questionDtoFromQuestion).collect(Collectors.toList()));
        } else {
            clientDto.setQuestions(new ArrayList<>());
        }

        if (!CollectionUtils.isEmpty(client.getAnswers())) {
            clientDto.setAnswers(client.getAnswers().stream().map(AnswerDto::answerDtoFromAnswer).collect(Collectors.toList()));
        } else {
            clientDto.setAnswers(new ArrayList<>());
        }

        return clientDto;
    }
}
