package utcn.ps.assignment1demo.persistance.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import utcn.ps.assignment1demo.persistance.api.*;

@RequiredArgsConstructor
@Component
@ConditionalOnProperty(name = "repository-type", havingValue = "JDBC")
public class JdbcRepositoryFactory implements RepositoryFactory {

    private final JdbcTemplate template;

    @Override
    public QuestionRepository createQuestionRepository() {
        return new JdbcQuestionRepository(template);
    }

    @Override
    public TagRepository createTagRepository() {
        return new JdbcTagRepository(template);
    }

    @Override
    public AnswerRepository createAnswerRepository() {
        return new JdbcAnswerRepository(template);
    }

    @Override
    public ClientRepository createClientRepository() {
        return new JdbcClientRepository(template);
    }

    @Override
    public VoteQuestionRepository createVoteQuestionRepository() {
        return new JdbcVoteQuestionRepository(template);
    }

    @Override
    public VoteAnswerRepository createVoteAnswerRepository() {
        return new JdbcVoteAnswerRepository(template);
    }

}
