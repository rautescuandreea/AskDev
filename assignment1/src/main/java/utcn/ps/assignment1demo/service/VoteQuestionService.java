package utcn.ps.assignment1demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utcn.ps.assignment1demo.dto.VoteQuestionDto;
import utcn.ps.assignment1demo.entity.vote.VoteQuestion;
import utcn.ps.assignment1demo.exception.VoteQuestionNotFoundException;
import utcn.ps.assignment1demo.persistance.api.RepositoryFactory;

import java.util.List;
import java.util.stream.Collectors;

// The @Service is a specialized @Component (https://www.baeldung.com/spring-component-repository-service)
@Service
@RequiredArgsConstructor
public class VoteQuestionService {
    private final RepositoryFactory factory;

    /*
        Transactional methods ensure that the code contained inside is run in a transaction,
        which is committed when the methods returns and is rolled-back when an exception is thrown
        see http://www.codingpedia.org/jhadesdev/how-does-spring-transactional-really-work/
    */
    @Transactional
    public VoteQuestionDto findById(Integer id) {
        return VoteQuestionDto.voteQuestionDtoFromVoteQuestion(factory.createVoteQuestionRepository().findById(id).orElseThrow(VoteQuestionNotFoundException::new));
    }

    @Transactional
    public List<VoteQuestionDto> findAll() {
        return factory.createVoteQuestionRepository().findAll().stream().map(VoteQuestionDto::voteQuestionDtoFromVoteQuestion).collect(Collectors.toList());
    }

    @Transactional
    public VoteQuestionDto insert(VoteQuestionDto voteQuestionDto) {
        VoteQuestion voteQuestion = new VoteQuestion(voteQuestionDto.getUserId(), voteQuestionDto.getUserId(), voteQuestionDto.getQuestionId());
        return VoteQuestionDto.voteQuestionDtoFromVoteQuestion(factory.createVoteQuestionRepository().save(voteQuestion));
    }

    @Transactional
    public VoteQuestionDto update(VoteQuestionDto voteQuestionDto) {
        VoteQuestion voteQuestion = new VoteQuestion(voteQuestionDto.getUserId(), voteQuestionDto.getUserId(), voteQuestionDto.getQuestionId());
        return VoteQuestionDto.voteQuestionDtoFromVoteQuestion(factory.createVoteQuestionRepository().save(voteQuestion));
    }

    @Transactional
    public void remove(int id) {
        VoteQuestion VoteQuestion = factory.createVoteQuestionRepository().findById(id).orElseThrow(VoteQuestionNotFoundException::new);
        factory.createVoteQuestionRepository().remove(VoteQuestion);
    }
}