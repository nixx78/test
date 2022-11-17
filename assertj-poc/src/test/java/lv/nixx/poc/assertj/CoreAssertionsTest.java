package lv.nixx.poc.assertj;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;


class CoreAssertionsTest {

    @Test
    void assertJSandboxTest() {
        assertAll(
                () -> assertThat("Start middle end")
                        .startsWith("Start")
                        .contains("middle")
                        .endsWith("end"),
                () -> assertThat(123).isNotEqualTo(777),
                () -> assertThat(123).withFailMessage("Fail").isEqualTo(123),
                () -> assertThat(BigDecimal.ONE).isEqualByComparingTo(BigDecimal.valueOf(1.0000))
        );
    }

    @Test
    void mapTestSandboxTest() {

        Map<Integer, Object> m = Map.of(
                1, "One",
                2, "Two",
                3, "Three",
                4, BigDecimal.valueOf(1.2)
        );

        assertThat(m)
                .containsKey(1)
                .containsValues("One", "Two", "Three")
                .containsEntry(1, "One");

        assertThat(m).containsAllEntriesOf(
                Map.of(
                        1, "One",
                        2, "Two",
                        3, "Three",
                        4, BigDecimal.valueOf(1.200)
                )
        );
    }

    @Test
    void collectionOfMapsTest() {
        List<Map<String, Object>> actualData = List.of(
                Map.of(
                        "field11", "value11",
                        "field12", 100,
                        "field13", BigDecimal.valueOf(1.000)
                ),
                Map.of(
                        "field21", "value21",
                        "field22", 200,
                        "field23", BigDecimal.valueOf(2.001)
                ),
                Map.of(
                        "field31", "value31",
                        "field32", 300,
                        "field33", BigDecimal.valueOf(3.33)
                )
        );

        List<Map<String, Object>> expectedData = List.of(
                Map.of(
                        "field11", "value11",
                        "field12", 100,
                        "field13", BigDecimal.valueOf(1.000)
                ),
                Map.of(
                        "field21", "value21",
                        "field22", 200,
                        "field23", BigDecimal.valueOf(2.001)
                ),
                Map.of(
                        "field31", "value31",
                        "field32", 300,
                        "field33", BigDecimal.valueOf(3.330)
                )
        );

        assertThat(actualData).containsAll(expectedData);
    }

    @Test
    void classAssertionTest() {
        Transaction actual = new Transaction("1", "ACC1", 10.01, "01/04/2021");
        Transaction expected = new Transaction("2", "ACC1", 10.01, "01/04/2021");

        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expected);

        assertThat(actual)
                .usingRecursiveComparison()
                .comparingOnlyFields("accountId", "amount")
                .isEqualTo(expected)
                .getWritableAssertionInfo();
    }

    @Test
    void listSandboxTest() {

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
    void exceptionSandboxTest() {

        assertThatThrownBy(() -> this.methodWithException(0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Exception message");

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> this.methodWithException(0))
                .withMessage("Exception message");

    }

    @Test
    void complexStructureTest() {

        Collection<Payload> actual = List.of(
                new Payload(
                        "Action1",
                        List.of(
                                Map.of(
                                        "key1", "value1",
                                        "key2", "value2"
                                ),
                                Map.of(
                                        "key21", "value21",
                                        "key22", "value22"
                                )
                        )
                ),
                new Payload(
                        "Action2",
                        List.of(
                                Map.of(
                                        "act1", "value1",
                                        "act2", "value2"
                                ),
                                Map.of(
                                        "act21", "value21",
                                        "act22", "value22"
                                )
                        )
                )

        );

        Collection<Payload> expected = List.of(
                new Payload(
                        "Action1",
                        List.of(
                                Map.of(
                                        "key1", "value1",
                                        "key2", "value2"
                                ),
                                Map.of(
                                        "key21", "value21",
                                        "key22", "value22"
                                )
                        )
                ),
                new Payload(
                        "Action2",
                        List.of(
                                Map.of(
                                        "act1", "value1",
                                        "act2", "value2"
                                ),
                                Map.of(
                                        "act21", "value21",
                                        "act22", "value22"
                                )
                        )
                )

        );

        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);

    }

    @Test
    void classAssertWithBigDecimalTest() {

        DtoWithBigDecimal actual = new DtoWithBigDecimal(BigDecimal.valueOf(10.100));
        DtoWithBigDecimal expected = new DtoWithBigDecimal(BigDecimal.valueOf(10.1));

        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @AllArgsConstructor
    @Getter
    static class Payload {
        String action;
        Collection<Map<String, String>> value;
    }

    private void methodWithException(int v) {
        if (v == 0) {
            throw new IllegalArgumentException("Exception message");
        }
    }


}
