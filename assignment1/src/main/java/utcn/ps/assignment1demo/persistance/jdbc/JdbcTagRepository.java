package utcn.ps.assignment1demo.persistance.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import utcn.ps.assignment1demo.entity.Question;
import utcn.ps.assignment1demo.entity.Tag;
import utcn.ps.assignment1demo.persistance.api.TagRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class JdbcTagRepository implements TagRepository {
    /*
        template.query  - when we want to read from the database
        template.update - when we want to modify the database (eg: delete, insert, update)
        The Jdbc template is a helper class for doing JDBC operations (usually "templates" simplify common tasks)
        see https://spring.io/guides/gs/relational-data-access/
    */
    private final JdbcTemplate template;

    @Override
    public Tag save(Tag tag) {
        if (tag.getTagId() != null) {
            update(tag);
        } else {
            Integer id = insert(tag);
            tag.setTagId(id);
        }
        return tag;
    }

    @Override
    public Optional<Tag> findById(int id) {
        // bad approach because of sql injection
        // template.query("SELECT * FROM tag WHERE tag_id = " + tag_id,..);

        // therefore it is better to use a placeholder
        List<Tag> tags = template.query("SELECT * FROM tag WHERE tag_id = ?",
                ((resultSet, i) -> new Tag(resultSet.getInt("tag_id"),
                        resultSet.getString("tag"))),
                id);

        if (tags.isEmpty()) {
            return Optional.empty();
        } else {
            Tag tag = tags.get(0);
            tag.setQuestions(getQuestion(tag.getTagId()));
            return Optional.of(tag);
        }

        // return tags.isEmpty() ? Optional.empty() : Optional.of(tags.get(0));
    }

    @Override
    public void remove(Tag tag) {
        template.update("DELETE FROM tag WHERE tag_id = ?", tag.getTagId());
        template.update("DELETE FROM question_tag WHERE tag_id = ?", tag.getTagId());
    }

    @Override
    public List<Tag> findAll() {
        List<Tag> tags = template.query("SELECT * FROM tag",
                (resultSet, i) -> new Tag(resultSet.getInt("tag_id"),
                        resultSet.getString("tag")));
        if (!tags.isEmpty()) {
            for (Tag tag : tags) {
                tag.setQuestions(getQuestion(tag.getTagId()));
            }
        }
        return tags;
    }

    private int insert(Tag tag) {
        // we use the SimpleJdbcInsert to easily retrieve the generated ID
        SimpleJdbcInsert insert = new SimpleJdbcInsert(template);
        insert.setTableName("tag");
        insert.usingGeneratedKeyColumns("tag_id");

        // String for the column's name
        // Object for the column's inserted value
        Map<String, Object> data = new HashMap<>();
        data.put("tag", tag.getTag());
        return insert.executeAndReturnKey(data).intValue();
    }

    private void update(Tag tag) {
        template.update("UPDATE tag SET tag = ? WHERE tag_id = ?",
                tag.getTag(),
                tag.getTagId());
    }

    public List<Tag> findByQuestionId(Integer questionId) {
        return template.query("SELECT * FROM tag WHERE tag_id IN (SELECT question_tag.tag_id FROM question_tag WHERE question_tag.question_id = ?)",
                (resultSet, i) -> new Tag(
                        resultSet.getInt("tag_id"),
                        resultSet.getString("tag")
                ),
                questionId);
    }

    public void setQuestionTag(Integer questionId, Integer tagId) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(template);
        insert.setTableName("question_tag");

        // String for the column's name
        // Object for the column's inserted value
        Map<String, Object> data = new HashMap<>();
        data.put("question_id", questionId);
        data.put("tag_id", tagId);
        insert.execute(data);
    }

    public List<Question> getQuestion(Integer tagId) {
        JdbcQuestionRepository jdbcQuestionRepository = new JdbcQuestionRepository(template);
        return jdbcQuestionRepository.findByTagId(tagId);
    }
}
