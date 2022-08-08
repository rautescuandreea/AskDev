package utcn.ps.assignment1demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utcn.ps.assignment1demo.dto.AnswerDto;
import utcn.ps.assignment1demo.entity.Answer;
import utcn.ps.assignment1demo.entity.Question;
import utcn.ps.assignment1demo.event.AnswerCreatedEvent;
import utcn.ps.assignment1demo.exception.AnswerNotFoundException;
import utcn.ps.assignment1demo.persistance.api.RepositoryFactory;

import java.util.List;
import java.util.stream.Collectors;

// The @Service is a specialized @Component (https://www.baeldung.com/spring-component-repository-service)
@Service
@RequiredArgsConstructor
public class AnswerService {
    private final RepositoryFactory factory;
    private final ApplicationEventPublisher eventPublisher;

    /*
        Transactional methods ensure that the code contained inside is run in a transaction,
        which is committed when the methods returns and is rolled-back when an exception is thrown
        see http://www.codingpedia.org/jhadesdev/how-does-spring-transactional-really-work/
    */
    @Transactional
    public AnswerDto findById(Integer id) {
        return AnswerDto.answerDtoFromAnswer(factory.createAnswerRepository().findById(id).orElseThrow(AnswerNotFoundException::new));
    }

    @Transactional
    public List<AnswerDto> findByQuestionId(Integer questionId) {
        return factory.createAnswerRepository().getAnswerFromQuestion(questionId).stream().map(
                AnswerDto::answerDtoFromAnswer).collect(Collectors.toList());
    }

    @Transactional
    public List<AnswerDto> findByUserId(Integer userId) {
        return factory.createAnswerRepository().getAnswerFromUser(userId).stream().map(
                AnswerDto::answerDtoFromAnswer).collect(Collectors.toList());
    }

    @Transactional
    public List<AnswerDto> findAll() {
        return factory.createAnswerRepository().findAll().stream().map(
                AnswerDto::answerDtoFromAnswer).collect(Collectors.toList());
    }

    @Transactional
    public AnswerDto insert(AnswerDto answerDto) {
        return getAnswerDto(answerDto);
    }

    @Transactional
    public AnswerDto update(AnswerDto answerDto) {
        return getAnswerDto(answerDto);
    }

    private AnswerDto getAnswerDto(AnswerDto answerDto) {
        Answer answer = new Answer(answerDto.getAnswerId(), answerDto.getAuthor(), answerDto.getBody(), answerDto.getCreationDate(), answerDto.getUpVote(), answerDto.getDownVote(), answerDto.getQuestionId(), answerDto.getUserId(), answerDto.getVoteAnswer());
        AnswerDto output = AnswerDto.answerDtoFromAnswer(factory.createAnswerRepository().save(answer));
        eventPublisher.publishEvent(new AnswerCreatedEvent(output));
        return output;
    }

    @Transactional
    public void remove(int id) {
        Answer Answer = factory.createAnswerRepository().findById(id).orElseThrow(AnswerNotFoundException::new);
        factory.createAnswerRepository().remove(Answer);
    }
}
