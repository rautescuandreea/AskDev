package utcn.ps.assignment1demo.persistance.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import utcn.ps.assignment1demo.entity.Answer;
import utcn.ps.assignment1demo.entity.Question;
import utcn.ps.assignment1demo.entity.user.Client;
import utcn.ps.assignment1demo.persistance.api.ClientRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class JdbcClientRepository implements ClientRepository {
    /*
        template.query  - when we want to read from the database
        template.update - when we want to modify the database (eg: delete, insert, update)
        The Jdbc template is a helper class for doing JDBC operations (usually "templates" simplify common tasks)
        see https://spring.io/guides/gs/relational-data-access/
    */
    private final JdbcTemplate template;

    @Override
    public Client save(Client client) {
        if (client.getUserId() != null) {
            update(client);
        } else {
            Integer id = insert(client);
            client.setUserId(id);
        }
        if(!client.getQuestions().isEmpty()) {
            for(Question question : client.getQuestions()) {
                setQuestion(question);
            }
        }

        if(!client.getAnswers().isEmpty()) {
            for(Answer answer : client.getAnswers()) {
                setAnswer(answer);
            }
        }

        return client;
    }



    @Override
    public Optional<Client> findById(int id) {
        // bad approach because of sql injection
        // template.query("SELECT * FROM Client WHERE Client_id = " + Client_id,..);

        // therefore it is better to use a placeholder
        List<Client> clients = template.query("SELECT * FROM client WHERE user_id = ?",
                ((resultSet, i) -> new Client(resultSet.getInt("user_id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getBoolean("banned"),
                        resultSet.getInt("score"))),
                id);

        if (clients.isEmpty()) {
            return Optional.empty();
        } else {
            Client client = clients.get(0);
            client.setAnswers(getAnswer(client.getUserId()));
            client.setQuestions(getQuestion(client.getUserId()));
            return Optional.of(clients.get(0));
        }

        // return Clients.isEmpty() ? Optional.empty() : Optional.of(Clients.get(0));
    }

    @Override
    public Optional<Client> findByUsername(String username) {
        List<Client> clients = template.query("SELECT * FROM client WHERE username = ?",
                ((resultSet, i) -> new Client(resultSet.getInt("user_id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getBoolean("banned"),
                        resultSet.getInt("score"))),
                username);

        if (clients.isEmpty()) {
            System.out.println("Nu gasesc pe nime cu useru " + username);
            return Optional.empty();
        } else {
            Client client = clients.get(0);
            client.setAnswers(getAnswer(client.getUserId()));
            client.setQuestions(getQuestion(client.getUserId()));
            return Optional.of(clients.get(0));
        }
    }

    @Override
    public void remove(Client client) {
        template.update("DELETE FROM client WHERE user_id = ?", client.getUserId());
    }

    @Override
    public List<Client> findAll() {
        List<Client> clients = template.query("SELECT * FROM client",
                (resultSet, i) -> new Client(resultSet.getInt("user_id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getBoolean("banned"),
                        resultSet.getInt("score")));
        if(!clients.isEmpty()) {
            for (Client client : clients) {
                client.setQuestions(getQuestion(client.getUserId()));
                client.setAnswers(getAnswer(client.getUserId()));
            }
        }
        return clients;
    }

    private int insert(Client client) {
        // we use the SimpleJdbcInsert to easily retrieve the generated ID
        SimpleJdbcInsert insert = new SimpleJdbcInsert(template);
        insert.setTableName("client");
        insert.usingGeneratedKeyColumns("user_id");

        // String for the column's name
        // Object for the column's inserted value
        Map<String, Object> data = new HashMap<>();
        data.put("password", client.getPassword());
        data.put("username", client.getUsername());
        data.put("banned", client.getBanned());
        data.put("score", client.getScore());
        return insert.executeAndReturnKey(data).intValue();
    }

    private void update(Client client) {
        template.update("UPDATE client SET password = ?, username = ?, banned = ?, score = ? WHERE user_id = ?",
                client.getPassword(),
                client.getUsername(),
                client.getBanned(),
                client.getScore(),
                client.getUserId());
    }
    private List<Answer> getAnswer(Integer userId) {
        JdbcAnswerRepository jdbcAnswerRepository = new JdbcAnswerRepository(template);
        return jdbcAnswerRepository.getAnswerFromUser(userId);
    }

    public Answer setAnswer(Answer answer) {
        JdbcAnswerRepository jdbcAnswerRepository = new JdbcAnswerRepository(template);
        return jdbcAnswerRepository.save(answer);
    }

    private List<Question> getQuestion(Integer userId) {
        JdbcQuestionRepository jdbcQuestionRepository = new JdbcQuestionRepository(template);
        return jdbcQuestionRepository.getQuestionFromUser(userId);
    }

    private Question setQuestion(Question question) {
        JdbcQuestionRepository jdbcQuestionRepository = new JdbcQuestionRepository(template);
        return jdbcQuestionRepository.save(question);
    }


}
