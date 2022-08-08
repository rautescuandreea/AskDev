package utcn.ps.assignment1demo.entity.user;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString(of = {"userId", "username"})
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;
    @Column(length = 100)
    private String username;
    @Column(length = 100)
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
