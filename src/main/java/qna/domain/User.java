package qna.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import qna.exception.UnAuthorizedException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {
    public static final GuestUser GUEST_USER = new GuestUser();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String userId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    private String email;

    @OneToMany(mappedBy = "writer")
    private List<Answer> answers = new ArrayList<>();

    @OneToMany(mappedBy = "writer", cascade = CascadeType.PERSIST)
    private List<Question> questions = new ArrayList<>();

    @OneToMany(mappedBy = "deleteUser")
    private List<DeleteHistory> deleteHistories = new ArrayList<>();

    public User(String userId, String password, String name, String email) {
        this(null, userId, password, name, email);
    }

    public User(Long id, String userId, String password, String name, String email) {
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
    }

    public void update(User loginUser, User target) {
        if (!matchUserId(loginUser.userId)) {
            throw new UnAuthorizedException();
        }

        if (!matchPassword(target.password)) {
            throw new UnAuthorizedException();
        }

        this.name = target.name;
        this.email = target.email;
    }

    private boolean matchUserId(String userId) {
        return this.userId.equals(userId);
    }

    public boolean matchPassword(String targetPassword) {
        return this.password.equals(targetPassword);
    }

    public boolean equalsNameAndEmail(User target) {
        if (Objects.isNull(target)) {
            return false;
        }

        return name.equals(target.name) &&
                email.equals(target.email);
    }

    public void addQuestion(Question question) {
        this.questions.add(question);
    }

    public boolean isGuestUser() {
        return false;
    }

    public Long id() {
        return this.id;
    }

    private static class GuestUser extends User {
        @Override
        public boolean isGuestUser() {
            return true;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(userId, user.userId) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, password);
    }
}
