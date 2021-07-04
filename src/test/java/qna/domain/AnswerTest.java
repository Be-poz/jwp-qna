package qna.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AnswerTest {
    public static final Answer A1 = new Answer(UserTest.JAVAJIGI, QuestionTest.Q1, "Answers Contents1");
    public static final Answer A2 = new Answer(UserTest.SANJIGI, QuestionTest.Q1, "Answers Contents2");

    @Test
    @DisplayName("isOwner")
    public void isOwnerTest() {
        assertThat(A1.isOwner(UserTest.JAVAJIGI)).isTrue();
    }

    @Test
    @DisplayName("delete")
    public void deleteTest() {
        A1.delete();
        assertThat(A1.isDeleted()).isTrue();
    }

}
