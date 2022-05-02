package lv.nixx.poc.assertj;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;


class AssertJSandbox {

    @Test
    void assertJSandboxTest() {

        assertAll(
                () -> assertThat("Start middle end")
                        .startsWith("Start")
                        .contains("middle")
                        .endsWith("end"),
                () -> assertThat(123).isNotEqualTo(777),
                () -> assertThat(123).withFailMessage("Fail").isEqualTo(777)
        );
    }

    @Test
    void mapTestSandbox() {

        Map<Integer, String> m = Map.of(
                1, "One",
                2, "Two",
                3, "Three"
        );

        assertThat(m)
                .containsKey(1)
                .containsValues("One", "Two", "Three")
                .containsEntry(1, "One");

        assertThat(m).containsAllEntriesOf(
                Map.of(
                        1, "One",
                        2, "Two",
                        3, "Three"
                )
        );

    }

    @Test
    void classAssertion() {
        TransactionDTO actual = new TransactionDTO("1", "ACC1", 10.01, "01/04/2021");
        TransactionDTO expected = new TransactionDTO("2", "ACC1", 10.01, "01/04/2021");

        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expected);
    }

    @Test
    void listSandbox() {

        List<Integer> lst = List.of(
                1, 2, 3, 4, 5, 6
        );

        assertThat(lst)
                .isNotEmpty()
                .startsWith(1)
                .endsWith(6)
                .containsAnyOf(5, 4, 3)
                .containsSequence(1, 2, 3, 4, 5, 6);
    }

    @Test
    void exceptionSandbox() {

        assertThatThrownBy(() -> this.methodWithException(0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Exception message");

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> this.methodWithException(0))
                .withMessage("Exception message");

    }

    private void methodWithException(int v) {
        if (v == 0) {
            throw new IllegalArgumentException("Exception message");
        }

    }


}
