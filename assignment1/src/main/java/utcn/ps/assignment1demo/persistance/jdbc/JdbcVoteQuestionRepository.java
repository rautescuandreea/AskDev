package utcn.ps.assignment1demo.persistance.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import utcn.ps.assignment1demo.entity.Answer;
import utcn.ps.assignment1demo.entity.vote.VoteAnswer;
import utcn.ps.assignment1demo.entity.vote.VoteQuestion;
import utcn.ps.assignment1demo.persistance.api.VoteQuestionRepository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class JdbcVoteQuestionRepository implements VoteQuestionRepository {
    private final JdbcTemplate template;

    @Override
    public VoteQuestion save(VoteQuestion voteQuestion) {
        if (voteQuestion.getVoteId() != null) {
            update(voteQuestion);
        } else {
            Integer id = insert(voteQuestion);
            voteQuestion.setVoteId(id);
        }
        return voteQuestion;
    }

    @Override
    public Optional<VoteQuestion> findById(int id) {
        // bad approach because of sql injection
        // template.query("SELECT * FROM VoteQuestion WHERE VoteQuestion_id = " + VoteQuestion_id,..);

        // therefore it is better to use a placeholder
        List<VoteQuestion> voteQuestions = template.query("SELECT * FROM vote_question WHERE vote_id = ?",
                ((resultSet, i) -> new VoteQuestion(resultSet.getInt("vote_id"),
                        resultSet.getInt("user_id"),
                        resultSet.getInt("question_id"))),
                id);


        if (voteQuestions.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(voteQuestions.get(0));
        }

        // return VoteQuestions.isEmpty() ? Optional.empty() : Optional.of(VoteQuestions.get(0));
    }

    @Override
    public void remove(VoteQuestion voteQuestion) {
        template.update("DELETE FROM vote_question WHERE vote_id = ?", voteQuestion.getVoteId());
    }

    @Override
    public List<VoteQuestion> findAll() {
        return template.query("SELECT * FROM VoteQuestion",
                (resultSet, i) -> new VoteQuestion(resultSet.getInt("vote_id"),
                        resultSet.getInt("user_id"),
                        resultSet.getInt("question_id")));
    }

    private int insert(VoteQuestion voteQuestion) {
        // we use the SimpleJdbcInsert to easily retrieve the generated ID
        SimpleJdbcInsert insert = new SimpleJdbcInsert(template);
        insert.setTableName("vote_question");
        insert.usingGeneratedKeyColumns("vote_id");

        // String for the column's name
        // Object for the column's inserted value
        Map<String, Object> data = new HashMap<>();
        data.put("vote_id", voteQuestion.getVoteId());
        data.put("user_id", voteQuestion.getUserId());
        data.put("question_id", voteQuestion.getQuestionId());
        return insert.executeAndReturnKey(data).intValue();
    }

    private void update(VoteQuestion voteQuestion) {
        template.update("UPDATE vote_question SET user_id = ?, question_id = ? WHERE vote_id = ?",
                voteQuestion.getUserId(),
                voteQuestion.getQuestionId(),
                voteQuestion.getVoteId());
    }

    public boolean alreadyVoted(VoteQuestion voteQuestion) {
        List<VoteAnswer> voteQuestions = template.query("SELECT * FROM vote_question WHERE user_id = ? and question_id = ?",
                ((resultSet, i) -> new VoteAnswer(resultSet.getInt("vote_id"),
                        resultSet.getInt("user_id"),
                        resultSet.getInt("question_id"))),
                voteQuestion.getUserId(), voteQuestion.getQuestionId());
        if (voteQuestions.isEmpty()) {
            save(voteQuestion);
        }

        return !voteQuestions.isEmpty();
    }
}
