package utcn.ps.assignment1demo.entity.user;

import lombok.*;
import utcn.ps.assignment1demo.entity.Answer;
import utcn.ps.assignment1demo.entity.Question;
import utcn.ps.assignment1demo.entity.vote.VoteAnswer;
import utcn.ps.assignment1demo.entity.vote.VoteQuestion;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString(callSuper = true, of = {"id", "username", "score", "banned"})
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Client extends User{
    private Boolean banned = false;
    private Integer score = 0;
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private List<Question> questions = new ArrayList<>();
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private List<Answer> answers = new ArrayList<>();

    public Client(String username, String password) {
        super(username, password);
        this.banned = false;
        this.score = 0;
        this.questions = new ArrayList<>();
        this.answers = new ArrayList<>();
    }

    public Client(Integer userId, String username, String password,
                  Boolean banned, Integer score ) {
        super(userId, username, password);
        this.banned = banned;
        this.score = score;
        this.questions = new ArrayList<>();
        this.answers = new ArrayList<>();
    }

    public Client(Integer userId, String username, String password, Boolean banned, Integer score, List<Question> questions, List<Answer> answers) {
        super(userId, username, password);
        this.banned = banned;
        this.score = score;
        this.questions = questions;
        this.answers = answers;
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public void addAnswer(Answer answer) {
        answers.add(answer);
    }
}
