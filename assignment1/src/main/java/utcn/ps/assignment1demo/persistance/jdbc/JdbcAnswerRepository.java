package utcn.ps.assignment1demo.persistance.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.data.relational.core.sql.In;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import utcn.ps.assignment1demo.entity.Answer;
import utcn.ps.assignment1demo.entity.vote.VoteAnswer;
import utcn.ps.assignment1demo.persistance.api.AnswerRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class JdbcAnswerRepository implements AnswerRepository {
    /*
        template.query  - when we want to read from the database
        template.update - when we want to modify the database (eg: delete, insert, update)
        The Jdbc template is a helper class for doing JDBC operations (usually "templates" simplify common tasks)
        see https://spring.io/guides/gs/relational-data-access/
    */
    private final JdbcTemplate template;

    @Override
    public Answer save(Answer answer) {
        if(answer.getVoteAnswer() != null && alreadyVoted(answer.getVoteAnswer())) {
            System.out.println("Answer already voted!");
        }
        else {
            if (answer.getAnswerId() != null) {
                update(answer);
            } else {
                Integer id = insert(answer);
                answer.setAnswerId(id);
            }
        }

        return answer;
    }

    @Override
    public Optional<Answer> findById(int id) {
        // bad approach because of sql injection
        // template.query("SELECT * FROM Answer WHERE Answer_id = " + Answer_id,..);

        // therefore it is better to use a placeholder
        List<Answer> answers = template.query("SELECT * FROM answer WHERE answer_id = ?",
                ((resultSet, i) -> new Answer(resultSet.getInt("answer_id"),
                        resultSet.getString("author"),
                        resultSet.getString("body"),
                        resultSet.getObject("creation_date", LocalDateTime.class),
                        resultSet.getInt("up_vote"),
                        resultSet.getInt("down_vote"),
                        resultSet.getInt("question_id"),
                        resultSet.getInt("user_id"))),
                id);


        if (answers.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(answers.get(0));
        }

        // return Answers.isEmpty() ? Optional.empty() : Optional.of(Answers.get(0));
    }

    @Override
    public void remove(Answer answer) {
        template.update("DELETE FROM answer WHERE answer_id = ?", answer.getAnswerId());
    }

    @Override
    public List<Answer> findAll() {
        return template.query("SELECT * FROM Answer",
                (resultSet, i) -> new Answer(resultSet.getInt("answer_id"),
                        resultSet.getString("author"),
                        resultSet.getString("body"),
                        resultSet.getObject("creation_date", LocalDateTime.class),
                        resultSet.getInt("up_vote"),
                        resultSet.getInt("down_vote"),
                        resultSet.getInt("question_id"),
                        resultSet.getInt("user_id")));
    }

    private int insert(Answer answer) {
        // we use the SimpleJdbcInsert to easily retrieve the generated ID
        SimpleJdbcInsert insert = new SimpleJdbcInsert(template);
        insert.setTableName("answer");
        insert.usingGeneratedKeyColumns("answer_id");

        // String for the column's name
        // Object for the column's inserted value
        Map<String, Object> data = new HashMap<>();
        data.put("author", answer.getAuthor());
        data.put("body", answer.getBody());
        data.put("creation_date", answer.getCreationDate());
        data.put("question_id", answer.getQuestionId());
        data.put("user_id", answer.getUserId());
        data.put("up_vote", answer.getUpVote());
        data.put("down_vote", answer.getDownVote());
        return insert.executeAndReturnKey(data).intValue();
    }

    private void update(Answer answer) {
        template.update("UPDATE answer SET body = ?, creation_date = ?, question_id = ?, user_id = ?, up_vote = ?, down_vote = ? WHERE answer_id = ?",
                answer.getAuthor(),
                answer.getBody(),
                answer.getCreationDate(),
                answer.getQuestionId(),
                answer.getUserId(),
                answer.getUpVote(),
                answer.getDownVote(),
                answer.getAnswerId());
    }

    public List<Answer> getAnswerFromQuestion(Integer questionId) {
        return template.query("SELECT * FROM answer WHERE question_id = ?",
                ((resultSet, i) -> new Answer(resultSet.getInt("answer_id"),
                        resultSet.getString("author"),
                        resultSet.getString("body"),
                        resultSet.getObject("creation_date", LocalDateTime.class),
                        resultSet.getInt("up_vote"),
                        resultSet.getInt("down_vote"),
                        resultSet.getInt("question_id"),
                        resultSet.getInt("user_id"))),
                questionId);
    }

    public List<Answer> getAnswerFromUser(Integer userId) {
        return template.query("SELECT * FROM answer WHERE user_id = ?",
                ((resultSet, i) -> new Answer(resultSet.getInt("answer_id"),
                        resultSet.getString("author"),
                        resultSet.getString("body"),
                        resultSet.getObject("creation_date", LocalDateTime.class),
                        resultSet.getInt("up_vote"),
                        resultSet.getInt("down_vote"),
                        resultSet.getInt("question_id"),
                        resultSet.getInt("user_id"))),
                userId);
    }

    public boolean alreadyVoted(VoteAnswer voteAnswer) {
        JdbcVoteAnswerRepository jdbcVoteAnswerRepository = new JdbcVoteAnswerRepository(template);
        return jdbcVoteAnswerRepository.alreadyVoted(voteAnswer);
    }
}