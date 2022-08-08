package utcn.ps.assignment1demo.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Integer tagId;
    @Column(length = 100)
    private String tag;
    @ManyToMany(mappedBy = "tags")
    private List<Question> questions = new ArrayList<>();

    public Tag (Integer tagId, String tag) {
        this.tagId = tagId;
        this.tag = tag;
        this.questions = new ArrayList<>();
    }
    public Tag (String tag) {
        this.tag = tag;
        this.questions = new ArrayList<>();
    }
    public void addQuestion(Question question) {
        questions.add(question);
    }
}
