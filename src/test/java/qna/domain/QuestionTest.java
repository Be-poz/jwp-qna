package qna.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class QuestionTest {
    public static final Question Q1 = new Question("title1", "contents1").writeBy(UserTest.JAVAJIGI);
    public static final Question Q2 = new Question("title2", "contents2").writeBy(UserTest.SANJIGI);

    @Test
    @DisplayName("deleteWithAnswers")
    public void deleteWithAnswersTest() {
        Q1.addAnswer(AnswerTest.A1);
        Q1.deleteWithAnswers();

        assertThat(Q1.isDeleted()).isTrue();
        for (Answer answer : Q1.answers()) {
            assertThat(answer.isDeleted()).isTrue();
        }
    }
}
