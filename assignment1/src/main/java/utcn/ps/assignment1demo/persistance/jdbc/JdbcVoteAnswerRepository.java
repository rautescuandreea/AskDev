package utcn.ps.assignment1demo.persistance.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import utcn.ps.assignment1demo.entity.Answer;
import utcn.ps.assignment1demo.entity.Question;
import utcn.ps.assignment1demo.entity.vote.VoteAnswer;
import utcn.ps.assignment1demo.persistance.api.VoteAnswerRepository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class JdbcVoteAnswerRepository implements VoteAnswerRepository {
    private final JdbcTemplate template;

    @Override
    public VoteAnswer save(VoteAnswer voteAnswer) {
        if (voteAnswer.getVoteId() != null) {
            update(voteAnswer);
        } else {
            Integer id = insert(voteAnswer);
            voteAnswer.setVoteId(id);
        }
        return voteAnswer;
    }

    @Override
    public Optional<VoteAnswer> findById(int id) {
        // bad approach because of sql injection
        // template.query("SELECT * FROM VoteAnswer WHERE VoteAnswer_id = " + VoteAnswer_id,..);

        // therefore it is better to use a placeholder
        List<VoteAnswer> voteAnswers = template.query("SELECT * FROM vote_answer WHERE vote_id = ?",
                ((resultSet, i) -> new VoteAnswer(resultSet.getInt("vote_id"),
                        resultSet.getInt("user_id"),
                        resultSet.getInt("answer_id"))),
                id);


        if (voteAnswers.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(voteAnswers.get(0));
        }

        // return VoteAnswers.isEmpty() ? Optional.empty() : Optional.of(VoteAnswers.get(0));
    }

    @Override
    public void remove(VoteAnswer voteAnswer) {
        template.update("DELETE FROM vote_answer WHERE vote_id = ?", voteAnswer.getVoteId());
    }

    @Override
    public List<VoteAnswer> findAll() {
        return template.query("SELECT * FROM VoteAnswer",
                (resultSet, i) -> new VoteAnswer(resultSet.getInt("vote_id"),
                        resultSet.getInt("user_id"),
                        resultSet.getInt("answer_id")));
    }

    public boolean alreadyVoted(VoteAnswer voteAnswer) {
        List<VoteAnswer> voteAnswers = template.query("SELECT * FROM vote_answer WHERE user_id = ? and answer_id = ?",
                ((resultSet, i) -> new VoteAnswer(resultSet.getInt("vote_id"),
                        resultSet.getInt("user_id"),
                        resultSet.getInt("answer_id"))),
                voteAnswer.getUserId(), voteAnswer.getAnswerId());
        if (voteAnswers.isEmpty()) {
            save(voteAnswer);
        }
        return !voteAnswers.isEmpty();
    }

    private int insert(VoteAnswer voteAnswer) {
        // we use the SimpleJdbcInsert to easily retrieve the generated ID
        SimpleJdbcInsert insert = new SimpleJdbcInsert(template);
        insert.setTableName("vote_answer");
        insert.usingGeneratedKeyColumns("vote_id");

        // String for the column's name
        // Object for the column's inserted value
        Map<String, Object> data = new HashMap<>();
        data.put("vote_id", voteAnswer.getVoteId());
        data.put("user_id", voteAnswer.getUserId());
        data.put("answer_id", voteAnswer.getAnswerId());
        return insert.executeAndReturnKey(data).intValue();
    }

    private void update(VoteAnswer voteAnswer) {
        template.update("UPDATE vote_answer SET user_id = ?, answer_id = ? WHERE vote_id = ?",
                voteAnswer.getUserId(),
                voteAnswer.getAnswerId(),
                voteAnswer.getVoteId());
    }
}
