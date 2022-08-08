package utcn.ps.assignment1demo.entity;
import lombok.*;
import utcn.ps.assignment1demo.entity.vote.VoteAnswer;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Answer implements Comparable<Answer>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id")
    private Integer answerId;
    @Column(length = 100)
    private String author;
    @Column(length = 1000)
    private String body;
    @Column(name = "creation_date")
    private LocalDateTime creationDate = LocalDateTime.now();
    @Column(name = "up_vote")
    private Integer upVote = 0;
    @Column(name = "down_vote")
    private Integer downVote = 0;
    @Column(name = "question_id")
    private Integer questionId;
    @Column(name = "user_id")
    private Integer userId;
    @Transient
    private VoteAnswer voteAnswer;

    public Answer(String author, String body, Integer questionId, Integer userId) {
        this.author = author;
        this.body = body;
        this.creationDate = LocalDateTime.now();
        this.questionId = questionId;
        this.userId = userId;
        this.upVote = 0;
        this.downVote = 0;
    }

    public Answer(Integer answerId, String author, String body, LocalDateTime creationDate, Integer upVote, Integer downVote, Integer questionId, Integer userId) {
        this.answerId = answerId;
        this.author = author;
        this.body = body;
        this.creationDate = creationDate;
        this.upVote = upVote;
        this.downVote = downVote;
        this.questionId = questionId;
        this.userId = userId;
    }

    public Integer getVoteCount() {
        return upVote - downVote;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "answerId=" + answerId +
                ", author='" + author + '\'' +
                ", body='" + body + '\'' +
                ", creationDate=" + creationDate +
                ", voteCount=" + getVoteCount() +
                '}';
    }

    @Override
    public int compareTo(Answer o) {
        return o.getUpVote() - getUpVote();
    }
}
