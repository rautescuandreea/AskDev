package utcn.ps.assignment1demo.persistance.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import utcn.ps.assignment1demo.entity.Answer;
import utcn.ps.assignment1demo.entity.Question;
import utcn.ps.assignment1demo.entity.Tag;
import utcn.ps.assignment1demo.entity.vote.VoteAnswer;
import utcn.ps.assignment1demo.entity.vote.VoteQuestion;
import utcn.ps.assignment1demo.persistance.api.QuestionRepository;

import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
public class JdbcQuestionRepository implements QuestionRepository {

    private final JdbcTemplate template;

    @Override
    public Question save(Question question) {
        if(question.getVoteQuestion() != null && alreadyVoted(question.getVoteQuestion())) {
            System.out.println("Question already voted!");
        }
        else {
            if (question.getQuestionId() != null) {
                update(question);
            } else {
                Integer id = insert(question);
                question.setQuestionId(id);
            }
            if (!question.getTags().isEmpty()) {
                for (Tag tag : question.getTags()) {
                    setQuestionTagTable(question.getQuestionId(), tag.getTagId());
                }
            }
            if (!question.getAnswers().isEmpty()) {
                for (Answer answer : question.getAnswers()) {
                    setAnswer(answer);
                }
            }
        }
        return question;
    }

    @Override
    public Optional<Question> findById(int id) {
        // bad approach because of sql injection
        // template.query("SELECT * FROM question WHERE question_id = " + question_id,..);

        // therefore it is better to use a placeholder
        List<Question> questions = template.query("SELECT * FROM question WHERE question_id = ?",
                ((resultSet, i) -> new Question(resultSet.getInt("question_id"),
                        resultSet.getString("author"),
                        resultSet.getString("title"),
                        resultSet.getString("body"),
                        resultSet.getObject("creation_date", LocalDateTime.class),
                        resultSet.getInt("up_vote"),
                        resultSet.getInt("down_vote"),
                        resultSet.getInt("user_id"))),
                id);


        if (questions.isEmpty()) {
            return Optional.empty();
        } else {
            Question question = questions.get(0);
            question.setAnswers(getAnswer(question.getQuestionId()));
            question.setTags(getTag(question.getQuestionId()));
            return Optional.of(question);
        }

        // return questions.isEmpty() ? Optional.empty() : Optional.of(questions.get(0));
    }

    @Override
    public void remove(Question question) {
        template.update("DELETE FROM question WHERE question_id = ?", question.getQuestionId());
        template.update("DELETE FROM questions_tags WHERE questions_question_id = ?", question.getQuestionId());
    }

    @Override
    public List<Question> findAll() {
        List<Question> questions = template.query("SELECT * FROM question ORDER BY question_id DESC",
                (resultSet, i) -> new Question(resultSet.getInt("question_id"),
                        resultSet.getString("author"),
                        resultSet.getString("title"),
                        resultSet.getString("body"),
                        resultSet.getObject("creation_date", LocalDateTime.class),
                        resultSet.getInt("up_vote"),
                        resultSet.getInt("down_vote"),
                        resultSet.getInt("user_id")));
        if (!questions.isEmpty()) {
            for (Question question : questions) {
                question.setAnswers(getAnswer(question.getQuestionId()));
                question.setTags(getTag(question.getQuestionId()));
            }
        }
        return questions;
    }

    private int insert(Question question) {
        // we use the SimpleJdbcInsert to easily retrieve the generated ID
        SimpleJdbcInsert insert = new SimpleJdbcInsert(template);
        insert.setTableName("question");
        insert.usingGeneratedKeyColumns("question_id");

        // String for the column's name
        // Object for the column's inserted value
        Map<String, Object> data = new HashMap<>();
        data.put("author", question.getAuthor());
        data.put("title", question.getTitle());
        data.put("body", question.getBody());
        data.put("creation_date", question.getCreationDate());
        data.put("user_id", question.getUserId());
        data.put("up_vote", question.getUpVote());
        data.put("down_vote", question.getDownVote());
        return insert.executeAndReturnKey(data).intValue();
    }

    private void update(Question question) {
        template.update("UPDATE question SET author = ?, title = ?, body = ?, creation_date = ?, user_id = ?, up_vote = ?, down_vote = ? WHERE question_id = ?",
                question.getAuthor(),
                question.getTitle(),
                question.getBody(),
                question.getCreationDate(),
                question.getUserId(),
                question.getUpVote(),
                question.getDownVote(),
                question.getQuestionId()
        );
    }

    public List<Question> getQuestion(Integer userId) {
        List<Question> questions = template.query("SELECT * FROM question WHERE user_id = ?",
                ((resultSet, i) -> new Question(resultSet.getInt("question_id"),
                        resultSet.getString("author"),
                        resultSet.getString("title"),
                        resultSet.getString("body"),
                        resultSet.getObject("creation_date", LocalDateTime.class),
                        resultSet.getInt("up_vote"),
                        resultSet.getInt("down_vote"),
                        resultSet.getInt("user_id"))),
                userId);
        if (!questions.isEmpty()) {
            for (Question question : questions) {
                question.setAnswers(getAnswer(question.getQuestionId()));
                question.setTags(getTag(question.getQuestionId()));
            }
        }
        return questions;
    }

    public List<Answer> getAnswer(Integer questionId) {
        JdbcAnswerRepository jdbcAnswerRepository = new JdbcAnswerRepository(template);
        return jdbcAnswerRepository.getAnswerFromQuestion(questionId);
    }

    public void setAnswer(Answer answer) {
        JdbcAnswerRepository jdbcAnswerRepository = new JdbcAnswerRepository(template);
        jdbcAnswerRepository.save(answer);
    }

    public List<Tag> getTag(Integer questionId) {
        JdbcTagRepository jdbcTagRepository = new JdbcTagRepository(template);
        return jdbcTagRepository.findByQuestionId(questionId);
    }

    public void setQuestionTagTable(Integer questionId, Integer tagId) {
        JdbcTagRepository jdbcTagRepository = new JdbcTagRepository(template);
        jdbcTagRepository.setQuestionTag(questionId, tagId);
    }

    public List<Question> getQuestionFromUser(Integer userId) {
        List<Question> questions = template.query("SELECT * FROM question WHERE user_id = ?",
                ((resultSet, i) -> new Question(resultSet.getInt("question_id"),
                        resultSet.getString("author"),
                        resultSet.getString("title"),
                        resultSet.getString("body"),
                        resultSet.getObject("creation_date", LocalDateTime.class),
                        resultSet.getInt("up_vote"),
                        resultSet.getInt("down_vote"),
                        resultSet.getInt("user_id"))),
                userId);
        if (!questions.isEmpty()) {
            for (Question question : questions) {
                question.setAnswers(getAnswer(question.getQuestionId()));
                question.setTags(getTag(question.getQuestionId()));
            }
        }
        return questions;
    }

    public List<Question> findByTagId(Integer tagId) {
        return template.query("SELECT * FROM question WHERE question_id IN (SELECT question_tag.question_id FROM question_tag WHERE question_tag.tag_id = ?)",
                ((resultSet, i) -> new Question(resultSet.getInt("question_id"),
                        resultSet.getString("author"),
                        resultSet.getString("title"),
                        resultSet.getString("body"),
                        resultSet.getObject("creation_date", LocalDateTime.class),
                        resultSet.getInt("up_vote"),
                        resultSet.getInt("down_vote"),
                        resultSet.getInt("user_id"))),
                tagId);
    }

    public boolean alreadyVoted(VoteQuestion voteQuestion) {
        JdbcVoteQuestionRepository jdbcVoteQuestionRepository = new JdbcVoteQuestionRepository(template);
        return jdbcVoteQuestionRepository.alreadyVoted(voteQuestion);
    }

}
