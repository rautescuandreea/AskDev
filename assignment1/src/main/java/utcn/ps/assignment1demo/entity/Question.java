package utcn.ps.assignment1demo.entity;

import lombok.*;
import utcn.ps.assignment1demo.entity.vote.VoteQuestion;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString(of = {"question_id", "author", "title", "body", "creationDate"})
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Integer questionId;
    @Column(length = 100)
    private String author;
    @Column(length = 100)
    private String title;
    @Column(length = 1000)
    private String body;
    @Column
    private LocalDateTime creationDate = LocalDateTime.now();
    @Column(name = "up_vote")
    private Integer upVote = 0;
    @Column(name = "down_vote")
    private Integer downVote = 0;
    @Column(name = "user_id")
    private Integer userId = 0;
    @ManyToMany
    @JoinTable(name = "question_tag", joinColumns = { @JoinColumn(name = "question_id") },
            inverseJoinColumns = { @JoinColumn(name = "tag_id") })
    private List<Tag> tags = new ArrayList<>();
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "question_id")
    private List<Answer> answers = new ArrayList<>();
    @Transient
    private VoteQuestion voteQuestion;

    public Question(Integer questionId, String author, String title, String body, LocalDateTime creationDate, Integer upVote, Integer downVote, Integer userId) {
        this.questionId = questionId;
        this.author = author;
        this.title = title;
        this.body = body;
        this.creationDate = creationDate;
        this.upVote = upVote;
        this.downVote = downVote;
        this.tags = new ArrayList<>();
        this.answers = new ArrayList<>();
        this.userId = userId;
    }

    public Question(String author, String title, String body, Integer userId) {
        System.out.println("Ajung aici");
        this.author = author;
        this.title = title;
        this.body = body;
        this.creationDate = LocalDateTime.now();
        this.upVote = 0;
        this.downVote = 0;
        this.tags = new ArrayList<>();
        this.answers = new ArrayList<>();
        this.userId = userId;
    }

    public void addTag(Tag tag) {
        this.tags.add(tag);
    }

    public void addAnswer(Answer answer) {
        this.answers.add(answer);
    }
}

