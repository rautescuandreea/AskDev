package utcn.ps.assignment1demo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utcn.ps.assignment1demo.dto.VoteAnswerDto;
import utcn.ps.assignment1demo.entity.vote.Vote;
import utcn.ps.assignment1demo.entity.vote.VoteAnswer;
import utcn.ps.assignment1demo.exception.VoteAnswerNotFoundException;
import utcn.ps.assignment1demo.persistance.api.RepositoryFactory;

import java.util.List;
import java.util.stream.Collectors;

// The @Service is a specialized @Component (https://www.baeldung.com/spring-component-repository-service)
@Service
@RequiredArgsConstructor
public class VoteAnswerService {
    private final RepositoryFactory factory;

    /*
        Transactional methods ensure that the code contained inside is run in a transaction,
        which is committed when the methods returns and is rolled-back when an exception is thrown
        see http://www.codingpedia.org/jhadesdev/how-does-spring-transactional-really-work/
    */
    @Transactional
    public VoteAnswerDto findById(Integer id) {
        return VoteAnswerDto.voteAnswerDtoFromVoteAnswer(factory.createVoteAnswerRepository().findById(id).orElseThrow(VoteAnswerNotFoundException::new));
    }

    @Transactional
    public List<VoteAnswerDto> findAll() {
        return factory.createVoteAnswerRepository().findAll().stream().map(VoteAnswerDto::voteAnswerDtoFromVoteAnswer).collect(Collectors.toList());
    }

    @Transactional
    public VoteAnswerDto insert(VoteAnswerDto voteAnswerDto) {
        VoteAnswer voteAnswer = new VoteAnswer(voteAnswerDto.getVoteId(), voteAnswerDto.getUserId(), voteAnswerDto.getAnswerId());
        return VoteAnswerDto.voteAnswerDtoFromVoteAnswer(factory.createVoteAnswerRepository().save(voteAnswer));
    }

    @Transactional
    public VoteAnswerDto update(VoteAnswerDto voteAnswerDto) {
        VoteAnswer voteAnswer = new VoteAnswer(voteAnswerDto.getVoteId(), voteAnswerDto.getUserId(), voteAnswerDto.getAnswerId());
        return VoteAnswerDto.voteAnswerDtoFromVoteAnswer(factory.createVoteAnswerRepository().save(voteAnswer));
    }

    @Transactional
    public void remove(int id) {
        VoteAnswer VoteAnswer = factory.createVoteAnswerRepository().findById(id).orElseThrow(VoteAnswerNotFoundException::new);
        factory.createVoteAnswerRepository().remove(VoteAnswer);
    }
}