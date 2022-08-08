package utcn.ps.assignment1demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utcn.ps.assignment1demo.dto.QuestionDto;
import utcn.ps.assignment1demo.entity.Question;
import utcn.ps.assignment1demo.event.QuestionCreatedEvent;
import utcn.ps.assignment1demo.exception.QuestionNotFoundException;
import utcn.ps.assignment1demo.persistance.api.RepositoryFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// The @Service is a specialized @Component (https://www.baeldung.com/spring-component-repository-service)
@Service
@RequiredArgsConstructor
public class QuestionService {
    private final RepositoryFactory factory;
    private final ApplicationEventPublisher eventPublisher;

    /*
        Transactional methods ensure that the code contained inside is run in a transaction,
        which is committed when the methods returns and is rolled-back when an exception is thrown
        see http://www.codingpedia.org/jhadesdev/how-does-spring-transactional-really-work/
    */
    @Transactional
    public QuestionDto findById(Integer id) {
        return QuestionDto.questionDtoFromQuestion(factory.createQuestionRepository().findById(id).orElseThrow(QuestionNotFoundException::new));
    }

    @Transactional
    public List<QuestionDto> findByTagId(Integer tagId) {
        return factory.createQuestionRepository().findByTagId(tagId).stream().map(QuestionDto::questionDtoFromQuestion).collect(Collectors.toList());
    }

    @Transactional
    public List<QuestionDto> findAll() {
        return factory.createQuestionRepository().findAll().stream().map(QuestionDto::questionDtoFromQuestion).collect(Collectors.toList());
    }

    @Transactional
    public QuestionDto insert(QuestionDto questionDto) {
        return getQuestionDto(questionDto);
    }

    @Transactional
    public QuestionDto update(QuestionDto questionDto) {
        return getQuestionDto(questionDto);
    }

    private QuestionDto getQuestionDto(QuestionDto questionDto) {
        Question question = new Question(questionDto.getQuestionId(), questionDto.getAuthor(), questionDto.getTitle(), questionDto.getTitle(), questionDto.getCreationDate(), questionDto.getUpVote(), questionDto.getDownVote(), questionDto.getUserId(), new ArrayList<>(), new ArrayList<>(), questionDto.getVoteQuestion());
        QuestionDto output = QuestionDto.questionDtoFromQuestion(factory.createQuestionRepository().save(question));
        eventPublisher.publishEvent(new QuestionCreatedEvent(output));
        return output;
    }

    @Transactional
    public void remove(int id) {
        Question Question = factory.createQuestionRepository().findById(id).orElseThrow(QuestionNotFoundException::new);
        factory.createQuestionRepository().remove(Question);
    }
}